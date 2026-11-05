package com.mhq.salati.location.domain.model

/** What happened when the app tried to find where the user is. */
sealed interface LocationResolution {
    data class Resolved(val location: SavedLocation) : LocationResolution

    /** Something the lookup needs is missing (internet, location services or the permission). */
    data class Blocked(val prerequisites: LocationPrerequisites) : LocationResolution

    data object GeocodingFailed : LocationResolution
    data object LocationUnavailable : LocationResolution
}