package agency.five.codebase.android.movieapp.ui.home

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.ui.component.MovieCard
import agency.five.codebase.android.movieapp.ui.component.MovieCategoryLabel
import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import agency.five.codebase.android.movieapp.ui.theme.Typography
import agency.five.codebase.android.movieapp.ui.theme.spacing
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeRoute(
    viewModel: HomeViewModel,
    onNavigateToMovieDetails: (Int) -> Unit,
) {
    val popularCategoryViewState: HomeMovieCategoryViewState by viewModel.popularMoviesViewState.collectAsState()
    val nowPlayingViewState: HomeMovieCategoryViewState by viewModel.nowPlayingMoviesViewState.collectAsState()
    val upcomingViewState: HomeMovieCategoryViewState by viewModel.upcomingMoviesViewState.collectAsState()
    HomeScreen(
        popularCategoryViewState = popularCategoryViewState,
        nowPlayingViewState = nowPlayingViewState,
        upcomingViewState = upcomingViewState,
        onFavoriteButtonClick = viewModel::toggleFavorite,
        onCategoryClick = viewModel::changeCategory,
        onNavigateToMovieDetails = onNavigateToMovieDetails,
    )
}

@Composable
fun HomeScreen(
    popularCategoryViewState: HomeMovieCategoryViewState,
    nowPlayingViewState: HomeMovieCategoryViewState,
    upcomingViewState: HomeMovieCategoryViewState,
    modifier: Modifier = Modifier,
    onCategoryClick: (Int) -> Unit,
    onNavigateToMovieDetails: (Int) -> Unit,
    onFavoriteButtonClick: (Int) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        item {
            MoviesInCategory(
                homeMovieCategoryViewState = popularCategoryViewState,
                title = stringResource(id = R.string.whats_popular),
                onCategoryClick = onCategoryClick,
                onFavoriteButtonClick = onFavoriteButtonClick,
                onNavigateToMovieDetails = onNavigateToMovieDetails,
            )
        }
        item {
            MoviesInCategory(
                homeMovieCategoryViewState = nowPlayingViewState,
                title = stringResource(id = R.string.now_playing),
                onCategoryClick = onCategoryClick,
                onFavoriteButtonClick = onFavoriteButtonClick,
                onNavigateToMovieDetails = onNavigateToMovieDetails,
            )
        }
        item {
            MoviesInCategory(
                homeMovieCategoryViewState = upcomingViewState,
                title = stringResource(id = R.string.upcoming),
                onCategoryClick = onCategoryClick,
                onFavoriteButtonClick = onFavoriteButtonClick,
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
    onCategoryClick: (Int) -> Unit,
    onNavigateToMovieDetails: (Int) -> Unit,
    onFavoriteButtonClick: (Int) -> Unit,
) {
    Column(modifier = modifier.padding(MaterialTheme.spacing.small)) {
        Text(text = title, style = Typography.h3)
        LazyRow(
            contentPadding = PaddingValues(vertical = MaterialTheme.spacing.small),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmallToSmall),
            content = {
                itemsIndexed(homeMovieCategoryViewState.movieCategories) { _, item ->
                    MovieCategoryLabel(
                        movieCategoryLabelViewState = item,
                        onTextClick = { onCategoryClick(item.id) },
                    )
                }
            },
        )
        LazyRow(
            contentPadding = PaddingValues(vertical = MaterialTheme.spacing.small),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmallToSmall),
            content = {
                itemsIndexed(homeMovieCategoryViewState.movies) { _, item ->
                    MovieCard(
                        movieCardViewState = item.movieCardViewState,
                        onLikeButtonClick = { onFavoriteButtonClick(item.id) },
                        modifier = Modifier
                            .width(dimensionResource(id = R.dimen.movie_card_width))
                            .height(dimensionResource(id = R.dimen.movie_card_height)),
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

    }
}
