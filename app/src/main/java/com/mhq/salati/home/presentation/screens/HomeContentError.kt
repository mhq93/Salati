package com.mhq.salati.home.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mhq.salati.R
import com.mhq.salati.home.presentation.contract.HomeContract
import com.mhq.salati.permissions.location.LocationPermissionState
import com.mhq.salati.shared.presentation.errors.LocationPermissionPermanentlyDeniedError
import com.mhq.salati.shared.presentation.errors.LocationPermissionRequiredError
import com.mhq.salati.shared.presentation.errors.LocationServicesDisabledError
import com.mhq.salati.shared.presentation.theme.SalatiTheme

@Composable
fun HomeContentError(
    errorMessage: String,
    locationPermissionState: LocationPermissionState,
    onIntent: (HomeContract.Intent) -> Unit,
    modifier: Modifier = Modifier
) {
    when {
        locationPermissionState.permanentlyDenied -> {
            LocationPermissionPermanentlyDeniedError(
                errorMessage = errorMessage,
                onOpenSettingsClicked = { onIntent(HomeContract.Intent.AccessAppSettings) }
            )
        }

        locationPermissionState.servicesDisabled -> {
            LocationServicesDisabledError(
                errorMessage = errorMessage,
                onEnableLocationClicked = { onIntent(HomeContract.Intent.AccessDeviceLocationSettings) }
            )
        }

        else -> {
            LocationPermissionRequiredError(
                errorMessage = errorMessage,
                onRetryClicked = { onIntent(HomeContract.Intent.RetryClicked) }
            )
        }
    }
}

@Preview
@Composable
private fun HomeContentErrorPreview() {
    SalatiTheme() {
        HomeContentError(
            errorMessage = stringResource(R.string.error),
            locationPermissionState = LocationPermissionState(
                required = true,
                permanentlyDenied = true,
                servicesDisabled = true
            ),
            onIntent = {}
        )
    }
}