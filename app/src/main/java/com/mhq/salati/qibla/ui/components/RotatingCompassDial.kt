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

    val smoothedHeading by animateFloatAsState(
        targetValue = deviceHeading,
        animationSpec = spring(stiffness = 150f, dampingRatio = 0.85f),
        label = "smoothHeading"
    )

    val degreeLabelColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
    val cardinalLabelColor = MaterialTheme.colorScheme.onSurface
    val northLabelColor = MaterialTheme.colorScheme.primary

    val compassBgGradient = Brush.radialGradient(
        colors = listOf(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.colorScheme.surface)
    )

    // Clean, normal Compose structural alphas
    val baseContentColor = MaterialTheme.colorScheme.onSurface
    val outerRingStrokeColor = baseContentColor.copy(alpha = 0.25f)
    val centerGlassOverlayColor = baseContentColor.copy(alpha = 0.02f)
    val tickColor = baseContentColor

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
            alpha = 100
            maskFilter = BlurMaskFilter(24f, BlurMaskFilter.Blur.OUTER)
        }
    }

    val pointerShadowPaint = remember {
        AndroidPaint().apply {
            color = shadowColor
            alpha = 140
            maskFilter = BlurMaskFilter(6f, BlurMaskFilter.Blur.NORMAL)
        }
    }

    val degreeLabels = remember(textMeasurer, degreeLabelColor) {
        (0 until 360 step 30).associateWith { angle ->
            textMeasurer.measure(
                text = angle.toString().padStart(3, '0'),
                style = TextStyle(
                    color = degreeLabelColor,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Medium,
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
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Default
                    )
                )
            )
        }
    }

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
                            color = baseContentColor.copy(alpha = 0.02f),
                            radius = radius,
                            center = center,
                            style = Stroke(2.dp.toPx())
                        )
                        drawCircle(
                            color = outerRingStrokeColor,
                            radius = radius - 4.dp.toPx(),
                            center = center,
                            style = Stroke(1.dp.toPx())
                        )
                    }
                }
        ) {
            val radius = size.minDimension / 2f
            val center = Offset(size.width / 2f, size.height / 2f)

            // Inner Dashboard Details
            val centerLensRadius = radius * 0.45f
            val intermediateRingRadius = radius * 0.68f

            // Fine Concentric Instrument Tracks using explicit explicit alpha levels
            drawCircle(
                color = baseContentColor.copy(alpha = 0.10f),
                radius = intermediateRingRadius,
                center = center,
                style = Stroke(1.dp.toPx())
            )
            drawCircle(
                color = baseContentColor.copy(alpha = 0.16f),
                radius = centerLensRadius,
                center = center,
                style = Stroke(1.5.dp.toPx())
            )
            drawCircle(
                color = centerGlassOverlayColor,
                radius = centerLensRadius,
                center = center
            )

            // Precise Inner Crosshairs
            val crosshairStartOffset = 18.dp.toPx()
            val verticalLength = centerLensRadius - 4.dp.toPx()
            val crosshairColor = baseContentColor.copy(alpha = 0.10f)

            // Vertical segments
            drawLine(color = crosshairColor, start = Offset(center.x, center.y - crosshairStartOffset), end = Offset(center.x, center.y - verticalLength), strokeWidth = 1.dp.toPx())
            drawLine(color = crosshairColor, start = Offset(center.x, center.y + crosshairStartOffset), end = Offset(center.x, center.y + verticalLength), strokeWidth = 1.dp.toPx())
            // Horizontal segments
            drawLine(color = crosshairColor, start = Offset(center.x - crosshairStartOffset, center.y), end = Offset(center.x - verticalLength, center.y), strokeWidth = 1.dp.toPx())
            drawLine(color = crosshairColor, start = Offset(center.x + crosshairStartOffset, center.y), end = Offset(center.x + verticalLength, center.y), strokeWidth = 1.dp.toPx())

            // High-Density Scale Loop
            for (angle in 0 until 360 step 2) {
                val angleRad = Math.toRadians((angle - 90).toDouble())
                val isMajor = angle % 30 == 0
                val isMedium = !isMajor && angle % 10 == 0
                val isMinor = !isMajor && !isMedium && angle % 5 == 0

                val tickLength = when {
                    isMajor -> 12.dp.toPx()
                    isMedium -> 8.dp.toPx()
                    isMinor -> 6.dp.toPx()
                    else -> 4.dp.toPx()
                }
                val strokeWidth = if (isMajor) 2.dp.toPx() else if (isMedium) 1.5.dp.toPx() else 1.dp.toPx()
                val alphaFactor = if (isMajor) 0.9f else if (isMedium) 0.6f else if (isMinor) 0.4f else 0.2f

                val outerBound = radius - 5.dp.toPx()
                val innerBound = outerBound - tickLength

                drawLine(
                    color = tickColor.copy(alpha = alphaFactor),
                    start = Offset(center.x + outerBound * cos(angleRad).toFloat(), center.y + outerBound * sin(angleRad).toFloat()),
                    end = Offset(center.x + innerBound * cos(angleRad).toFloat(), center.y + innerBound * sin(angleRad).toFloat()),
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Round
                )

                // Secondary Inner Technical Ring Notches
                if (isMedium || isMajor) {
                    val innerRingTickLength = if (isMajor) 5.dp.toPx() else 3.dp.toPx()
                    val innerTickStart = centerLensRadius
                    val innerTickEnd = centerLensRadius + innerRingTickLength

                    drawLine(
                        color = tickColor.copy(alpha = if (isMajor) 0.4f else 0.2f),
                        start = Offset(center.x + innerTickStart * cos(angleRad).toFloat(), center.y + innerTickStart * sin(angleRad).toFloat()),
                        end = Offset(center.x + innerTickEnd * cos(angleRad).toFloat(), center.y + innerTickEnd * sin(angleRad).toFloat()),
                        strokeWidth = 1.dp.toPx()
                    )
                }
            }
            // Render Degree Text Coordinates
            degreeLabels.forEach { (angle, textLayoutResult) ->
                val angleRad = Math.toRadians((angle - 90).toDouble())
                val textRadius = radius - 26.dp.toPx()
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
            // Render Cardinal Headings
            cardinalLabels.forEach { (_, angleDeg, textLayoutResult) ->
                val angleRad = Math.toRadians((angleDeg - 90).toDouble())
                val textRadius = radius - 46.dp.toPx()
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
        // LAYER 3: STATIC HARDWARE GUIDELINE & BEZEL SPINDLE
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
                            color = spindleHighlightColor,
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
    SalatiTheme {
        RotatingCompassDial(
            deviceHeading = 0f,
            qiblaBearing = 45f
        )
    }
}