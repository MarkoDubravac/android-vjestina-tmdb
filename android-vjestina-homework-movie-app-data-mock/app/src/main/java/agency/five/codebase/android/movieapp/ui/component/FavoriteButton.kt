package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier
){
    val isFavorite = remember {
        mutableStateOf(false)
    }
    Image(
        painter = painterResource(id = if (isFavorite.value) R.drawable.liked else R.drawable.not_liked),
        contentDescription = "Liked icon",
        modifier = modifier
            .background(Color(0xFF627181).copy(alpha = 0.6f), CircleShape)
            .padding(15.dp)
            .size(20.dp)
            .clickable {
                isFavorite.value = isFavorite.value.not()
            })
}

@Preview
@Composable
fun FavoriteButtonPreview(){
    FavoriteButton()
}
