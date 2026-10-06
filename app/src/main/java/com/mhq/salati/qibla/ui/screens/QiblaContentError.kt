package com.mhq.salati.qibla.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mhq.salati.qibla.presentation.contract.QiblaContract
import com.mhq.salati.qibla.ui.errors.QiblaSensorUnavailableError
import com.mhq.salati.shared.ui.errors.CatchAllError
import com.mhq.salati.shared.ui.errors.LocationPermissionPermanentlyDeniedError
import com.mhq.salati.shared.ui.errors.LocationPermissionRequiredError
import com.mhq.salati.shared.ui.errors.LocationServicesDisabledError

@Composable
fun QiblaContentError(
    errorMessage: String,
    sensorUnavailable: Boolean,
    isLocationPermissionPermanentlyDenied: Boolean,
    areLocationServicesDisabled: Boolean,
    isLocationPermissionRequired: Boolean,
    onIntent: (QiblaContract.Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    when {
        areLocationServicesDisabled -> {
            LocationServicesDisabledError(
                errorMessage = errorMessage,
                onEnableLocationClicked = { onIntent(QiblaContract.Intent.AccessDeviceLocationSettings) },
                modifier = modifier
            )
        }
        isLocationPermissionRequired -> {
            LocationPermissionRequiredError(
                errorMessage = errorMessage,
                onRetryClicked = { onIntent(QiblaContract.Intent.RetryClicked) },
                modifier = modifier
            )
        }
        isLocationPermissionPermanentlyDenied -> {
            LocationPermissionPermanentlyDeniedError(
                errorMessage = errorMessage,
                onOpenSettingsClicked = { onIntent(QiblaContract.Intent.AccessAppSettings) },
                modifier = modifier
            )
        }
        sensorUnavailable -> {
            QiblaSensorUnavailableError(
                errorMessage = errorMessage,
                modifier = modifier
            )
        }
        else -> {
            CatchAllError(
                errorMessage = errorMessage,
                onRetryClicked = { onIntent(QiblaContract.Intent.RetryClicked) },
                modifier = modifier
            )
        }
    }
}