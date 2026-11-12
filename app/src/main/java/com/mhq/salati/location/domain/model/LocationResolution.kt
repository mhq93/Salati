package com.mhq.salati.location.domain.model

/**
 * What happened when the app tried to find where the user is.
 *
 * This is the one-shot result of the full resolve flow: prerequisites, GPS fix, geocoding and save.
 * A [Resolved] location has already been persisted.
 */
sealed interface LocationResolution {
    /** The place was found and saved. */
    data class Resolved(val location: SavedLocation) : LocationResolution

    /** Something the lookup needs is missing (internet, location services or the permission). */
    data class Blocked(val prerequisites: LocationPrerequisites) : LocationResolution

    /** Coordinates were found but naming them failed. */
    data object GeocodingFailed : LocationResolution

    /** The GPS fix never arrived or the lookup crashed unexpectedly. */
    data object LocationUnavailable : LocationResolution
}