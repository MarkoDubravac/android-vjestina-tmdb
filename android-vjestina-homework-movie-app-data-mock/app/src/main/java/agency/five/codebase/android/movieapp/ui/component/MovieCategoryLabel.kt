package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.ui.theme.Typography
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

sealed class MovieCategoryLabelTextViewState {
    class InputAsTextState(val text: String) : MovieCategoryLabelTextViewState()
    class InputAsResourceState(@StringRes val textRes: Int) : MovieCategoryLabelTextViewState()
}

data class MovieCategoryLabelViewState(
    val id: Int,
    val isSelected: Boolean,
    val categoryText: MovieCategoryLabelTextViewState,
)

@Composable
fun MovieCategoryLabel(
    movieCategoryLabelViewState: MovieCategoryLabelViewState,
    modifier: Modifier = Modifier,
    onTextClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .width(IntrinsicSize.Max)
            .clickable { onTextClick() },
    ) {
        if (movieCategoryLabelViewState.isSelected) {
            Text(
                text = selectInputType(movieCategoryLabelViewState = movieCategoryLabelViewState),
                style = Typography.h3,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Divider(
                color = Color.Black,
                thickness = 2.dp,
                modifier = Modifier.fillMaxWidth(),
            )
        } else {
            UnselectedText(movieCategoryLabelViewState = movieCategoryLabelViewState)
        }
    }
}

@Composable
fun UnselectedText(
    movieCategoryLabelViewState: MovieCategoryLabelViewState,
    modifier: Modifier = Modifier,
) {
    Text(
        text = selectInputType(movieCategoryLabelViewState = movieCategoryLabelViewState),
        style = Typography.h4,
        fontSize = 16.sp,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier,
    )
}

@Composable
fun selectInputType(
    movieCategoryLabelViewState: MovieCategoryLabelViewState
): String {
    return when (movieCategoryLabelViewState.categoryText) {
        is MovieCategoryLabelTextViewState.InputAsTextState -> movieCategoryLabelViewState.categoryText.text
        is MovieCategoryLabelTextViewState.InputAsResourceState -> stringResource(id = movieCategoryLabelViewState.categoryText.textRes)
    }
}

@Preview
@Composable
fun MovieCategoryLabelPreview() {
    val inputText = MovieCategoryLabelTextViewState.InputAsTextState("Movies")
    val stringRes = MovieCategoryLabelTextViewState.InputAsResourceState(R.string.mockMovie)
    val inputAsText = MovieCategoryLabelViewState(1, false, inputText)
    val inputAsRes = MovieCategoryLabelViewState(2, true, stringRes)
    Row {
        MovieCategoryLabel(movieCategoryLabelViewState = inputAsRes)
        MovieCategoryLabel(movieCategoryLabelViewState = inputAsText)
    }
}

