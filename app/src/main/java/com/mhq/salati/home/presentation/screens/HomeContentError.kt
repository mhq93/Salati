package com.mhq.salati.home.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhq.salati.permissions.location.LocationPermissionState
import com.mhq.salati.home.presentation.contract.HomeContract
import com.mhq.salati.shared.presentation.theme.DarkGreen
import com.mhq.salati.shared.presentation.theme.SalatiTheme

@Composable
fun HomeContentError(
    errorMessage: String,
    locationPermissionState: LocationPermissionState,
    onIntent: (HomeContract.Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Error: $errorMessage",
            color = DarkGreen
        )

        when {
            locationPermissionState.permanentlyDenied -> {
                Button(
                    onClick = { onIntent(HomeContract.Intent.AccessAppSettings) },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Open Settings")
                }
            }

            locationPermissionState.servicesDisabled -> {
                Button(
                    onClick = { onIntent(HomeContract.Intent.AccessDeviceLocationSettings) },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Enable Location")
                }
            }

            else -> {
                Button(
                    onClick = { onIntent(HomeContract.Intent.Retry) },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Retry")
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeContentErrorPreview() {
    SalatiTheme() {
        HomeContentError(
            errorMessage = "errorMessage",
            locationPermissionState = LocationPermissionState(),
            onIntent = {}
        )
    }
}