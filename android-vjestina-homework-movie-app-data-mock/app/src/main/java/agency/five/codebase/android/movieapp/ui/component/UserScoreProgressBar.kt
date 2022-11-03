package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.ui.theme.Typography
import agency.five.codebase.android.movieapp.ui.theme.spacing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp

private const val DEGREES_IN_CIRCLE = 360
private const val PERCENTAGE_FACTOR = 10f
private const val STARTING_POSITION_OF_CIRCLE = -90f
private const val ANIMATION_DURATION = 1000

@Composable
fun UserScoreProgressBar(
    modifier: Modifier = Modifier,
    score: Float,
    radius: Dp = dimensionResource(id = R.dimen.progress_circle_radius),
    color: Color = Color.Green,
    strokeWidth: Dp = MaterialTheme.spacing.extraSmall,
    animDuration: Int = ANIMATION_DURATION,
) {

    var animationPlayed by remember { mutableStateOf(false) }
    val curPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) score else 0f,
        animationSpec = tween(
            durationMillis = animDuration
        )
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(radius * 2f)
    ) {
        Canvas(
            modifier = Modifier
                .size(radius * 2f)
                .padding(MaterialTheme.spacing.extraSmall)
        ) {
            drawArc(
                color = color,
                startAngle = STARTING_POSITION_OF_CIRCLE,
                sweepAngle = DEGREES_IN_CIRCLE * curPercentage.value,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(
            text = (score * PERCENTAGE_FACTOR).toString(),
            style = Typography.button
        )
    }
}

@Preview
@Composable
fun UserScoreProgressBarPreview() {
    UserScoreProgressBar(score = 0.57f)
}
