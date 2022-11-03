package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.ui.theme.Shapes
import agency.five.codebase.android.movieapp.ui.theme.Typography
import agency.five.codebase.android.movieapp.ui.theme.spacing
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage


data class ActorCardViewState(
    val imageUrl: String,
    val name: String,
    val character: String,
)

@Composable
fun ActorCard(
    modifier: Modifier = Modifier,
    actorCardViewState: ActorCardViewState
) {
    Card(
        elevation = dimensionResource(id = R.dimen.card_elevation),
        modifier = modifier,
        shape = Shapes.large
    )
    {
        Column {
            AsyncImage(
                model = actorCardViewState.imageUrl,
                contentDescription = actorCardViewState.name,
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.actor_image_height)),
                contentScale = ContentScale.Crop,

                )
            Text(
                text = actorCardViewState.name,
                style = Typography.h3,
                modifier = Modifier
                    .padding(MaterialTheme.spacing.small),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis

            )
            Text(
                text = actorCardViewState.character,
                style = Typography.h4,
                modifier = Modifier
                    .padding(MaterialTheme.spacing.small),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
private fun ActorCardPreview() {
    val actor = MoviesMock.getActor()
    val actorCardViewState = ActorCardViewState(
        name = actor.name,
        imageUrl = actor.imageUrl.toString(),
        character = actor.character
    )
    ActorCard(
        actorCardViewState = actorCardViewState,
        modifier = Modifier
            .width(dimensionResource(id = R.dimen.actor_card_width))
            .height(dimensionResource(id = R.dimen.actor_card_height))
            .padding(MaterialTheme.spacing.small),
    )
}


