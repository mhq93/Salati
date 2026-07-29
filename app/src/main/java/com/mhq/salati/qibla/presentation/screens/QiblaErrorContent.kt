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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhq.salati.R
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.error, errorMessage)
            )
            Spacer(
                Modifier.height(12.dp)
            )
            when {
                sensorUnavailable -> {
                    Text(
                        text = stringResource(R.string.this_device_doesn_t_have_the_sensors_needed_for_a_compass),
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = stringResource(R.string.qibla_direction_can_t_be_shown_here),
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )
                }

                locationPermission.permanentlyDenied -> {
                    Button(
                        onClick = {
                            onIntent(QiblaContract.Intent.AccessAppSettings)
                        }
                    ) {
                        Frame.Text(stringResource(R.string.open_settings))
                    }
                }

                locationPermission.servicesDisabled -> {
                    Button(
                        onClick = {
                            onIntent(QiblaContract.Intent.AccessDeviceLocationSettings)
                        }
                    ) {
                        Text(stringResource(R.string.enable_location))
                    }
                }

                else -> {
                    Button(
                        onClick = {
                            onIntent(QiblaContract.Intent.Retry)
                        }
                    ) {
                        Text(stringResource(R.string.retry))
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