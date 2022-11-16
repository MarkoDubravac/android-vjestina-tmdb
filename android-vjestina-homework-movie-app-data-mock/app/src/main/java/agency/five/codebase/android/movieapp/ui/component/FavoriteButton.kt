package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.ui.theme.ButtonBlue
import agency.five.codebase.android.movieapp.ui.theme.spacing
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(id = if (isFavorite) R.drawable.liked else R.drawable.not_liked),
        contentDescription = "Liked icon",
        modifier = modifier
            .clickable {
                onClick()
            }
            .background(
                color = ButtonBlue.copy(alpha = 0.6f),
                shape = CircleShape,
            )
            .padding(MaterialTheme.spacing.small),
    )
}

@Preview(name = "phone", device = "spec:shape=Normal,width=360,height=640,unit=dp,dpi=480")
@Composable
fun FavoriteButtonPreview() {
    FavoriteButton(
        modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size)),
        isFavorite = false,
        onClick = { },
    )
}
