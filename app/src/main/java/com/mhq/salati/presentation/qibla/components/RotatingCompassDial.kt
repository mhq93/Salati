package com.mhq.salati.presentation.qibla.components

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin
import androidx.core.graphics.toColorInt

@Composable
fun RotatingCompassDial(
    deviceHeading: Float,
    qiblaBearing: Float,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(280.dp)) {
            val radius = size.minDimension / 2f
            val center = Offset(size.width / 2f, size.height / 2f)

            // Static outer ring — represents the phone's frame, never rotates
            drawCircle(
                color = Color(0xFF3A3A3A),
                radius = radius,
                center = center,
                style = Stroke(width = 4f)
            )

            // Everything below rotates together as ONE group, by -deviceHeading —
            // this is what makes it a "rotating dial" compass instead of a fixed
            // dial with a rotating arrow: the labels themselves spin as you turn.
            rotate(degrees = -deviceHeading, pivot = center) {
                val tickRadius = radius - 8f
                val labelRadius = radius - 32f

                listOf(
                    "N" to 0f, "NE" to 45f, "E" to 90f, "SE" to 135f,
                    "S" to 180f, "SW" to 225f, "W" to 270f, "NW" to 315f
                ).forEach { (label, angleDeg) ->
                    val angleRad = Math.toRadians((angleDeg - 90).toDouble())
                    val tickStart = Offset(
                        center.x + (tickRadius - 12f) * cos(angleRad).toFloat(),
                        center.y + (tickRadius - 12f) * sin(angleRad).toFloat()
                    )
                    val tickEnd = Offset(
                        center.x + tickRadius * cos(angleRad).toFloat(),
                        center.y + tickRadius * sin(angleRad).toFloat()
                    )
                    drawLine(
                        color = Color(0xFF9E9E9E),
                        start = tickStart,
                        end = tickEnd,
                        strokeWidth = 4f,
                        cap = StrokeCap.Round
                    )

                    val labelX = center.x + labelRadius * cos(angleRad).toFloat()
                    val labelY = center.y + labelRadius * sin(angleRad).toFloat()
                    drawContext.canvas.nativeCanvas.drawText(
                        label,
                        labelX,
                        labelY,
                        Paint().apply {
                            textAlign = Paint.Align.CENTER
                            textSize = if (label.length == 1) 30f else 24f
                            color = if (label == "N") {
                                "#0F9D7C".toColorInt()
                                //android.graphics.Color.parseColor("#0F9D7C")
                            } else {
                                android.graphics.Color.LTGRAY
                            }
                            isFakeBoldText = label.length == 1
                        }
                    )
                }

                // Qibla marker — fixed at `qiblaBearing` WITHIN this rotating group,
                // so as the group spins with the phone, this marker sweeps around
                // and lands at the top only when the phone is actually facing Mecca.
                val markerAngleRad = Math.toRadians((qiblaBearing - 90).toDouble())
                val markerRadius = radius - 4f
                val markerTip = Offset(
                    center.x + markerRadius * cos(markerAngleRad).toFloat(),
                    center.y + markerRadius * sin(markerAngleRad).toFloat()
                )
                val markerBase1 = Offset(
                    center.x + (markerRadius - 20f) * cos(markerAngleRad + 0.08).toFloat(),
                    center.y + (markerRadius - 20f) * sin(markerAngleRad + 0.08).toFloat()
                )
                val markerBase2 = Offset(
                    center.x + (markerRadius - 20f) * cos(markerAngleRad - 0.08).toFloat(),
                    center.y + (markerRadius - 20f) * sin(markerAngleRad - 0.08).toFloat()
                )
                val path = Path().apply {
                    moveTo(markerTip.x, markerTip.y)
                    lineTo(markerBase1.x, markerBase1.y)
                    lineTo(markerBase2.x, markerBase2.y)
                    close()
                }
                drawPath(path, color = Color.White)
            }

            // Center dot — static, never rotates
            drawCircle(color = Color(0xFF0F9D7C), radius = 8f, center = center)
        }
    }
}