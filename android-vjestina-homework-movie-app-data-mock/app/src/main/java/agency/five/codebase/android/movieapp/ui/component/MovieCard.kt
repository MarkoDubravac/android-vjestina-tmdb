package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.ui.theme.Shapes
import agency.five.codebase.android.movieapp.ui.theme.spacing
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage

data class MovieCardViewState(
    val imageUrl: String,
    val isFavorite: Boolean,
)

@Composable
fun MovieCard(
    movieCardViewState: MovieCardViewState,
    modifier: Modifier = Modifier,
    onMovieItemClick: () -> Unit = {},
    onLikeButtonClick: () -> Unit = {}
) {
    Card(
        modifier = modifier.clickable { onMovieItemClick() },
        shape = Shapes.large,
        elevation = dimensionResource(id = R.dimen.card_elevation)
    ) {
        Box {
            AsyncImage(
                model = movieCardViewState.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            FavoriteButton(
                modifier = Modifier.padding(MaterialTheme.spacing.small),
                isFavorite = false,
                onClick = onLikeButtonClick
            )
        }
    }
}

@Preview(name = "phone", device = "spec:shape=Normal,width=360,height=640,unit=dp,dpi=480")
@Composable
fun MovieCardPreview() {
    val movie = MoviesMock.getMoviesList().first()
    val movieCardViewState = MovieCardViewState(
        imageUrl = movie.imageUrl.toString(),
        isFavorite = false,
    )

    MovieCard(
        movieCardViewState = movieCardViewState,
        modifier = Modifier
            .padding(MaterialTheme.spacing.small)
            .width(dimensionResource(id = R.dimen.movie_card_width))
            .height(dimensionResource(id = R.dimen.movie_card_height))
    )
}
