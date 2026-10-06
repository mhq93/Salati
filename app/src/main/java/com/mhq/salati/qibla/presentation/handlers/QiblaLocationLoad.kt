package com.mhq.salati.qibla.presentation.handlers

import com.mhq.salati.location.domain.model.SavedLocation

/** The steps of working out where the user is for the Qibla screen, in the order they happen. */

sealed interface QiblaLocationLoad {

    /** A place the Qibla bearing can be calculated for. */
    sealed interface Located : QiblaLocationLoad {
        val location: SavedLocation
    }

    /** The place stored from an earlier run or picked by hand. */
    data class Saved(override val location: SavedLocation) : Located

    /** The place just found through GPS, with its names when they could be looked up. */
    data class Found(override val location: SavedLocation) : Located

    /** The checks passed and the GPS fix is being awaited. */
    data object LookingUpCurrentLocation : QiblaLocationLoad

    data object ServicesDisabled : QiblaLocationLoad

    /** The permission was denied for good, so asking again is pointless. */
    data object PermissionPermanentlyDenied : QiblaLocationLoad

    /** The system permission prompt was just requested; nothing to show yet. */
    data object PermissionPrompted : QiblaLocationLoad

    /** The prompt was already shown once and the permission is still missing. */
    data object PermissionRequired : QiblaLocationLoad

    data object TimedOut : QiblaLocationLoad
    data class Failed(val message: String?) : QiblaLocationLoad
}