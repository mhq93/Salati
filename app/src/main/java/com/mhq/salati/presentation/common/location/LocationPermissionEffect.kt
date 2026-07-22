package com.mhq.salati.presentation.common.location

sealed class LocationPermissionEffect {
    object RequestPermission : LocationPermissionEffect()
    object PermissionResolved : LocationPermissionEffect()
    object NavigateToAppSettings : LocationPermissionEffect()
    object NavigateToLocationSettings : LocationPermissionEffect()
}