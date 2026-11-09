package com.mhq.salati.home.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.R
import com.mhq.salati.shared.ui.theme.SalatiTheme
import com.mhq.salati.shared.ui.theme.countdownSweep
import com.mhq.salati.shared.ui.theme.countdownTrack
import java.time.Duration
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun PrayerCountdownRing(
    nextPrayerName: String,
    remaining: Duration,
    progress: Float,
    modifier: Modifier = Modifier
) {
    val countdownLabel = remaining.toCountdownLabel()
    val progressFraction = progress.coerceIn(0f, 1f)

    val trackColor = MaterialTheme.colorScheme.countdownTrack
    val progressBrush = MaterialTheme.colorScheme.countdownSweep
    val markerOuterColor = MaterialTheme.colorScheme.surface
    val markerInnerColor = MaterialTheme.colorScheme.tertiary
    val textColor = MaterialTheme.colorScheme.onSurface

    BoxWithConstraints(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        /** 1f at the original 220dp design size, shrinks only when space is tighter */
        val scale = (minOf(maxWidth, maxHeight) / 220.dp).coerceIn(0.5f, 1f)

        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = (16 * scale).dp.toPx()
            val radius = (minOf(size.width, size.height) - strokeWidth) / 2f
            val center = Offset(size.width / 2f, size.height / 2f)
            val topLeft = Offset(center.x - radius, center.y - radius)
            val arcSize = Size(radius * 2, radius * 2)

            drawArc(
                color = trackColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            drawArc(
                brush = progressBrush,
                startAngle = -90f,
                sweepAngle = 360f * progressFraction,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            val markerAngleRad = Math.toRadians((-90 + 360 * progressFraction).toDouble())
            val markerPoint = Offset(
                center.x + radius * cos(markerAngleRad).toFloat(),
                center.y + radius * sin(markerAngleRad).toFloat()
            )
            drawCircle(
                color = markerOuterColor,
                radius = strokeWidth / 2f,
                center = markerPoint
            )
            drawCircle(
                color = markerInnerColor,
                radius = strokeWidth / 3.2f,
                center = markerPoint
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.next_prayer, nextPrayerName),
                color = textColor,
                fontSize = (16 * scale).sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height((8 * scale).dp))
            Text(
                text = countdownLabel,
                color = textColor,
                fontSize = (34 * scale).sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@SuppressLint("DefaultLocale")
private fun Duration.toCountdownLabel(): String {
    val totalSeconds = this.seconds
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}

@Preview
@Composable
private fun PrayerCountdownRingPreview() {
    SalatiTheme {
        PrayerCountdownRing(
            nextPrayerName = "Fajr",
            remaining = Duration.ofSeconds(3600),
            progress = 0.4f,
            modifier = Modifier.size(220.dp)
        )
    }
}