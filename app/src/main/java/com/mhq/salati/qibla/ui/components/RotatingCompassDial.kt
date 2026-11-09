package com.mhq.salati.qibla.ui.components

import android.graphics.BlurMaskFilter
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.shared.ui.theme.SalatiTheme
import kotlin.math.cos
import kotlin.math.sin
import android.graphics.Paint as AndroidPaint

/** Every measurement in the drawing code below is authored for a dial of this size. */
private val DialReferenceSize = 300.dp

/** Labels shrink with the dial but never below these sizes, so small dials stay readable. */
private const val MinDegreeLabelSp = 7f
private const val MinCardinalLabelSp = 13f

/**
 * Square compass dial that fills the largest square inside whatever bounded size the caller gives
 * it (`Modifier.size(...)`, or `fillMaxSize()` inside a bounded parent). It must NOT be placed in
 * an unbounded (scrolling) parent without a size.
 *
 * Everything drawn is authored for [DialReferenceSize] and scaled by `dialSize / DialReferenceSize`,
 * so ticks, labels and the needle keep their proportions on a phone, in landscape or on a tablet.
 */
@Composable
fun RotatingCompassDial(
    deviceHeading: Float,
    qiblaBearing: Float,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        val dialSize = minOf(maxWidth, maxHeight)
        CompassDial(
            deviceHeading = deviceHeading,
            qiblaBearing = qiblaBearing,
            scale = dialSize / DialReferenceSize,
            modifier = Modifier.size(dialSize)
        )
    }
}

@Composable
private fun CompassDial(
    deviceHeading: Float,
    qiblaBearing: Float,
    scale: Float,
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()
    val density = LocalDensity.current
    val shadowColor = MaterialTheme.colorScheme.scrim.toArgb()

    /** The dial is an instrument face: its labels follow the dial size, not the user's font scale. */
    val fixedFontDensity = remember(density.density) { Density(density.density, fontScale = 1f) }

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

    val baseContentColor = MaterialTheme.colorScheme.onSurface
    val outerRingStrokeColor = baseContentColor.copy(alpha = 0.25f)
    val centerGlassOverlayColor = baseContentColor.copy(alpha = 0.02f)

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

    /** Keyed on color, size and density so a theme switch or a resize rebuilds the blur. */
    val chassisShadowPaint = remember(shadowColor, scale, density) {
        AndroidPaint().apply {
            color = shadowColor
            alpha = 100
            maskFilter = BlurMaskFilter(
                with(density) { (8.dp.toPx() * scale).coerceAtLeast(1f) },
                BlurMaskFilter.Blur.OUTER
            )
        }
    }

    val pointerShadowPaint = remember(shadowColor, scale, density) {
        AndroidPaint().apply {
            color = shadowColor
            alpha = 140
            maskFilter = BlurMaskFilter(
                with(density) { (2.dp.toPx() * scale).coerceAtLeast(1f) },
                BlurMaskFilter.Blur.NORMAL
            )
        }
    }

    val degreeLabels = remember(textMeasurer, degreeLabelColor, scale, fixedFontDensity) {
        (0 until 360 step 30).associateWith { angle ->
            textMeasurer.measure(
                text = angle.toString().padStart(3, '0'),
                style = TextStyle(
                    color = degreeLabelColor,
                    fontSize = (9f * scale).coerceAtLeast(MinDegreeLabelSp).sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.Monospace
                ),
                density = fixedFontDensity
            )
        }
    }

    val cardinalLabels = remember(
        textMeasurer, cardinalLabelColor, northLabelColor, scale, fixedFontDensity
    ) {
        listOf("N" to 0f, "E" to 90f, "S" to 180f, "W" to 270f).map { (label, angleDeg) ->
            Triple(
                label, angleDeg,
                textMeasurer.measure(
                    text = label,
                    style = TextStyle(
                        color = if (label == "N") northLabelColor else cardinalLabelColor,
                        fontSize = (18f * scale).coerceAtLeast(MinCardinalLabelSp).sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Default
                    ),
                    density = fixedFontDensity
                )
            )
        }
    }

    Box(modifier = modifier) {
        /** LAYER 1: ROTATING DIAL FACE */
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { rotationZ = -smoothedHeading }
                .drawWithCache {
                    /** [value] dp of the reference dial, scaled to the real dial size. */
                    fun px(value: Float) = value.dp.toPx() * scale

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
                            style = Stroke(px(2f))
                        )
                        drawCircle(
                            color = outerRingStrokeColor,
                            radius = radius - px(4f),
                            center = center,
                            style = Stroke(px(1f))
                        )
                    }
                }
        ) {
            fun px(value: Float) = value.dp.toPx() * scale

            val radius = size.minDimension / 2f
            val center = Offset(size.width / 2f, size.height / 2f)

            /** Inner dashboard details */
            val centerLensRadius = radius * 0.45f
            val intermediateRingRadius = radius * 0.68f

            drawCircle(
                color = baseContentColor.copy(alpha = 0.10f),
                radius = intermediateRingRadius,
                center = center,
                style = Stroke(px(1f))
            )
            drawCircle(
                color = baseContentColor.copy(alpha = 0.16f),
                radius = centerLensRadius,
                center = center,
                style = Stroke(px(1.5f))
            )
            drawCircle(
                color = centerGlassOverlayColor,
                radius = centerLensRadius,
                center = center
            )

            /** Inner crosshairs */
            val crosshairStartOffset = px(18f)
            val verticalLength = centerLensRadius - px(4f)
            val crosshairColor = baseContentColor.copy(alpha = 0.10f)

            drawLine(color = crosshairColor, start = Offset(center.x, center.y - crosshairStartOffset), end = Offset(center.x, center.y - verticalLength), strokeWidth = px(1f))
            drawLine(color = crosshairColor, start = Offset(center.x, center.y + crosshairStartOffset), end = Offset(center.x, center.y + verticalLength), strokeWidth = px(1f))
            drawLine(color = crosshairColor, start = Offset(center.x - crosshairStartOffset, center.y), end = Offset(center.x - verticalLength, center.y), strokeWidth = px(1f))
            drawLine(color = crosshairColor, start = Offset(center.x + crosshairStartOffset, center.y), end = Offset(center.x + verticalLength, center.y), strokeWidth = px(1f))

            /** Tick scale */
            for (angle in 0 until 360 step 2) {
                val angleRad = Math.toRadians((angle - 90).toDouble())
                val isMajor = angle % 30 == 0
                val isMedium = !isMajor && angle % 10 == 0
                val isMinor = !isMajor && !isMedium && angle % 5 == 0

                val tickLength = when {
                    isMajor -> px(12f)
                    isMedium -> px(8f)
                    isMinor -> px(6f)
                    else -> px(4f)
                }
                val strokeWidth = if (isMajor) px(2f) else if (isMedium) px(1.5f) else px(1f)
                val alphaFactor = if (isMajor) 0.9f else if (isMedium) 0.6f else if (isMinor) 0.4f else 0.2f

                val outerBound = radius - px(5f)
                val innerBound = outerBound - tickLength

                drawLine(
                    color = baseContentColor.copy(alpha = alphaFactor),
                    start = Offset(center.x + outerBound * cos(angleRad).toFloat(), center.y + outerBound * sin(angleRad).toFloat()),
                    end = Offset(center.x + innerBound * cos(angleRad).toFloat(), center.y + innerBound * sin(angleRad).toFloat()),
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Round
                )

                /** Secondary notches on the inner ring */
                if (isMedium || isMajor) {
                    val innerRingTickLength = if (isMajor) px(5f) else px(3f)
                    val innerTickEnd = centerLensRadius + innerRingTickLength

                    drawLine(
                        color = baseContentColor.copy(alpha = if (isMajor) 0.4f else 0.2f),
                        start = Offset(center.x + centerLensRadius * cos(angleRad).toFloat(), center.y + centerLensRadius * sin(angleRad).toFloat()),
                        end = Offset(center.x + innerTickEnd * cos(angleRad).toFloat(), center.y + innerTickEnd * sin(angleRad).toFloat()),
                        strokeWidth = px(1f)
                    )
                }
            }

            /** Degree labels */
            degreeLabels.forEach { (angle, textLayoutResult) ->
                val angleRad = Math.toRadians((angle - 90).toDouble())
                val textRadius = radius - px(26f)
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

            /** Cardinal headings */
            cardinalLabels.forEach { (_, angleDeg, textLayoutResult) ->
                val angleRad = Math.toRadians((angleDeg - 90).toDouble())
                val textRadius = radius - px(46f)
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

        /** LAYER 2: ROTATING QIBLA NEEDLE */
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { rotationZ = qiblaBearing - smoothedHeading }
        ) {
            fun px(value: Float) = value.dp.toPx() * scale

            val radius = size.minDimension / 2f
            val center = Offset(size.width / 2f, size.height / 2f)
            val outerTrackRadius = radius - px(8f)
            val tip = Offset(center.x, center.y - outerTrackRadius)
            val baseLeft = Offset(center.x - px(14f), center.y - outerTrackRadius + px(22f))
            val baseRight = Offset(center.x + px(14f), center.y - outerTrackRadius + px(22f))
            val spineBase = Offset(center.x, center.y - outerTrackRadius + px(16f))
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
                strokeWidth = px(1.5f),
                cap = StrokeCap.Round
            )
        }

        /** LAYER 3: STATIC TOP INDICATOR AND CENTER SPINDLE */
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .drawWithCache {
                    fun px(value: Float) = value.dp.toPx() * scale

                    val radius = size.minDimension / 2f
                    val center = Offset(size.width / 2f, size.height / 2f)
                    onDrawBehind {
                        drawLine(
                            color = topIndicatorColor,
                            start = Offset(center.x, center.y - radius),
                            end = Offset(center.x, center.y - radius + px(20f)),
                            strokeWidth = px(2f)
                        )
                        drawCircle(
                            color = spindleShadowColor,
                            radius = px(10f),
                            center = center
                        )
                        drawCircle(
                            brush = spindleGradient,
                            radius = px(6f),
                            center = center
                        )
                        drawCircle(
                            color = spindleHighlightColor,
                            radius = px(2f),
                            center = center - Offset(px(1.5f), px(1.5f))
                        )
                    }
                }
        ) {}
    }
}

@Preview(name = "Reference size", showBackground = true)
@Composable
private fun RotatingCompassDialPreview() {
    SalatiTheme {
        RotatingCompassDial(
            deviceHeading = 0f,
            qiblaBearing = 45f,
            modifier = Modifier.size(300.dp)
        )
    }
}

@Preview(name = "Small dial", showBackground = true)
@Composable
private fun RotatingCompassDialSmallPreview() {
    SalatiTheme {
        RotatingCompassDial(
            deviceHeading = 0f,
            qiblaBearing = 45f,
            modifier = Modifier.size(170.dp)
        )
    }
}