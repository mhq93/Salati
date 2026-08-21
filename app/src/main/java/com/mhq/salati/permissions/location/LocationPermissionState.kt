package com.mhq.salati.permissions.location

data class LocationPermissionState(
    val granted: Boolean = false,
    val required: Boolean = false,
    val permanentlyDenied: Boolean = false,
    val servicesDisabled: Boolean = false
)