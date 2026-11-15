package com.mhq.salati.shared.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mhq.salati.shared.ui.theme.SalatiTheme
import com.mhq.salati.shared.ui.theme.countdownTrack
import kotlin.math.cos
import kotlin.math.sin

/**
 * A compass needle that swings back and forth, settles on the small Kaaba at the top of the ring,
 * then starts searching again, for as long as the Qibla is loading.
 */
@Composable
fun CompassNeedleLoader(
    modifier: Modifier = Modifier,
    diameter: Dp = 200.dp
) {
    val ringColor = MaterialTheme.colorScheme.countdownTrack
    val tickColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.25f)
    val tipColor = MaterialTheme.colorScheme.tertiary
    val tailColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.35f)
    val surfaceColor = MaterialTheme.colorScheme.surface
    val kaabaColor = MaterialTheme.colorScheme.onSurface

    val transition = rememberInfiniteTransition(label = "compassNeedle")
    val swing by transition.animateFloat(
        initialValue = 0f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 2800
                0f at 0 using FastOutSlowInEasing
                70f at 500 using FastOutSlowInEasing
                -50f at 1000 using FastOutSlowInEasing
                28f at 1450 using FastOutSlowInEasing
                -12f at 1850 using FastOutSlowInEasing
                0f at 2300 using FastOutSlowInEasing
                0f at 2800
            }
        ),
        label = "swing"
    )

    Canvas(modifier = modifier.size(diameter)) {
        val scale = size.minDimension / 200.dp.toPx()
        val ringStroke = 10.dp.toPx() * scale
        val radius = (size.minDimension - ringStroke) / 2f
        val center = Offset(size.width / 2f, size.height / 2f)

        drawCircle(color = ringColor, radius = radius, center = center, style = Stroke(ringStroke))

        for (i in 0 until 12) {
            val a = Math.toRadians(i * 30.0)
            val dx = sin(a).toFloat()
            val dy = -cos(a).toFloat()
            val edge = radius - ringStroke / 2f - 4.dp.toPx() * scale
            val tickLength = (if (i % 3 == 0) 14.dp.toPx() else 8.dp.toPx()) * scale
            drawLine(
                color = tickColor,
                start = Offset(center.x + dx * (edge - tickLength), center.y + dy * (edge - tickLength)),
                end = Offset(center.x + dx * edge, center.y + dy * edge),
                strokeWidth = 2.dp.toPx() * scale
            )
        }

        val kaabaSize = 16.dp.toPx() * scale
        val kaabaTopLeft = Offset(center.x - kaabaSize / 2f, center.y - radius - kaabaSize / 2f)
        val halo = 3.dp.toPx() * scale
        drawRoundRect(
            color = surfaceColor,
            topLeft = Offset(kaabaTopLeft.x - halo, kaabaTopLeft.y - halo),
            size = Size(kaabaSize + halo * 2, kaabaSize + halo * 2),
            cornerRadius = CornerRadius(5.dp.toPx() * scale)
        )
        drawRoundRect(
            color = kaabaColor,
            topLeft = kaabaTopLeft,
            size = Size(kaabaSize, kaabaSize),
            cornerRadius = CornerRadius(3.dp.toPx() * scale)
        )
        drawRect(
            color = tipColor,
            topLeft = Offset(kaabaTopLeft.x, kaabaTopLeft.y + kaabaSize * 0.28f),
            size = Size(kaabaSize, kaabaSize * 0.14f)
        )

        val halfWidth = radius * 0.11f
        rotate(degrees = swing, pivot = center) {
            val tip = Path().apply {
                moveTo(center.x, center.y - radius * 0.72f)
                lineTo(center.x + halfWidth, center.y)
                lineTo(center.x - halfWidth, center.y)
                close()
            }
            val tail = Path().apply {
                moveTo(center.x, center.y + radius * 0.45f)
                lineTo(center.x + halfWidth, center.y)
                lineTo(center.x - halfWidth, center.y)
                close()
            }
            drawPath(path = tail, color = tailColor)
            drawPath(path = tip, color = tipColor)
        }

        drawCircle(color = surfaceColor, radius = 7.dp.toPx() * scale, center = center)
        drawCircle(color = tipColor, radius = 3.5f.dp.toPx() * scale, center = center)
    }
}

@Preview(showBackground = true)
@Composable
private fun CompassNeedleLoaderPreview() {
    SalatiTheme { CompassNeedleLoader() }
}