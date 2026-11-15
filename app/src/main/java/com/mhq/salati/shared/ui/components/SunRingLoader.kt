package com.mhq.salati.shared.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mhq.salati.shared.ui.theme.SalatiTheme
import com.mhq.salati.shared.ui.theme.countdownSweep
import com.mhq.salati.shared.ui.theme.countdownTrack
import kotlin.math.cos
import kotlin.math.sin

/**
 * A sun traveling around the countdown ring while prayer times load. The arc behind the sun
 * grows and shrinks as it goes, and the sun's glow pulses.
 */
@Composable
fun SunRingLoader(
    modifier: Modifier = Modifier,
    diameter: Dp = 200.dp
) {
    val trackColor = MaterialTheme.colorScheme.countdownTrack
    val sweepBrush = MaterialTheme.colorScheme.countdownSweep
    val sunOuter = MaterialTheme.colorScheme.surface
    val sunInner = MaterialTheme.colorScheme.tertiary

    val transition = rememberInfiniteTransition(label = "sunRing")
    val head by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(2600, easing = LinearEasing)),
        label = "head"
    )
    val length by transition.animateFloat(
        initialValue = 50f,
        targetValue = 230f,
        animationSpec = infiniteRepeatable(
            animation = tween(1300, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "length"
    )
    val glow by transition.animateFloat(
        initialValue = 0.15f,
        targetValue = 0.40f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    Canvas(modifier = modifier.size(diameter)) {
        val scale = size.minDimension / 220.dp.toPx()
        val strokeWidth = 16.dp.toPx() * scale
        val radius = (size.minDimension - strokeWidth) / 2f
        val center = Offset(size.width / 2f, size.height / 2f)
        val topLeft = Offset(center.x - radius, center.y - radius)
        val arcSize = Size(radius * 2, radius * 2)

        drawArc(
            color = trackColor,
            startAngle = -90f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = topLeft,
            size = arcSize,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )
        drawArc(
            brush = sweepBrush,
            startAngle = -90f + head - length,
            sweepAngle = length,
            useCenter = false,
            topLeft = topLeft,
            size = arcSize,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )

        val angle = Math.toRadians((-90f + head).toDouble())
        val sun = Offset(
            center.x + radius * cos(angle).toFloat(),
            center.y + radius * sin(angle).toFloat()
        )
        drawCircle(color = sunInner.copy(alpha = glow), radius = strokeWidth * 1.15f, center = sun)
        drawCircle(color = sunOuter, radius = strokeWidth / 2f, center = sun)
        drawCircle(color = sunInner, radius = strokeWidth / 3.2f, center = sun)
    }
}

@Preview(showBackground = true)
@Composable
private fun SunRingLoaderPreview() {
    SalatiTheme { SunRingLoader() }
}