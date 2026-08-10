package com.mhq.salati.qibla.presentation.components


import android.graphics.BlurMaskFilter
import android.graphics.Paint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.shared.presentation.theme.AccentEmerald
import com.mhq.salati.shared.presentation.theme.DeepGunmetal
import com.mhq.salati.shared.presentation.theme.MetallicSilver
import com.mhq.salati.shared.presentation.theme.MutedSlate
import com.mhq.salati.shared.presentation.theme.ObsidianDark
import com.mhq.salati.shared.presentation.theme.PrimaryGreen
import com.mhq.salati.shared.presentation.theme.SalatiTheme
import kotlin.math.cos
import kotlin.math.sin

@OptIn(ExperimentalTextApi::class)
@Composable
fun RotatingCompassDial(
    deviceHeading: Float,
    qiblaBearing: Float,
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()

    val smoothedHeading by animateFloatAsState(
        targetValue = deviceHeading,
        animationSpec = spring(stiffness = 150f, dampingRatio = 0.85f),
        label = "smoothHeading"
    )

    // Pre-measured text layouts — computed once, not per animation frame.
    val degreeLabels = remember(textMeasurer) {
        (0 until 360 step 30).associateWith { angle ->
            textMeasurer.measure(
                text = angle.toString(),
                style = TextStyle(
                    color = MetallicSilver,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            )
        }
    }

    val cardinalLabels = remember(textMeasurer) {
        listOf("N" to 0f, "E" to 90f, "S" to 180f, "W" to 270f).map { (label, angleDeg) ->
            Triple(
                label, angleDeg,
                textMeasurer.measure(
                    text = label,
                    style = TextStyle(
                        color = if (label == "N") AccentEmerald else MetallicSilver,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Monospace
                    )
                )
            )
        }
    }

    // Reused native Paint objects — allocated once, not per frame.
    val chassisShadowPaint = remember {
        Paint().apply {
            color = android.graphics.Color.BLACK
            alpha = 140
            maskFilter = BlurMaskFilter(16f, BlurMaskFilter.Blur.OUTER)
        }
    }
    val pointerShadowPaint = remember {
        Paint().apply {
            color = android.graphics.Color.BLACK
            alpha = 160
            maskFilter = BlurMaskFilter(4f, BlurMaskFilter.Blur.NORMAL)
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(320.dp)
    ) {

        // LAYER 1 — static bezel + shadow. drawWithCache:
        // rebuilt only on size change, never on heading change.
        Canvas(
            modifier = Modifier
                .size(300.dp)
                .drawWithCache {
                    val radius = size.minDimension / 2f
                    val center = Offset(
                        size.width / 2f,
                        size.height / 2f
                    )
                    onDrawBehind {
                        drawContext.canvas.nativeCanvas.drawCircle(
                            center.x,
                            center.y,
                            radius,
                            chassisShadowPaint
                        )
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(DeepGunmetal, ObsidianDark),
                                center = center,
                                radius = radius
                            ),
                            radius = radius,
                            center = center
                        )
                        drawCircle(
                            Color.White.copy(alpha = 0.08f),
                            radius,
                            center,
                            style = Stroke(2.dp.toPx())
                        )
                        drawCircle(
                            Color.Black.copy(alpha = 0.5f),
                            radius - 4.dp.toPx(),
                            center,
                            style = Stroke(1.dp.toPx())
                        )
                    }
                }
        ) {}

        // LAYER 2 — rotating instrument field.
        // Redraws every frame (unavoidable, it's rotating),
        // but zero allocations.
        Canvas(
            modifier = Modifier.size(280.dp)
        ) {
            val radius = size.minDimension / 2f
            val center = Offset(
                size.width / 2f,
                size.height / 2f
            )

            rotate(
                degrees = -smoothedHeading,
                pivot = center
            ) {

                for (angle in 0 until 360 step 10) {
                    val angleRad = Math.toRadians((angle - 90).toDouble())
                    val isMajor = angle % 30 == 0
                    val tickLength = if (isMajor) 12.dp.toPx() else 6.dp.toPx()
                    val tickWidth = if (isMajor) 1.5.dp.toPx() else 1.dp.toPx()
                    val tickColor =
                        if (isMajor)
                            MetallicSilver.copy(alpha = 0.8f)
                        else 
                            MutedSlate.copy(alpha = 0.35f)

                    val startRadius = radius - 10.dp.toPx() - tickLength
                    val endRadius = radius - 10.dp.toPx()

                    drawLine(
                        color = tickColor,
                        start = Offset(
                            center.x + startRadius * cos(angleRad).toFloat(),
                            center.y + startRadius * sin(angleRad).toFloat()
                        ),
                        end = Offset(
                            center.x + endRadius * cos(angleRad).toFloat(),
                            center.y + endRadius * sin(angleRad).toFloat()
                        ),
                        strokeWidth = tickWidth,
                        cap = StrokeCap.Round
                    )

                    degreeLabels[angle]?.let { layout ->
                        if (angle % 90 != 0) {
                            val textRadius = startRadius - 16.dp.toPx()
                            val textX = center.x + textRadius * cos(angleRad).toFloat()
                            val textY = center.y + textRadius * sin(angleRad).toFloat()
                            rotate(degrees = angle - 90f, pivot = Offset(textX, textY)) {
                                drawText(
                                    textLayoutResult = layout,
                                    topLeft = Offset(
                                        textX - layout.size.width / 2f,
                                        textY - layout.size.height / 2f
                                    )
                                )
                            }
                        }
                    }
                }

                cardinalLabels.forEach { (_, angleDeg, layout) ->
                    val angleRad = Math.toRadians((angleDeg - 90).toDouble())
                    val textDistanceRadius = radius - 40.dp.toPx()
                    val labelX = center.x + textDistanceRadius * cos(angleRad).toFloat()
                    val labelY = center.y + textDistanceRadius * sin(angleRad).toFloat()
                    drawText(
                        textLayoutResult = layout,
                        topLeft = Offset(
                            labelX - layout.size.width / 2f,
                            labelY - layout.size.height / 2f
                        )
                    )
                }

                // Qibla pointer — old's shadow+solid-arrow look, reused Paint instead of allocating per frame.
                val markerAngleRad = Math.toRadians((qiblaBearing - 90).toDouble())
                val outerTrackRadius = radius - 8.dp.toPx()
                val tip = Offset(
                    center.x + outerTrackRadius * cos(markerAngleRad).toFloat(),
                    center.y + outerTrackRadius * sin(markerAngleRad).toFloat()
                )
                val baseLeft = Offset(
                    center.x + (outerTrackRadius - 22.dp.toPx()) * cos(markerAngleRad + 0.10).toFloat(),
                    center.y + (outerTrackRadius - 22.dp.toPx()) * sin(markerAngleRad + 0.10).toFloat()
                )
                val baseRight = Offset(
                    center.x + (outerTrackRadius - 22.dp.toPx()) * cos(markerAngleRad - 0.10).toFloat(),
                    center.y + (outerTrackRadius - 22.dp.toPx()) * sin(markerAngleRad - 0.10).toFloat()
                )
                val spineBase = Offset(
                    center.x + (outerTrackRadius - 16.dp.toPx()) * cos(markerAngleRad).toFloat(),
                    center.y + (outerTrackRadius - 16.dp.toPx()) * sin(markerAngleRad).toFloat()
                )

                val arrowPath = Path().apply {
                    moveTo(tip.x, tip.y); lineTo(baseLeft.x, baseLeft.y); lineTo(
                    spineBase.x,
                    spineBase.y
                ); lineTo(baseRight.x, baseRight.y); close()
                }

                drawContext.canvas.nativeCanvas.drawPath(
                    arrowPath.asAndroidPath(),
                    pointerShadowPaint
                )
                drawPath(path = arrowPath, color = Color.White)
                drawLine(
                    color = AccentEmerald,
                    start = tip,
                    end = spineBase,
                    strokeWidth = 1.5.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
        }

        // LAYER 3 — static center spindle + fixed top heading indicator.
        // Also, drawWithCache: draw once.
        Canvas(
            modifier = Modifier
                .size(280.dp)
                .drawWithCache {
                    val radius = size.minDimension / 2f
                    val center = Offset(
                        size.width / 2f,
                        size.height / 2f
                    )
                    onDrawBehind {
                        drawLine(
                            PrimaryGreen,
                            Offset(center.x, center.y - radius),
                            Offset(center.x, center.y - radius + 20.dp.toPx()),
                            strokeWidth = 2.dp.toPx()
                        )
                        drawCircle(
                            Color.Black.copy(alpha = 0.4f),
                            radius = 10.dp.toPx(),
                            center = center
                        )
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    AccentEmerald,
                                    AccentEmerald.copy(alpha = 0.6f)
                                ), center = center, radius = 6.dp.toPx()
                            ),
                            radius = 6.dp.toPx(), center = center
                        )
                        drawCircle(
                            Color.White.copy(alpha = 0.6f),
                            radius = 2.dp.toPx(),
                            center = center - Offset(1.5.dp.toPx(), 1.5.dp.toPx())
                        )
                    }
                }
        ) {}
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