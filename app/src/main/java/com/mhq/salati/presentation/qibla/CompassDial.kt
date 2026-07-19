package com.mhq.salati.presentation.qibla

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CompassDial(
    rotationDegrees: Float,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(280.dp)) {
            val radius = size.minDimension / 2f
            val center = Offset(size.width / 2f, size.height / 2f)

            // Static outer dial — stays fixed, represents the phone's frame of reference
            drawCircle(
                color = Color(0xFFDDDDDD),
                radius = radius,
                center = center,
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 4f)
            )

            // Cardinal direction labels (N/E/S/W), fixed relative to the phone
            val labelRadius = radius - 24f
            listOf("N" to 0f, "E" to 90f, "S" to 180f, "W" to 270f).forEach { (label, angleDeg) ->
                val angleRad = Math.toRadians((angleDeg - 90).toDouble())
                val x = center.x + labelRadius * cos(angleRad).toFloat()
                val y = center.y + labelRadius * sin(angleRad).toFloat()
                drawContext.canvas.nativeCanvas.drawText(
                    label,
                    x,
                    y,
                    android.graphics.Paint().apply {
                        textAlign = android.graphics.Paint.Align.CENTER
                        textSize = 32f
                        color = android.graphics.Color.GRAY
                    }
                )
            }

            // Rotating arrow — points toward Mecca, rotates as the device heading changes
            rotate(degrees = rotationDegrees, pivot = center) {
                val arrowLength = radius - 40f

                drawLine(
                    color = Color(0xFF0F9D7C),
                    start = center,
                    end = Offset(center.x, center.y - arrowLength),
                    strokeWidth = 8f,
                    cap = androidx.compose.ui.graphics.StrokeCap.Round
                )

                // Arrowhead
                val headSize = 24f
                val tipY = center.y - arrowLength
                drawLine(
                    color = Color(0xFF0F9D7C),
                    start = Offset(center.x, tipY),
                    end = Offset(center.x - headSize / 2, tipY + headSize),
                    strokeWidth = 8f,
                    cap = androidx.compose.ui.graphics.StrokeCap.Round
                )
                drawLine(
                    color = Color(0xFF0F9D7C),
                    start = Offset(center.x, tipY),
                    end = Offset(center.x + headSize / 2, tipY + headSize),
                    strokeWidth = 8f,
                    cap = androidx.compose.ui.graphics.StrokeCap.Round
                )
            }

            // Center dot
            drawCircle(color = Color(0xFF0F9D7C), radius = 10f, center = center)
        }
    }
}