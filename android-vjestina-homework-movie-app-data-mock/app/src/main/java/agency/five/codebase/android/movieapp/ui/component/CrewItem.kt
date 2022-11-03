package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.ui.theme.Gray600
import agency.five.codebase.android.movieapp.ui.theme.Shapes
import agency.five.codebase.android.movieapp.ui.theme.Typography
import agency.five.codebase.android.movieapp.ui.theme.spacing
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview

data class CrewItemViewState(
    val name: String,
    val job: String
)

@Composable
fun CrewItem(
    modifier: Modifier = Modifier,
    crewItemViewState: CrewItemViewState
) {
    Column(
        modifier = modifier
            .wrapContentSize()
    ) {
        Text(
            text = crewItemViewState.name,
            style = Typography.h3,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = crewItemViewState.job,
            style = Typography.h4,
            color = Color.Black,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
private fun CrewItemPreview() {
    val crewman = MoviesMock.getCrewman()
    val crewItemViewState = CrewItemViewState(
        crewman.name,
        crewman.job
    )
    CrewItem(crewItemViewState = crewItemViewState, modifier = Modifier
        .clip(Shapes.medium)
        .background(color = Gray600)
        .padding(MaterialTheme.spacing.small))
}
