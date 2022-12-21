package agency.five.codebase.android.movieapp.ui.favorites

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.ui.component.MovieCard
import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import agency.five.codebase.android.movieapp.ui.theme.Typography
import agency.five.codebase.android.movieapp.ui.theme.spacing
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
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
fun FavoritesRoute(
    viewModel: FavoritesViewModel,
    onNavigateToMovieDetails: (Int) -> Unit,
) {
    val favoritesViewState: FavoritesViewState by viewModel.favoritesViewState.collectAsState()
    FavoritesScreen(
        favoritesViewState = favoritesViewState,
        onNavigateToMovieDetails = onNavigateToMovieDetails,
        onFavoriteButtonClick = { viewModel.toggleFavorite(it) },
    )
}

@Composable
fun FavoritesScreen(
    favoritesViewState: FavoritesViewState,
    modifier: Modifier = Modifier,
    onNavigateToMovieDetails: (Int) -> Unit,
    onFavoriteButtonClick: (Int) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier.padding(MaterialTheme.spacing.small),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmallToSmall),
        columns = GridCells.Fixed(3),
        content = {
            item(span = { GridItemSpan(3) }) {
                Text(text = stringResource(id = R.string.favorites), style = Typography.h3)
            }
            itemsIndexed(favoritesViewState.favoriteMovies) { _, item ->
                MovieCard(
                    movieCardViewState = item.movieCardViewState,
                    onMovieItemClick = { onNavigateToMovieDetails(item.id) },
                    onLikeButtonClick = { onFavoriteButtonClick(item.movieCardViewState.movieId) },
                    modifier = modifier.height(dimensionResource(id = R.dimen.movie_card_height))
                )
            }
        },
    )
}

@Preview
@Composable
fun FavoritesScreenPreview() {
    MovieAppTheme {

    }
}
