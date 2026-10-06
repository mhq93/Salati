package com.mhq.salati.home.presentation.handlers

import com.mhq.salati.location.domain.model.SavedLocation

/** The steps of working out where the user is, in the order they happen. */

sealed interface HomeLocationLoadingHandler {

    /** A place the prayer times can be loaded for. */
    sealed interface Located : HomeLocationLoadingHandler {
        val location: SavedLocation
    }

    /** The place stored from an earlier run. */
    data class Saved(override val location: SavedLocation) : Located

    /** The place just found through GPS. */
    data class Found(override val location: SavedLocation) : Located

    /** There is no saved place, so a GPS lookup has started. */
    data object LookingUpCurrentLocation : HomeLocationLoadingHandler

    /** The permission was denied for good, so asking again is pointless. */
    data object PermissionPermanentlyDenied : HomeLocationLoadingHandler

    /** The system permission prompt was just requested; nothing to show yet. */
    data object PermissionPrompted : HomeLocationLoadingHandler

    /** The prompt was already shown once and the permission is still missing. */
    data object PermissionRequired : HomeLocationLoadingHandler

    data object ServicesDisabled : HomeLocationLoadingHandler
    data object NoInternet : HomeLocationLoadingHandler
    data object GeocodingFailed : HomeLocationLoadingHandler
    data object Unavailable : HomeLocationLoadingHandler
}