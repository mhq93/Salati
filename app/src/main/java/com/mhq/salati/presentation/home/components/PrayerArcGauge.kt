package com.mhq.salati.presentation.home.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.mhq.salati.presentation.theme.GaugeTrackBright
import com.mhq.salati.presentation.theme.GaugeTrackDim
import com.mhq.salati.presentation.theme.SalatiTheme
import kotlin.math.cos
import kotlin.math.sin

/**
 * Semicircular gauge spanning Fajr -> Isha, with a marker showing where
 * "now" falls within that span. fajrMinutes/ishaMinutes/nowMinutes are all
 * minutes-since-midnight (see parseTimeToMinutes in HomeContent.kt).
 */

@Composable
fun PrayerArcGauge(
    currentTimeLabel: String,
    fajrLabel: String,
    ishaLabel: String,
    fajrMinutes: Int,
    ishaMinutes: Int,
    nowMinutes: Int,
    modifier: Modifier = Modifier
) {
    val progressFraction = if (ishaMinutes > fajrMinutes) {
        ((nowMinutes - fajrMinutes).toFloat() / (ishaMinutes - fajrMinutes).toFloat())
            .coerceIn(0f, 1f)
    } else {
        0f
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp),
    ) {
        Canvas(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .height(160.dp)
        ) {
            val strokeWidth = 16.dp.toPx()
            val maxRadius = minOf(size.width, size.height * 2f) / 2f
            val radius = maxRadius - strokeWidth / 2f
            val center = Offset(size.width / 2f, size.height)

            // Dim full track — the whole Fajr-to-Isha span, always visible
            drawArc(
                color = GaugeTrackDim,
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // Bright progress arc — only the portion of the day already elapsed
            drawArc(
                brush = Brush.linearGradient(
                    colors = listOf(GaugeTrackBright, Color(0xFFFFE0A3))
                ),
                startAngle = 180f,
                sweepAngle = 180f * progressFraction,
                useCenter = false,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // Marker dot at the current position along the arc
            val markerAngleRad = Math.toRadians((180 + 180 * progressFraction).toDouble())
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
                color = GaugeTrackBright,
                radius = strokeWidth / 3.6f,
                center = markerPoint
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 28.dp)
        ) {
            Text(
                text = currentTimeLabel,
                color = Color.White,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 28.dp)
            )
        }
    }
}

@Preview
@Composable
private fun PrayerArcGaugePreview() {
    SalatiTheme() {
        PrayerArcGauge(
            currentTimeLabel = "Hi",
            fajrLabel = "Hi",
            ishaLabel = "Hi",
            fajrMinutes = 10,
            ishaMinutes = 10,
            nowMinutes = 10
        )
    }
}