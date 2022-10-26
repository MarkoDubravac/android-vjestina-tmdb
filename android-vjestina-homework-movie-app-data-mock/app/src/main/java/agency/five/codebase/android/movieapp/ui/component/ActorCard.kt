package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.mock.MoviesMock
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage



data class ActorCardViewState(
    val imageUrl: String,
    val name: String,
    val character: String,
)

@Composable
fun ActorCard(
    actorCardViewState: ActorCardViewState,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier
            .size(width = 150.dp, height = 250.dp)
            .padding(10.dp)
            .clip(RoundedCornerShape(15.dp)),
        elevation = 10.dp,
    )
    {
        Column {
            AsyncImage(
                model = actorCardViewState.imageUrl,
                contentDescription = null,
                modifier = modifier
                    .height(130.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = modifier
                    .padding(6.dp),
                text = actorCardViewState.name,
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = actorCardViewState.character,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = modifier
                    .padding(5.dp)
                    .alpha(0.5f)
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
    ActorCard(actorCardViewState = actorCardViewState)
}


