package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.R
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

sealed class MovieCategoryLabelTextViewState{
    class InputAsTextState(val text: String): MovieCategoryLabelTextViewState()
    class InputAsResourceState(@StringRes val textRes: Int): MovieCategoryLabelTextViewState()
}
data class MovieCategoryLabelViewState(
    val itemId: Int,
    val isSelected: Boolean,
    val categoryText: MovieCategoryLabelTextViewState
)

@Composable
fun MovieCategoryLabel(
    movieCategoryLabelViewState: MovieCategoryLabelViewState
){
    if(movieCategoryLabelViewState.isSelected){
        SelectedText(movieCategoryLabelViewState)
    }
    else{
        UnselectedText(movieCategoryLabelViewState)
    }
}
@Composable
fun SelectedText(
    movieCategoryLabelViewState: MovieCategoryLabelViewState,
    modifier: Modifier = Modifier
){
    Column {
    Text(
        text = when(movieCategoryLabelViewState.categoryText){
            is MovieCategoryLabelTextViewState.InputAsTextState -> movieCategoryLabelViewState.categoryText.text
            is MovieCategoryLabelTextViewState.InputAsResourceState -> stringResource(id = movieCategoryLabelViewState.categoryText.textRes)
        },
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
    )

    Divider(color = Color.Black, thickness = 2.dp, modifier = modifier.width(50.dp))
    }
}

@Composable
fun UnselectedText(
    movieCategoryLabelViewState: MovieCategoryLabelViewState,
    modifier: Modifier = Modifier
){
    Text(
        text = when(movieCategoryLabelViewState.categoryText){
            is MovieCategoryLabelTextViewState.InputAsTextState -> movieCategoryLabelViewState.categoryText.text
            is MovieCategoryLabelTextViewState.InputAsResourceState -> stringResource(id = movieCategoryLabelViewState.categoryText.textRes)
        },
        fontSize = 14.sp,
        modifier = modifier
            .alpha(0.5f)
    )
}

@Preview
@Composable
fun MovieCategoryLabelPreview(){
    val inputText = MovieCategoryLabelTextViewState.InputAsTextState("Movies")
    val stringRes = MovieCategoryLabelTextViewState.InputAsResourceState(R.string.mockMovie)
    val inputAsText = MovieCategoryLabelViewState(1, false, inputText)
    val inputAsRes = MovieCategoryLabelViewState(2, true, stringRes)
    Row {
        MovieCategoryLabel(movieCategoryLabelViewState = inputAsRes)
        MovieCategoryLabel(movieCategoryLabelViewState = inputAsText)
    }
}

