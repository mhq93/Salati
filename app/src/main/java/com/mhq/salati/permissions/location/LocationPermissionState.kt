package com.mhq.salati.permissions.location

data class LocationPermissionState(
    val required: Boolean = false,
    val permanentlyDenied: Boolean = false,
    val servicesDisabled: Boolean = false
)