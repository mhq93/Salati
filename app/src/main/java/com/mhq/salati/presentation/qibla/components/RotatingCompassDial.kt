package com.mhq.salati.presentation.qibla.components

import android.graphics.BlurMaskFilter
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.mhq.salati.presentation.theme.SalatiTheme
import com.mhq.salati.presentation.theme.AccentEmerald
import com.mhq.salati.presentation.theme.DeepGunmetal
import com.mhq.salati.presentation.theme.MetallicSilver
import com.mhq.salati.presentation.theme.MutedSlate
import com.mhq.salati.presentation.theme.ObsidianDark
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

    // Smooth physics smoothing for premium dial motion
    val smoothedHeading by animateFloatAsState(
        targetValue = deviceHeading,
        animationSpec = spring(stiffness = 150f, dampingRatio = 0.85f),
        label = "smoothHeading"
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(300.dp)) {
            val radius = size.minDimension / 2f
            val center = Offset(size.width / 2f, size.height / 2f)

            // 1. OUTER CHASSIS SHADOW (Underlay)
            drawContext.canvas.nativeCanvas.apply {
                val shadowPaint = android.graphics.Paint().apply {
                    color = android.graphics.Color.BLACK
                    alpha = 140
                    maskFilter = BlurMaskFilter(16.dp.toPx(), BlurMaskFilter.Blur.OUTER)
                }
                drawCircle(center.x, center.y, radius, shadowPaint)
            }

            // 2. LUXURY METALLIC OUTER BEZEL TRIMS
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
                color = Color.White.copy(alpha = 0.08f),
                radius = radius,
                center = center,
                style = Stroke(width = 2.dp.toPx())
            )
            drawCircle(
                color = Color.Black.copy(alpha = 0.5f),
                radius = radius - 4.dp.toPx(),
                center = center,
                style = Stroke(width = 1.dp.toPx())
            )

            // 3. INTERNAL BEZEL ROTATING INSTRUMENT FIELD
            rotate(degrees = -smoothedHeading, pivot = center) {

                // --- HIGH FIDELITY CHRONOGRAPH TICK MARKS ---
                // Draw tiny sub-degrees lines every 5 degrees to mimic a mechanical dive watch
                for (angle in 0 until 360 step 5) {
                    val angleRad = Math.toRadians((angle - 90).toDouble())
                    val isMajor = angle % 45 == 0
                    val isSecondary = angle % 15 == 0 && !isMajor

                    val tickLength = when {
                        isMajor -> 14.dp.toPx()
                        isSecondary -> 10.dp.toPx()
                        else -> 6.dp.toPx()
                    }
                    val tickWidth = when {
                        isMajor -> 2.dp.toPx()
                        else -> 1.dp.toPx()
                    }
                    val tickColor = when {
                        isMajor -> MetallicSilver.copy(alpha = 0.8f)
                        isSecondary -> MutedSlate.copy(alpha = 0.6f)
                        else -> MutedSlate.copy(alpha = 0.3f)
                    }

                    val startRadius = radius - 8.dp.toPx() - tickLength
                    val endRadius = radius - 8.dp.toPx()

                    val tickStart = Offset(
                        center.x + startRadius * cos(angleRad).toFloat(),
                        center.y + startRadius * sin(angleRad).toFloat()
                    )
                    val tickEnd = Offset(
                        center.x + endRadius * cos(angleRad).toFloat(),
                        center.y + endRadius * sin(angleRad).toFloat()
                    )

                    drawLine(
                        color = tickColor,
                        start = tickStart,
                        end = tickEnd,
                        strokeWidth = tickWidth,
                        cap = StrokeCap.Round
                    )
                }

                // --- LUXURY GEOMETRIC CARDINAL LABELS ---
                listOf(
                    "N" to 0f, "NE" to 45f, "E" to 90f, "SE" to 135f,
                    "S" to 180f, "SW" to 225f, "W" to 270f, "NW" to 315f
                ).forEach { (label, angleDeg) ->
                    val angleRad = Math.toRadians((angleDeg - 90).toDouble())
                    val textDistanceRadius = radius - 36.dp.toPx()

                    val labelX = center.x + textDistanceRadius * cos(angleRad).toFloat()
                    val labelY = center.y + textDistanceRadius * sin(angleRad).toFloat()

                    val isPrimary = label.length == 1
                    val styleColor = when {
                        label == "N" -> AccentEmerald
                        isPrimary -> MetallicSilver
                        else -> MutedSlate.copy(alpha = 0.8f)
                    }

                    val textLayoutResult = textMeasurer.measure(
                        text = label,
                        style = TextStyle(
                            color = styleColor,
                            fontSize = if (isPrimary) 15.sp else 11.sp,
                            fontWeight = if (isPrimary) FontWeight.ExtraBold else FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    )

                    // Draw layout centering text parameters cleanly
                    drawText(
                        textLayoutResult = textLayoutResult,
                        topLeft = Offset(
                            x = labelX - (textLayoutResult.size.width / 2f),
                            y = labelY - (textLayoutResult.size.height / 2f)
                        )
                    )
                }

                // --- LUXURY CHRONO PIECE: QIBLA MARKER ---
                val markerAngleRad = Math.toRadians((qiblaBearing - 90).toDouble())
                val outerTrackRadius = radius - 8.dp.toPx()

                val tip = Offset(
                    center.x + outerTrackRadius * cos(markerAngleRad).toFloat(),
                    center.y + outerTrackRadius * sin(markerAngleRad).toFloat()
                )
                val baseLeft = Offset(
                    center.x + (outerTrackRadius - 22.dp.toPx()) * cos(markerAngleRad + 0.07).toFloat(),
                    center.y + (outerTrackRadius - 22.dp.toPx()) * sin(markerAngleRad + 0.07).toFloat()
                )
                val baseRight = Offset(
                    center.x + (outerTrackRadius - 22.dp.toPx()) * cos(markerAngleRad - 0.07).toFloat(),
                    center.y + (outerTrackRadius - 22.dp.toPx()) * sin(markerAngleRad - 0.07).toFloat()
                )
                val spineBase = Offset(
                    center.x + (outerTrackRadius - 16.dp.toPx()) * cos(markerAngleRad).toFloat(),
                    center.y + (outerTrackRadius - 16.dp.toPx()) * sin(markerAngleRad).toFloat()
                )

                val arrowPath = Path().apply {
                    moveTo(tip.x, tip.y)
                    lineTo(baseLeft.x, baseLeft.y)
                    lineTo(spineBase.x, spineBase.y)
                    lineTo(baseRight.x, baseRight.y)
                    close()
                }

                // Pointer Blur Drop Shadow Underlay
                drawContext.canvas.nativeCanvas.apply {
                    val pointerShadow = android.graphics.Paint().apply {
                        color = android.graphics.Color.BLACK
                        alpha = 160
                        maskFilter = BlurMaskFilter(4.dp.toPx(), BlurMaskFilter.Blur.NORMAL)
                    }
                    drawPath(arrowPath.asAndroidPath(), pointerShadow)
                }

                // Render High-Contrast Luxury Pointer Tip
                drawPath(path = arrowPath, color = Color.White)

                // Add a structural centerline pin stripe across the arrow vector
                drawLine(
                    color = AccentEmerald,
                    start = tip,
                    end = spineBase,
                    strokeWidth = 1.5.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }

            // 4. METALLIC CENTER SPINDLE AXIS PIN
            drawCircle(
                color = Color.Black.copy(alpha = 0.4f),
                radius = 10.dp.toPx(),
                center = center
            )
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        AccentEmerald,
                        AccentEmerald.copy(alpha = 0.6f)
                    ), center = center, radius = 6.dp.toPx()
                ), radius = 6.dp.toPx(), center = center
            )
            drawCircle(
                color = Color.White.copy(alpha = 0.6f),
                radius = 2.dp.toPx(),
                center = center - Offset(
                    1.5.dp.toPx(),
                    1.5.dp.toPx()
                )
            ) // Off-center bezel metallic glint reflection)
        }
    }
}

@Preview
@Composable
private fun RotatingCompassDialPreview() {
    SalatiTheme() {
        RotatingCompassDial(
            deviceHeading = 1.0f,
            qiblaBearing = 1.0f
        )
    }
}