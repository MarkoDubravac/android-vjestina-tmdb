package agency.five.codebase.android.movieapp.ui.main

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.data.repository.FakeMovieRepository
import agency.five.codebase.android.movieapp.navigation.MOVIE_ID_KEY
import agency.five.codebase.android.movieapp.navigation.MovieDetailsDestination
import agency.five.codebase.android.movieapp.navigation.NavigationItem
import agency.five.codebase.android.movieapp.ui.favorites.FavoritesRoute
import agency.five.codebase.android.movieapp.ui.favorites.FavoritesViewModel
import agency.five.codebase.android.movieapp.ui.favorites.mapper.FavoritesMapperImpl
import agency.five.codebase.android.movieapp.ui.home.HomeRoute
import agency.five.codebase.android.movieapp.ui.home.HomeViewModel
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapperImpl
import agency.five.codebase.android.movieapp.ui.moviedetails.MovieDetailsRoute
import agency.five.codebase.android.movieapp.ui.moviedetails.MovieDetailsViewModel
import agency.five.codebase.android.movieapp.ui.moviedetails.mapper.MovieDetailsMapperImpl
import agency.five.codebase.android.movieapp.ui.theme.BackgroundBlue
import agency.five.codebase.android.movieapp.ui.theme.spacing
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var showBottomBar by remember { mutableStateOf(false) }
    val showBackIcon = !showBottomBar
    Scaffold(
        topBar = { TopBar(navigationIcon = { if (showBackIcon) BackIcon(onBackClick = navController::popBackStack) }) },
        bottomBar = {
            if (showBottomBar) BottomNavigationBar(
                destinations = listOf(
                    NavigationItem.HomeDestination,
                    NavigationItem.FavoritesDestination,
                ), onNavigateToDestination = {
                    navController.popBackStack(
                        NavigationItem.HomeDestination.route,
                        inclusive = false,
                    )
                    navController.navigate(it.route) { launchSingleTop = true }
                }, currentDestination = navBackStackEntry?.destination
            )
        },
    ) { padding ->
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxSize(),
        ) {
            NavHost(
                navController = navController,
                startDestination = NavigationItem.HomeDestination.route,
                modifier = Modifier.padding(padding),
            ) {
                composable(NavigationItem.HomeDestination.route) {
                    showBottomBar = true
                    HomeRoute(
                        viewModel = HomeViewModel(FakeMovieRepository(Dispatchers.IO), HomeScreenMapperImpl()),
                        onNavigateToMovieDetails = { id ->
                            navController.navigate(
                                MovieDetailsDestination.createNavigationRoute(id)
                            )
                        }
                    )
                }
                composable(NavigationItem.FavoritesDestination.route) {
                    showBottomBar = true
                    FavoritesRoute(
                        viewModel = FavoritesViewModel(
                            FakeMovieRepository(Dispatchers.IO), FavoritesMapperImpl()
                        ),
                        onNavigateToMovieDetails = { id ->
                            navController.navigate(
                                MovieDetailsDestination.createNavigationRoute(id)
                            )
                        },
                    )
                }
                composable(
                    route = MovieDetailsDestination.route,
                    arguments = listOf(navArgument(MOVIE_ID_KEY) { type = NavType.IntType }),
                ) {
                    showBottomBar = false
                    val movieId = it.arguments?.getInt(MOVIE_ID_KEY)
                    MovieDetailsRoute(
                        viewModel = getViewModel {
                            parametersOf(
                                it.arguments?.getInt(
                                    MOVIE_ID_KEY
                                ) ?: throw IllegalArgumentException("No movie id found.")
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun TopBar(
    navigationIcon: @Composable (() -> Unit)? = null,
) {
    Box(
        modifier = Modifier
            .background(BackgroundBlue)
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.tob_bar_height)),
        contentAlignment = Alignment.CenterStart,
    ) {
        navigationIcon?.invoke()
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@Composable
private fun BackIcon(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(id = R.drawable.ic_back),
        contentDescription = stringResource(id = R.string.back_icon),
        modifier = modifier
            .clickable { onBackClick() }
            .size(dimensionResource(id = R.dimen.nav_icon_size))
            .padding(start = MaterialTheme.spacing.extraSmallToSmall),
        alignment = Alignment.Center,
    )
}

@Composable
private fun BottomNavigationBar(
    destinations: List<NavigationItem>,
    onNavigateToDestination: (NavigationItem) -> Unit,
    currentDestination: NavDestination?,
) {
    BottomNavigation(backgroundColor = MaterialTheme.colors.background) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.spacing.extraSmallToSmall),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            destinations.forEach { destination ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    if (currentDestination != null) {
                        Image(
                            painter = painterResource(
                                id = if (currentDestination.route == destination.route) destination.selectedIconId else destination.unselectedIconId
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .size(dimensionResource(id = R.dimen.nav_icon_size))
                                .clickable { onNavigateToDestination(destination) },
                        )
                    }
                    Text(
                        text = stringResource(id = destination.labelId),
                        style = MaterialTheme.typography.h3
                    )
                }
            }
        }
    }
}
