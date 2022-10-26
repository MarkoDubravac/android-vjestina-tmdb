package agency.five.codebase.android.movieapp.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.Duration

@Composable
fun UserScoreProgressBar(
    modifier: Modifier = Modifier,
    score: Float,
    radius: Dp = 25.dp,
    color: Color = Color.Green,
    strokeWidth: Dp = 6.dp,
    animDuration: Int = 1000,
){

    var animationPlayed by remember { mutableStateOf(false) }
    val curPercentage = animateFloatAsState(
        targetValue = if(animationPlayed) score else 0f,
        animationSpec = tween(
            durationMillis = animDuration
        )
    )
    LaunchedEffect(key1 = true){
        animationPlayed = true
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(radius*2f)
    ){
        Canvas(modifier = modifier.size(radius*2f).padding(4.dp)){
            drawArc(
                color = color,
                -90f,
                360*curPercentage.value,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(
            text = (score*10f).toString(),
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun UserScoreProgressBarPreview(){
    UserScoreProgressBar(score = 0.75f)
}