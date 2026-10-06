package com.mhq.salati.home.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mhq.salati.home.presentation.contract.HomeContract
import com.mhq.salati.shared.ui.errors.CatchAllError
import com.mhq.salati.shared.ui.errors.LocationPermissionPermanentlyDeniedError
import com.mhq.salati.shared.ui.errors.LocationPermissionRequiredError
import com.mhq.salati.shared.ui.errors.LocationServicesDisabledError
import com.mhq.salati.shared.ui.theme.SalatiTheme

@Composable
fun HomeContentError(
    errorMessage: String,
    isLocationPermissionPermanentlyDenied: Boolean,
    areLocationServicesDisabled: Boolean,
    isLocationPermissionRequired: Boolean,
    onIntent: (HomeContract.Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    when {

        areLocationServicesDisabled -> {
            LocationServicesDisabledError(
                errorMessage = errorMessage,
                onEnableLocationClicked = { onIntent(HomeContract.Intent.AccessDeviceLocationSettings) },
                modifier = modifier
            )
        }

        isLocationPermissionRequired -> {
            LocationPermissionRequiredError(
                errorMessage = errorMessage,
                onRetryClicked = { onIntent(HomeContract.Intent.RetryClicked) },
                modifier = modifier
            )
        }

        isLocationPermissionPermanentlyDenied -> {
            LocationPermissionPermanentlyDeniedError(
                errorMessage = errorMessage,
                onOpenSettingsClicked = { onIntent(HomeContract.Intent.AccessAppSettings) },
                modifier = modifier
            )
        }

        else -> {
            CatchAllError(
                errorMessage = errorMessage,
                onRetryClicked = { onIntent(HomeContract.Intent.RetryClicked) },
                modifier = modifier
            )
        }
    }
}

@Preview
@Composable
private fun HomeContentErrorPreview() {
    SalatiTheme()  {
        HomeContentError(
            errorMessage = "Some message",
            isLocationPermissionPermanentlyDenied = true,
            areLocationServicesDisabled = true,
            isLocationPermissionRequired = true,
            onIntent = {}
        )
    }
}