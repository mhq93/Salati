package com.mhq.salati.home.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.shared.presentation.theme.AccentEmerald
import com.mhq.salati.shared.presentation.theme.AccentGold
import com.mhq.salati.shared.presentation.theme.GaugeTrackDim
import com.mhq.salati.shared.presentation.theme.SalatiTheme
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun PrayerCountdownRing(
    nextPrayerName: String,
    spanStartMillis: Long,
    spanEndMillis: Long,
    remainingMillis: Long,
    modifier: Modifier = Modifier
) {

    val countdownLabel = remainingMillis.toCountdownLabel()
    val nowMillis = spanEndMillis - remainingMillis

    val progressFraction = if (spanEndMillis > spanStartMillis) {
        ((nowMillis - spanStartMillis).toFloat() / (spanEndMillis - spanStartMillis).toFloat())
            .coerceIn(0f, 1f)
    } else {
        0f
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(220.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 16.dp.toPx()
            val radius = (minOf(size.width, size.height) - strokeWidth) / 2f
            val center = Offset(size.width / 2f, size.height / 2f)
            val topLeft = Offset(center.x - radius, center.y - radius)
            val arcSize = Size(radius * 2, radius * 2)

            // Dim full track — the entire prayer-to-prayer span
            drawArc(
                color = GaugeTrackDim,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // Bright progress arc — elapsed portion since the previous prayer
            drawArc(
                brush = Brush.sweepGradient(
                    colors = listOf(AccentGold, AccentEmerald, AccentGold),
                    center = center
                ),
                startAngle = -90f,
                sweepAngle = 360f * progressFraction,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // Marker dot at current position
            val markerAngleRad = Math.toRadians((-90 + 360 * progressFraction).toDouble())
            val markerPoint = Offset(
                center.x + radius * cos(markerAngleRad).toFloat(),
                center.y + radius * sin(markerAngleRad).toFloat()
            )
            drawCircle(
                color = Color.White,
                radius = strokeWidth / 2.4f,
                center = markerPoint
            )
            drawCircle(
                color = AccentGold,
                radius = strokeWidth / 3.6f,
                center = markerPoint
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Next Prayer: $nextPrayerName",
                color = AccentGold,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = countdownLabel,
                color = AccentGold,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@SuppressLint("DefaultLocale")
private fun Long.toCountdownLabel(): String {
    val totalSeconds = this / 1000
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}

@Preview
@Composable
private fun PrayerCountdownRingPreview() {
    SalatiTheme() {
        PrayerCountdownRing(
            nextPrayerName = "Fajr",
            spanStartMillis = 1L,
            spanEndMillis = 1L,
            remainingMillis = 1L
        )
    }
}