package com.mhq.salati.presentation.qibla

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhq.salati.presentation.qibla.components.CalibrationBanner
import com.mhq.salati.presentation.qibla.components.RotatingCompassDial
import com.mhq.salati.presentation.theme.SalatiTheme

@Composable
fun QiblaContent(
    state: QiblaContract.State,
    onIntent: (QiblaContract.Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator()
            }

            state.errorMessage != null -> {
                Text(
                    text = "Error: ${state.errorMessage}"
                )
                when {
                    state.sensorUnavailable -> {
                        Text(
                            text = "This device doesn't have the sensors needed for a compass. " +
                                    "Qibla direction can't be shown here.",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    state.locationPermission.permanentlyDenied -> {
                        Button(
                            onClick = {
                                onIntent(
                                    QiblaContract.Intent.AccessAppSettings
                                )
                            }
                        ) {
                            Text("Open Settings")
                        }
                    }

                    state.locationPermission.servicesDisabled -> {
                        Button(
                            onClick = {
                                onIntent(
                                    QiblaContract.Intent.AccessDeviceLocationSettings
                                )
                            }
                        ) {
                            Text("Enable Location")
                        }
                    }

                    else -> {
                        Button(
                            onClick = {
                                onIntent(
                                    QiblaContract.Intent.Retry
                                )
                            }
                        ) {
                            Text("Retry")
                        }
                    }
                }
            }

            state.qiblaBearing != null -> {
                Text(
                    text = "\uD83D\uDD4B",
                    style = MaterialTheme.typography.displayMedium
                )

                RotatingCompassDial(
                    deviceHeading = state.deviceHeading,
                    qiblaBearing = state.qiblaBearing,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .padding(24.dp)
                )

                Text(
                    text = "${state.deviceHeading.toInt()}°",
                    style = MaterialTheme.typography.displaySmall
                )

                Text(
                    text = "Approximate Qibla direction: ${state.qiblaBearing.toInt()}°",
                    style = MaterialTheme.typography.bodyMedium
                )

                CalibrationBanner(accuracy = state.compassAccuracy)
            }

            else -> {
                Text(text = "Waiting for location permission…")
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