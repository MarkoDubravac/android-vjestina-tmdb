package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.model.Crewman
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.utils.widget.MockView


@Composable
fun CrewItem(
    crewman: Crewman,
    modifier: Modifier = Modifier
){
    Column {
        Text(
            text = crewman.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = crewman.job,
            fontSize = 14.sp
        )
    }
}

@Preview
@Composable
private fun CrewItemPreview(){
    val crewman = MoviesMock.getCrewman()
    val crewItemViewState = Crewman(id = 0, name = crewman.name, job = crewman.job)
    CrewItem(crewman = crewItemViewState)
}