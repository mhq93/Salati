package com.mhq.salati.location.presentation.model

data class PrerequisitesUi(
    val hasInternet: Boolean,
    val servicesEnabled: Boolean,
    val permissionGranted: Boolean,
    /** Asking again is pointless; only app settings can fix it. */
    val permissionPermanentlyDenied: Boolean
)