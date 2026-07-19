package com.mhq.salati.presentation.common

data class LocationPermissionState(
    val required: Boolean = false,
    val permanentlyDenied: Boolean = false,
    val servicesDisabled: Boolean = false
)