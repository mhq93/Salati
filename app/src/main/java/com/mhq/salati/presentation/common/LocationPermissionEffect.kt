package com.mhq.salati.presentation.common

sealed interface LocationPermissionEffect {
    data object NavigateToAppSettings : LocationPermissionEffect
    data object NavigateToLocationSettings : LocationPermissionEffect
}