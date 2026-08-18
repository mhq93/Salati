package com.mhq.salati.qibla.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mhq.salati.permissions.location.LocationPermissionState
import com.mhq.salati.qibla.presentation.contract.QiblaContract
import com.mhq.salati.settings.presentation.components.QiblaSensorUnavailableError
import com.mhq.salati.shared.presentation.errors.LocationPermissionPermanentlyDeniedError
import com.mhq.salati.shared.presentation.errors.LocationPermissionRequiredError
import com.mhq.salati.shared.presentation.errors.LocationServicesDisabledError
import com.mhq.salati.shared.presentation.theme.SalatiTheme

@Composable
fun QiblaContentError(
    errorMessage: String,
    sensorUnavailable: Boolean,
    locationPermissionState: LocationPermissionState,
    onIntent: (QiblaContract.Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    when {
        sensorUnavailable -> {
            QiblaSensorUnavailableError(
                errorMessage = errorMessage
            )
        }

        locationPermissionState.permanentlyDenied -> {
            LocationPermissionPermanentlyDeniedError(
                errorMessage = errorMessage,
                onOpenSettingsClicked = { onIntent(QiblaContract.Intent.AccessAppSettings) }
            )
        }

        locationPermissionState.servicesDisabled -> {
            LocationServicesDisabledError(
                errorMessage = errorMessage,
                onEnableLocationClicked = { onIntent(QiblaContract.Intent.AccessDeviceLocationSettings) }
            )
        }

        else -> {
            LocationPermissionRequiredError(
                errorMessage = errorMessage,
                onRetryClicked = { onIntent(QiblaContract.Intent.RetryClicked) }
            )
        }
    }
}

@Preview
@Composable
private fun QiblaContentErrorPreview() {
    SalatiTheme() {
        QiblaContentError(
            errorMessage = "errorMessage",
            sensorUnavailable = true,
            locationPermissionState = LocationPermissionState(),
            onIntent = {}
        )
    }
}