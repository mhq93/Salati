package com.mhq.salati.presentation.common.location

sealed interface LocationPermissionEffect {
    data object NavigateToAppSettings : LocationPermissionEffect
    data object NavigateToLocationSettings : LocationPermissionEffect
}