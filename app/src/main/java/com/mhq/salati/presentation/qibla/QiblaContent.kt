package com.mhq.salati.presentation.qibla

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhq.salati.presentation.home.screens.HomeAwaitingLocationPermissions
import com.mhq.salati.presentation.home.screens.HomeLoadingContent
import com.mhq.salati.presentation.qibla.components.CalibrationBanner
import com.mhq.salati.presentation.qibla.components.LocationPill
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
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(24.dp))

        Text(
            text = "Direction to the Qibla",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(16.dp))

        LocationPill(locationName = "Located in Alexandria, Egypt")

        Spacer(Modifier.height(24.dp))

        when {
            state.isLoading -> {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            state.errorMessage != null -> {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Error: ${state.errorMessage}")
                        Spacer(Modifier.height(12.dp))
                        when {
                            state.sensorUnavailable -> {
                                Text(
                                    text = "This device doesn't have the sensors needed for a compass. " +
                                            "Qibla direction can't be shown here.",
                                    style = MaterialTheme.typography.bodySmall,
                                    textAlign = TextAlign.Center
                                )
                            }

                            state.locationPermission.permanentlyDenied -> {
                                Button(
                                    onClick = {
                                        onIntent(QiblaContract.Intent.AccessAppSettings)
                                    }
                                ) {
                                    Text("Open Settings")
                                }
                            }

                            state.locationPermission.servicesDisabled -> {
                                Button(
                                    onClick = {
                                        onIntent(QiblaContract.Intent.AccessDeviceLocationSettings)
                                    }
                                ) {
                                    Text("Enable Location")
                                }
                            }

                            else -> {
                                Button(onClick = { onIntent(QiblaContract.Intent.Retry) }) {
                                    Text("Retry")
                                }
                            }
                        }
                    }
                }
            }

            state.qiblaBearing != null -> {
                Text(text = "\uD83D\uDED5", fontSize = 40.sp)

                Spacer(Modifier.height(8.dp))

                RotatingCompassDial(
                    deviceHeading = state.deviceHeading,
                    qiblaBearing = state.qiblaBearing,
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .aspectRatio(1f)
                        .padding(8.dp)
                )

                Spacer(Modifier.height(20.dp))

                Text(
                    text = "The Qibla direction is ${"%.2f".format(state.qiblaBearing)}° from North, " +
                            "and the distance to the Kaaba is approximately 7397 kilometers.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(16.dp))

                CalibrationBanner(
                    compassAccuracy = state.compassAccuracy,
                    onCalibrateClick = { /* TODO */ }
                )

                Spacer(Modifier.height(16.dp))
            }

            else -> {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Waiting for location permission…")
                }
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