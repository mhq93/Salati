package com.mhq.salati.qibla.ui.components

import android.graphics.BlurMaskFilter
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.shared.ui.theme.SalatiTheme
import kotlin.math.cos
import kotlin.math.sin
import android.graphics.Paint as AndroidPaint

@Composable
fun RotatingCompassDial(
    deviceHeading: Float,
    qiblaBearing: Float,
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()
    val shadowColor = MaterialTheme.colorScheme.scrim.toArgb()

    // Restoring the dampened hardware smoothing layer
    val smoothedHeading by animateFloatAsState(
        targetValue = deviceHeading,
        animationSpec = spring(stiffness = 150f, dampingRatio = 0.85f),
        label = "smoothHeading"
    )

    val degreeLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
    val cardinalLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
    val northLabelColor = MaterialTheme.colorScheme.secondary

    val compassBgGradient = Brush.radialGradient(
        colors = listOf(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.colorScheme.surface)
    )
    val innerRingColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
    val innerRingStrokeColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)

    val pointerColor = MaterialTheme.colorScheme.onSurface
    val pointerSpineColor = MaterialTheme.colorScheme.secondary
    val topIndicatorColor = MaterialTheme.colorScheme.primary
    val spindleShadowColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
    val spindleHighlightColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)

    val spindleGradient = Brush.radialGradient(
        colors = listOf(
            MaterialTheme.colorScheme.secondary,
            MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f)
        )
    )

    val chassisShadowPaint = remember {
        AndroidPaint().apply {
            color = shadowColor
            alpha = 140
            maskFilter = BlurMaskFilter(16f, BlurMaskFilter.Blur.OUTER)
        }
    }

    val pointerShadowPaint = remember {
        AndroidPaint().apply {
            color = shadowColor
            alpha = 160
            maskFilter = BlurMaskFilter(4f, BlurMaskFilter.Blur.NORMAL)
        }
    }

    val degreeLabels = remember(textMeasurer, degreeLabelColor) {
        (0 until 360 step 30).associateWith { angle ->
            textMeasurer.measure(
                text = angle.toString(),
                style = TextStyle(
                    color = degreeLabelColor,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            )
        }
    }

    val cardinalLabels = remember(textMeasurer, cardinalLabelColor, northLabelColor) {
        listOf("N" to 0f, "E" to 90f, "S" to 180f, "W" to 270f).map { (label, angleDeg) ->
            Triple(
                label, angleDeg,
                textMeasurer.measure(
                    text = label,
                    style = TextStyle(
                        color = if (label == "N") northLabelColor else cardinalLabelColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Monospace
                    )
                )
            )
        }
    }

    // Fixed: Standardizing on a single size boundary constraint (300.dp) so coordinate centers always coincide perfectly
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(300.dp)
    ) {
        // LAYER 1: ROTATING DIAL FACE
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { rotationZ = -smoothedHeading }
                .drawWithCache {
                    val radius = size.minDimension / 2f
                    val center = Offset(size.width / 2f, size.height / 2f)
                    onDrawBehind {
                        drawContext.canvas.nativeCanvas.drawCircle(
                            center.x, center.y, radius, chassisShadowPaint
                        )
                        drawCircle(
                            brush = compassBgGradient,
                            radius = radius,
                            center = center
                        )
                        drawCircle(
                            color = innerRingColor,
                            radius = radius,
                            center = center,
                            style = Stroke(2.dp.toPx())
                        )
                        drawCircle(
                            color = innerRingStrokeColor,
                            radius = radius - 4.dp.toPx(),
                            center = center,
                            style = Stroke(1.dp.toPx())
                        )
                    }
                }
        ) {
            val radius = size.minDimension / 2f
            val center = Offset(size.width / 2f, size.height / 2f)

            degreeLabels.forEach { (angle, textLayoutResult) ->
                val angleRad = Math.toRadians((angle - 90).toDouble())
                val textRadius = radius - 24.dp.toPx()
                val x = center.x + textRadius * cos(angleRad).toFloat()
                val y = center.y + textRadius * sin(angleRad).toFloat()

                drawText(
                    textLayoutResult = textLayoutResult,
                    topLeft = Offset(
                        x - textLayoutResult.size.width / 2f,
                        y - textLayoutResult.size.height / 2f
                    )
                )
            }

            cardinalLabels.forEach { (label, angleDeg, textLayoutResult) ->
                val angleRad = Math.toRadians((angleDeg - 90).toDouble())
                val textRadius = radius - 40.dp.toPx()
                val x = center.x + textRadius * cos(angleRad).toFloat()
                val y = center.y + textRadius * sin(angleRad).toFloat()

                drawText(
                    textLayoutResult = textLayoutResult,
                    topLeft = Offset(
                        x - textLayoutResult.size.width / 2f,
                        y - textLayoutResult.size.height / 2f
                    )
                )
            }
        }

        // LAYER 2: ROTATING QIBLA NEEDLE
        // Keeps geometry math completely isolated from dial face math
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { rotationZ = qiblaBearing - smoothedHeading }
        ) {
            val radius = size.minDimension / 2f
            val center = Offset(size.width / 2f, size.height / 2f)

            val outerTrackRadius = radius - 8.dp.toPx()
            val tip = Offset(center.x, center.y - outerTrackRadius)
            val baseLeft =
                Offset(center.x - 14.dp.toPx(), center.y - outerTrackRadius + 22.dp.toPx())
            val baseRight =
                Offset(center.x + 14.dp.toPx(), center.y - outerTrackRadius + 22.dp.toPx())
            val spineBase = Offset(center.x, center.y - outerTrackRadius + 16.dp.toPx())

            val arrowPath = Path().apply {
                moveTo(tip.x, tip.y)
                lineTo(baseLeft.x, baseLeft.y)
                lineTo(spineBase.x, spineBase.y)
                lineTo(baseRight.x, baseRight.y)
                close()
            }

            drawContext.canvas.nativeCanvas.drawPath(arrowPath.asAndroidPath(), pointerShadowPaint)
            drawPath(path = arrowPath, color = pointerColor)
            drawLine(
                color = pointerSpineColor,
                start = tip,
                end = spineBase,
                strokeWidth = 1.5.dp.toPx(),
                cap = StrokeCap.Round
            )
        }

        // LAYER 3: STATIC HARDWARE GUIDELINE & SPINDLE
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .drawWithCache {
                    val radius = size.minDimension / 2f
                    val center = Offset(size.width / 2f, size.height / 2f)
                    onDrawBehind {
                        drawLine(
                            color = topIndicatorColor,
                            start = Offset(center.x, center.y - radius),
                            end = Offset(center.x, center.y - radius + 20.dp.toPx()),
                            strokeWidth = 2.dp.toPx()
                        )
                        drawCircle(
                            color = spindleShadowColor,
                            radius = 10.dp.toPx(),
                            center = center
                        )
                        drawCircle(
                            brush = spindleGradient,
                            radius = 6.dp.toPx(), center = center
                        )
                        drawCircle(
                            color =
                                spindleHighlightColor,
                            radius = 2.dp.toPx(),
                            center = center - Offset(1.5.dp.toPx(), 1.5.dp.toPx())
                        )
                    }
                }) {}
    }
}

@Preview
@Composable
private fun RotatingCompassDialPreview() {
    SalatiTheme() {
        RotatingCompassDial(
            deviceHeading = 1f,
            qiblaBearing = 1f
        )
    }
}