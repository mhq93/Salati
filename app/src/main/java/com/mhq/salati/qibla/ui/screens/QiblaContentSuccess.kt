package com.mhq.salati.qibla.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.qibla.domain.model.CompassAccuracy
import com.mhq.salati.qibla.ui.components.CalibrationBanner
import com.mhq.salati.qibla.ui.components.LocationPill
import com.mhq.salati.qibla.ui.components.RotatingCompassDial
import com.mhq.salati.shared.ui.theme.CardBackground
import com.mhq.salati.shared.ui.theme.SalatiTheme

@Composable
fun QiblaContentSuccess(
    locationName: String?,
    deviceHeading: Float,
    qiblaBearing: Float,
    compassAccuracy: CompassAccuracy,
    onLocationPillClick: () -> Unit,
    onRecalibrateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
        ),

        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            LocationPill(
                locationName = locationName,
                onClick = onLocationPillClick,
                modifier = Modifier.fillMaxWidth().padding(4.dp)
            )
            Spacer(Modifier.height(24.dp))
            Text(
                text = "\uD83D\uDD4B",
                fontSize = 40.sp
            )
            Spacer(Modifier.height(16.dp))
            RotatingCompassDial(
                deviceHeading = deviceHeading,
                qiblaBearing = qiblaBearing,
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .aspectRatio(1f)
                    .padding(8.dp)
            )
            Spacer(Modifier.height(24.dp))
            CalibrationBanner(
                compassAccuracy = compassAccuracy,
                onRecalibrateClick = onRecalibrateClick,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

@Preview
@Composable
private fun QiblaContentSuccessPreview() {
    SalatiTheme() {
        QiblaContentSuccess(
            locationName = "",
            deviceHeading = 1.0f,
            qiblaBearing = 1.0f,
            compassAccuracy = CompassAccuracy.HIGH,
            onLocationPillClick = {},
            onRecalibrateClick = {}
        )
    }
}