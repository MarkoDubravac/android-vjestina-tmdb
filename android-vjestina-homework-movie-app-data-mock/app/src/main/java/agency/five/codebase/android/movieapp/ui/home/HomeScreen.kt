package agency.five.codebase.android.movieapp.ui.home

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.ui.component.MovieCard
import agency.five.codebase.android.movieapp.ui.component.MovieCategoryLabel
import agency.five.codebase.android.movieapp.ui.component.MovieCategoryLabelViewState
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapper
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapperImpl
import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import agency.five.codebase.android.movieapp.ui.theme.Typography
import agency.five.codebase.android.movieapp.ui.theme.spacing
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

private val homeScreenMapper: HomeScreenMapper = HomeScreenMapperImpl()

val popularCategoryViewState = homeScreenMapper.toHomeMovieCategoryViewState(
    listOf(
        MovieCategory.POPULAR_STREAMING,
        MovieCategory.POPULAR_ON_TV,
        MovieCategory.POPULAR_FOR_RENT,
        MovieCategory.POPULAR_IN_THEATERS
    ), MovieCategory.POPULAR_STREAMING, MoviesMock.getMoviesList()
)

val nowPlayingCategoryViewState = homeScreenMapper.toHomeMovieCategoryViewState(
    listOf(
        MovieCategory.PLAYING_TV,
        MovieCategory.PLAYING_MOVIES,
    ), MovieCategory.PLAYING_MOVIES, MoviesMock.getMoviesList()
)

val upcomingCategoryViewState = homeScreenMapper.toHomeMovieCategoryViewState(
    listOf(
        MovieCategory.UPCOMING_TODAY,
        MovieCategory.UPCOMING_THIS_WEEK,
    ), MovieCategory.UPCOMING_TODAY, MoviesMock.getMoviesList()
)

@Composable
fun HomeRoute(
    onNavigateToMovieDetails: (
        Int
    ) -> Unit,
) {
    var popularCategoryViewState by remember {
        mutableStateOf(popularCategoryViewState)
    }
    var nowPlayingViewState by remember {
        mutableStateOf(nowPlayingCategoryViewState)
    }
    var upcomingViewState by remember {
        mutableStateOf(upcomingCategoryViewState)
    }
    HomeScreen(
        popularCategoryViewState = popularCategoryViewState,
        nowPlayingViewState = nowPlayingViewState,
        upcomingViewState = upcomingViewState,
        onLikeButtonClick = {},
        onCategoryClick = {
            when (it.id) {
                in 0..3 -> {
                    popularCategoryViewState = changeCategory(
                        homeMovieCategoryViewState = popularCategoryViewState,
                        id = it.id,
                    )
                }
                in 4..5 -> {
                    nowPlayingViewState = changeCategory(
                        homeMovieCategoryViewState = nowPlayingViewState,
                        id = it.id,
                    )
                }
                else -> {
                    upcomingViewState = changeCategory(
                        homeMovieCategoryViewState = upcomingViewState,
                        id = it.id,
                    )
                }
            }
        },
        onNavigateToMovieDetails = onNavigateToMovieDetails,
    )
}

@Composable
fun HomeScreen(
    popularCategoryViewState: HomeMovieCategoryViewState,
    nowPlayingViewState: HomeMovieCategoryViewState,
    upcomingViewState: HomeMovieCategoryViewState,
    modifier: Modifier = Modifier,
    onCategoryClick: (
        MovieCategoryLabelViewState
    ) -> Unit,
    onNavigateToMovieDetails: (
        Int
    ) -> Unit,
    onLikeButtonClick: () -> Unit,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        item {
            MoviesInCategory(
                homeMovieCategoryViewState = popularCategoryViewState,
                title = stringResource(id = R.string.whats_popular),
                onCategoryClick = onCategoryClick,
                onLikeButtonClick = onLikeButtonClick,
                onNavigateToMovieDetails = onNavigateToMovieDetails,
            )
        }
        item {
            MoviesInCategory(
                homeMovieCategoryViewState = nowPlayingViewState,
                title = stringResource(id = R.string.now_playing),
                onCategoryClick = onCategoryClick,
                onLikeButtonClick = onLikeButtonClick,
                onNavigateToMovieDetails = onNavigateToMovieDetails,
            )
        }
        item {
            MoviesInCategory(
                homeMovieCategoryViewState = upcomingViewState,
                title = stringResource(id = R.string.upcoming),
                onCategoryClick = onCategoryClick,
                onLikeButtonClick = onLikeButtonClick,
                onNavigateToMovieDetails = onNavigateToMovieDetails,
            )
        }
    }
}

@Composable
fun MoviesInCategory(
    homeMovieCategoryViewState: HomeMovieCategoryViewState,
    title: String,
    modifier: Modifier = Modifier,
    onCategoryClick: (
        MovieCategoryLabelViewState
    ) -> Unit,
    onNavigateToMovieDetails: (
        Int
    ) -> Unit,
    onLikeButtonClick: () -> Unit,
) {
    Column(
        modifier = modifier.padding(
            MaterialTheme.spacing.small,
        ),
    ) {
        Text(
            text = title,
            style = Typography.h3,
        )
        LazyRow(
            contentPadding = PaddingValues(vertical = MaterialTheme.spacing.small),
            horizontalArrangement = Arrangement.spacedBy(
                MaterialTheme.spacing.extraSmallToSmall
            ),
            content = {
                itemsIndexed(homeMovieCategoryViewState.movieCategories) { _, item ->
                    MovieCategoryLabel(
                        movieCategoryLabelViewState = item,
                        onTextClick = {
                            onCategoryClick(item)
                        },
                    )
                }
            },
        )
        LazyRow(
            contentPadding = PaddingValues(vertical = MaterialTheme.spacing.small),
            horizontalArrangement = Arrangement.spacedBy(
                MaterialTheme.spacing.extraSmallToSmall
            ),
            content = {
                itemsIndexed(homeMovieCategoryViewState.movies) { _, item ->
                    MovieCard(
                        movieCardViewState = item.movieCardViewState,
                        onLikeButtonClick = onLikeButtonClick,
                        modifier = Modifier
                            .width(
                                dimensionResource(id = R.dimen.movie_card_width)
                            )
                            .height(
                                dimensionResource(id = R.dimen.movie_card_height)
                            ),
                        onMovieItemClick = { onNavigateToMovieDetails(item.id) },
                    )
                }
            },
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    MovieAppTheme {
        HomeScreen(
            popularCategoryViewState = popularCategoryViewState,
            nowPlayingViewState = nowPlayingCategoryViewState,
            upcomingViewState = upcomingCategoryViewState,
            onCategoryClick = {},
            onLikeButtonClick = {},
            onNavigateToMovieDetails = {},
        )
    }
}
