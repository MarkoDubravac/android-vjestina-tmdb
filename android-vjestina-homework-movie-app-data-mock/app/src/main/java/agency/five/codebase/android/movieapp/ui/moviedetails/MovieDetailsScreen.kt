package agency.five.codebase.android.movieapp.ui.moviedetails

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.ui.component.ActorCard
import agency.five.codebase.android.movieapp.ui.component.CrewItem
import agency.five.codebase.android.movieapp.ui.component.FavoriteButton
import agency.five.codebase.android.movieapp.ui.component.UserScoreProgressBar
import agency.five.codebase.android.movieapp.ui.moviedetails.mapper.MovieDetailsMapper
import agency.five.codebase.android.movieapp.ui.moviedetails.mapper.MovieDetailsMapperImpl
import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import agency.five.codebase.android.movieapp.ui.theme.Typography
import agency.five.codebase.android.movieapp.ui.theme.spacing
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage

private val movieDetailsMapper: MovieDetailsMapper = MovieDetailsMapperImpl()

val movieDetailsViewState = movieDetailsMapper.toMovieDetailsViewState(MoviesMock.getMovieDetails())

@Composable
fun MovieDetailsRoute() {
    val movieDetailsViewState by remember {
        mutableStateOf(
            movieDetailsViewState
        )
    }
    MovieDetailsScreen(
        movieDetailsViewState = movieDetailsViewState.copy(
            isFavorite = !movieDetailsViewState.isFavorite
        ),
    )
}

@Composable
fun MovieDetailsScreen(
    movieDetailsViewState: MovieDetailsViewState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        content = {
            item {
                MovieImageDetails(
                    movieDetailsViewState = movieDetailsViewState,
                    modifier = Modifier.height(dimensionResource(id = R.dimen.details_screen_image_height)),
                )
            }
            item {
                OverviewDetails(
                    movieDetailsViewState = movieDetailsViewState
                )
            }
            item {
                TopBilledDetails(
                    movieDetailsViewState = movieDetailsViewState
                )
            }
        },
    )
}

@Composable
fun MovieImageDetails(
    movieDetailsViewState: MovieDetailsViewState,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        AsyncImage(
            model = movieDetailsViewState.imageUrl,
            contentDescription = movieDetailsViewState.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
        Column(
            modifier = Modifier
                .padding(MaterialTheme.spacing.small)
                .align(alignment = Alignment.BottomStart),
        ) {
            Row {
                UserScoreProgressBar(
                    score = movieDetailsViewState.voteAverage,
                    modifier = Modifier.size(dimensionResource(id = R.dimen.progress_circle_diameter)),
                    textColor = Color.White,
                )
                Text(
                    text = stringResource(id = R.string.user_score),
                    style = Typography.h3,
                    color = Color.White,
                    modifier = Modifier
                        .wrapContentSize()
                        .align(
                            alignment = Alignment.CenterVertically
                        ),
                )
            }
            Text(
                text = movieDetailsViewState.title,
                style = Typography.h2, color = Color.White,
            )
            FavoriteButton(
                isFavorite = movieDetailsViewState.isFavorite,
                onClick = { },
            )
        }
    }
}

@Composable
fun OverviewDetails(
    movieDetailsViewState: MovieDetailsViewState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(
            MaterialTheme.spacing.small
        ),
    ) {
        Text(
            text = stringResource(id = R.string.overview),
            style = Typography.h3,
        )
        Text(
            text = movieDetailsViewState.overview,
            style = Typography.h4,
            color = Color.Black,
            maxLines = 15,
            overflow = TextOverflow.Ellipsis,
        )
        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            modifier = Modifier.height(
                dimensionResource(id = R.dimen.crew_grid_height)
            ),
            horizontalArrangement = Arrangement.spacedBy(
                MaterialTheme.spacing.medium
            ),
            verticalArrangement = Arrangement.spacedBy(
                MaterialTheme.spacing.medium
            ),
            content = {
                itemsIndexed(movieDetailsViewState.crew) { _, item ->
                    CrewItem(
                        crewItemViewState = item.crewItemViewState
                    )
                }
            },
        )
    }
}

@Composable
fun TopBilledDetails(
    movieDetailsViewState: MovieDetailsViewState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(
            MaterialTheme.spacing.small
        ),
    ) {
        Text(
            text = stringResource(id = R.string.top_billed_cast),
            style = Typography.h3,
        )
        LazyRow(
            contentPadding = PaddingValues(
                vertical = MaterialTheme.spacing.small
            ),
            horizontalArrangement = Arrangement.spacedBy(
                MaterialTheme.spacing.extraSmallToSmall
            ),
            content = {
                items(movieDetailsViewState.cast.size) { index ->
                    ActorCard(
                        actorCardViewState = movieDetailsViewState.cast[index].actorCardViewState,
                        modifier = Modifier
                            .width(dimensionResource(id = R.dimen.actor_card_width))
                            .height(dimensionResource(id = R.dimen.actor_card_height)),
                    )
                }
            },
        )
    }
}

@Preview
@Composable
fun MovieDetailsScreenPreview() {
    MovieAppTheme {
        MovieDetailsScreen(
            movieDetailsViewState = movieDetailsViewState
        )
    }
}
