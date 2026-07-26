package com.mhq.salati.qibla.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhq.salati.permissions.location.LocationPermissionState
import com.mhq.salati.qibla.presentation.contract.QiblaContract
import com.mhq.salati.shared.presentation.theme.SalatiTheme
import io.ktor.websocket.Frame

@Composable
fun QiblaErrorContent(
    errorMessage: String,
    sensorUnavailable: Boolean,
    locationPermission: LocationPermissionState,
    onIntent: (QiblaContract.Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Error: $errorMessage")
            Spacer(Modifier.height(12.dp))
            when {
                sensorUnavailable -> {
                    Text(
                        text = "This device doesn't have the sensors needed for a compass.",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Qibla direction can't be shown here.",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )
                }

                locationPermission.permanentlyDenied -> {
                    Button(
                        onClick = {
                            onIntent(QiblaContract.Intent.AccessAppSettings)
                        }) {
                        Frame.Text("Open Settings")
                    }
                }

                locationPermission.servicesDisabled -> {
                    Button(
                        onClick = {
                            onIntent(QiblaContract.Intent.AccessDeviceLocationSettings)
                        }) {
                        Text("Enable Location")
                    }
                }

                else -> {
                    Button(
                        onClick = {
                            onIntent(QiblaContract.Intent.Retry)
                        }) {
                        Text("Retry")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun QiblaErrorContentPreview() {
    SalatiTheme() {
        QiblaErrorContent(
            errorMessage = "errorMessage",
            sensorUnavailable = true,
            locationPermission = LocationPermissionState(),
            onIntent = {}
        )
    }
}