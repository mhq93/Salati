package com.mhq.salati.location.domain.model

/** What finding the user's place needs, each checked on its own. */
data class LocationPrerequisites(
    val hasInternet: Boolean,
    val servicesEnabled: Boolean,
    val permissionGranted: Boolean
) {
    val areMet: Boolean
        get() = hasInternet && servicesEnabled && permissionGranted
}