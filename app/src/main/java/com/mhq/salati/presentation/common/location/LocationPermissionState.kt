package com.mhq.salati.presentation.common.location

data class LocationPermissionState(
    val required: Boolean = false,
    val permanentlyDenied: Boolean = false,
    val servicesDisabled: Boolean = false
)