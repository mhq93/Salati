package com.mhq.salati.qibla.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mhq.salati.qibla.presentation.contract.QiblaContract
import com.mhq.salati.settings.presentation.components.QiblaSensorUnavailableError
import com.mhq.salati.shared.presentation.errors.CatchAllError
import com.mhq.salati.shared.presentation.errors.LocationPermissionPermanentlyDeniedError
import com.mhq.salati.shared.presentation.errors.LocationPermissionRequiredError
import com.mhq.salati.shared.presentation.errors.LocationServicesDisabledError

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

//package com.mhq.salati.qibla.presentation.screens
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.height
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.unit.dp
//import com.mhq.salati.R
//import com.mhq.salati.home.presentation.contract.HomeContract
//import com.mhq.salati.qibla.presentation.contract.QiblaContract
//import com.mhq.salati.settings.presentation.components.QiblaSensorUnavailableError
//import com.mhq.salati.shared.presentation.errors.CatchAllError
//import com.mhq.salati.shared.presentation.errors.LocationPermissionPermanentlyDeniedError
//import com.mhq.salati.shared.presentation.errors.LocationPermissionRequiredError
//import com.mhq.salati.shared.presentation.errors.LocationServicesDisabledError
//import com.mhq.salati.shared.presentation.theme.DarkGreen
//
//@Composable
//fun QiblaContentError(
//    errorMessage: String,
//    sensorUnavailable: Boolean,
//    isLocationPermissionPermanentlyDenied: Boolean,
//    areLocationServicesDisabled: Boolean,
//    isLocationPermissionRequired: Boolean,
//    onIntent: (QiblaContract.Intent) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    when {
//
//        areLocationServicesDisabled -> {
//            LocationServicesDisabledError(
//                errorMessage = errorMessage,
//                onEnableLocationClicked = { onIntent(QiblaContract.Intent.AccessDeviceLocationSettings) },
//                modifier = modifier
//            )
//        }
//
//        isLocationPermissionRequired -> {
//            LocationPermissionRequiredError(
//                errorMessage = errorMessage,
//                onRetryClicked = { onIntent(QiblaContract.Intent.RetryClicked) },
//                modifier = modifier
//            )
//        }
//
//        isLocationPermissionPermanentlyDenied -> {
//            LocationPermissionPermanentlyDeniedError(
//                errorMessage = errorMessage,
//                onOpenSettingsClicked = { onIntent(QiblaContract.Intent.AccessAppSettings) },
//                modifier = modifier
//            )
//        }
//
//        sensorUnavailable -> {
//            QiblaSensorUnavailableError(
//                errorMessage = errorMessage,
//                modifier = modifier
//            )
//        }
//
//        else -> {
//            CatchAllError(
//                errorMessage = errorMessage,
//                onRetryClicked = { onIntent(QiblaContract.Intent.RetryClicked) },
//                modifier = modifier
//            )
//        }
//    }
//}