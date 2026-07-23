package com.mhq.salati.presentation.qibla.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhq.salati.presentation.common.screens.AwaitingLocationPermissions
import com.mhq.salati.presentation.common.screens.LoadingContent
import com.mhq.salati.presentation.qibla.QiblaContract
import com.mhq.salati.presentation.qibla.components.LocationPill
import com.mhq.salati.presentation.theme.SalatiTheme

@Composable
fun QiblaContent(
    state: QiblaContract.State,
    onIntent: (QiblaContract.Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp)
    ) {
        Spacer(
            Modifier.height(24.dp)
        )

        when {
            state.isLoading -> {
                LoadingContent(modifier = Modifier.weight(1f))
            }

            state.errorMessage != null -> {
                QiblaErrorContent(
                    errorMessage = state.errorMessage,
                    sensorUnavailable = state.sensorUnavailable,
                    locationPermission = state.locationPermission,
                    onIntent = onIntent,
                    modifier = Modifier.weight(1f)
                )
            }

            state.qiblaBearing != null -> {
                Text(
                    text = "Direction to the Qibla",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(16.dp))

                LocationPill(locationName = "Located in Alexandria, Egypt")

                Spacer(Modifier.height(24.dp))

                QiblaSuccessContent(
                    deviceHeading = state.deviceHeading,
                    qiblaBearing = state.qiblaBearing,
                    compassAccuracy = state.compassAccuracy
                )
            }

            else -> {
                AwaitingLocationPermissions(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Preview
@Composable
private fun QiblaContentPreview() {
    SalatiTheme() {
        QiblaContent(
            state = QiblaContract.State(),
            onIntent = {}
        )
    }
}