package com.mhq.salati.permissions.location

sealed class LocationPermissionEffect {
    object RequestPermission : LocationPermissionEffect()
    object PermissionResolved : LocationPermissionEffect()
    object NavigateToAppSettings : LocationPermissionEffect()
    object NavigateToLocationSettings : LocationPermissionEffect()
}