package com.mhq.salati.permissions.presentation

sealed class LocationPermissionEffect {
    object RequestPermission : LocationPermissionEffect()
    object PermissionResolved : LocationPermissionEffect()
    object NavigateToAppSettings : LocationPermissionEffect()
    object NavigateToLocationSettings : LocationPermissionEffect()
}