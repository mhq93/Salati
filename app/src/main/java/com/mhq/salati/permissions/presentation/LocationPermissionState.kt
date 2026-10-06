package com.mhq.salati.permissions.presentation

data class LocationPermissionState(
    val granted: Boolean = false,
    val required: Boolean = false,
    val permanentlyDenied: Boolean = false,
    val servicesDisabled: Boolean = false
)