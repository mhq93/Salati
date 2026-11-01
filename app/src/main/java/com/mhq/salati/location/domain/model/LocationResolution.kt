package com.mhq.salati.location.domain.model

/** What happened when the app tried to find where the user is. */
sealed interface LocationResolution {
    data class Resolved(val location: SavedLocation) : LocationResolution
    data object NoInternet : LocationResolution
    data object ServicesDisabled : LocationResolution
    data object PermissionDenied : LocationResolution
    data object GeocodingFailed : LocationResolution
    data object LocationUnavailable : LocationResolution
}