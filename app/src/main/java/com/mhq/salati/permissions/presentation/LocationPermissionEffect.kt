package com.mhq.salati.permissions.presentation

sealed class LocationPermissionEffect {
    data object RequestPermission : LocationPermissionEffect()
    data object NavigateToAppSettings : LocationPermissionEffect()
    data object NavigateToLocationSettings : LocationPermissionEffect()
}