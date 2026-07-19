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
import androidx.compose.ui.unit.dp

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
                Text(text = "Error: ${state.errorMessage}")
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
                // Rotation needed so the arrow always points toward Mecca,
                // regardless of which way the phone is currently facing.
                val rotationDegrees = state.qiblaBearing - state.deviceHeading

                Text(
                    text = "Qibla direction",
                    style = MaterialTheme.typography.titleMedium
                )

                CompassDial(
                    rotationDegrees = rotationDegrees,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .padding(32.dp)
                )

                Text(text = "Heading: ${state.deviceHeading.toInt()}°")
                Text(text = "Qibla bearing: ${state.qiblaBearing.toInt()}°")
            }

            else -> {
                Text(text = "Waiting for location permission…")
            }
        }
    }
}