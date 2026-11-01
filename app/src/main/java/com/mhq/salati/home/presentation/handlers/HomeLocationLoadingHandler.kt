package com.mhq.salati.home.presentation.handlers

import com.mhq.salati.location.domain.model.SavedLocation


/** The steps of working out where the user is, in the order they happen. */
sealed interface HomeLocationLoadingHandler {

    /** [Located] => A place the prayer times can be loaded for. */
    sealed interface Located : HomeLocationLoadingHandler {
        val location: SavedLocation
    }

    /** [Saved] => The place stored from an earlier run. */
    /** [Found] => The place just found through GPS. */
    /** [LookingUpCurrentLocation] => There is no saved place, so a GPS lookup has started. */
    /** [PermissionPermanentlyDenied] => Permission denied for good, asking again is pointless. */
    /** [PermissionPrompted] => The system permission prompt was just requested; nothing to show yet. */
    /** [PermissionRequired] => The prompt was already shown once and the permission is still missing. */
    data class Saved(override val location: SavedLocation) : Located
    data class Found(override val location: SavedLocation) : Located
    data object LookingUpCurrentLocation : HomeLocationLoadingHandler
    data object PermissionPermanentlyDenied : HomeLocationLoadingHandler
    data object PermissionPrompted : HomeLocationLoadingHandler
    data object PermissionRequired : HomeLocationLoadingHandler
    data object ServicesDisabled : HomeLocationLoadingHandler
    data object NoInternet : HomeLocationLoadingHandler
    data object GeocodingFailed : HomeLocationLoadingHandler
    data object Unavailable : HomeLocationLoadingHandler
}