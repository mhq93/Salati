package com.mhq.salati.location.domain.model

/**
 * What finding the user's place needs, each checked on its own.
 *
 * All three are evaluated independently
 * so the UI can show every missing one at once, instead of one at a time.
 *
 * @property hasInternet whether the device has a usable network connection
 * @property servicesEnabled whether location services (GPS) are on
 * @property permissionGranted whether the location permission is granted
 */
data class LocationPrerequisites(
    val hasInternet: Boolean,
    val servicesEnabled: Boolean,
    val permissionGranted: Boolean
) {
    /** True only when nothing is missing. */
    val areMet: Boolean
        get() = hasInternet && servicesEnabled && permissionGranted
}