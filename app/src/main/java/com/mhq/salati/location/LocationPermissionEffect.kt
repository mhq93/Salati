package com.mhq.salati.location

sealed class LocationPermissionEffect {
    object RequestPermission : LocationPermissionEffect()
    object PermissionResolved : LocationPermissionEffect()
    object NavigateToAppSettings : LocationPermissionEffect()
    object NavigateToLocationSettings : LocationPermissionEffect()
}