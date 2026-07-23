package com.mhq.salati.presentation.qibla.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.domain.model.qibla.CompassAccuracy
import com.mhq.salati.presentation.qibla.components.CalibrationBanner
import com.mhq.salati.presentation.qibla.components.RotatingCompassDial
import com.mhq.salati.presentation.theme.SalatiTheme

@Composable
fun QiblaSuccessContent(
    deviceHeading: Float,
    qiblaBearing: Float,
    compassAccuracy: CompassAccuracy,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = "\uD83D\uDD4B",
            fontSize = 40.sp
        )

        Spacer(
            Modifier.height(8.dp)
        )

        RotatingCompassDial(
            deviceHeading = deviceHeading,
            qiblaBearing = qiblaBearing,
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .aspectRatio(1f)
                .padding(8.dp)
        )

        Spacer(
            Modifier.height(20.dp)
        )

        Text(
            text = "The Qibla direction is ${"%.2f".format(qiblaBearing)}° from North, " +
                    "and the distance to the Kaaba is approximately 7397 kilometers.",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(
            Modifier.height(16.dp)
        )

        CalibrationBanner(
            compassAccuracy = compassAccuracy,
            onCalibrateClick = { /* TODO */ }
        )

        Spacer(
            Modifier.height(16.dp)
        )
    }
}

@Preview
@Composable
private fun QiblaSuccessContentPreview() {
    SalatiTheme() {
        QiblaSuccessContent(
            deviceHeading = 1.0f,
            qiblaBearing = 1.0f,
            compassAccuracy = CompassAccuracy.HIGH
        )
    }
}