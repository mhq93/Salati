package com.mhq.salati.qibla.presentation.handlers

import com.mhq.salati.location.domain.model.LocationPrerequisites
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

    /** There is no saved place and something needed to find one is missing. */
    data class Blocked(val prerequisites: LocationPrerequisites) : QiblaLocationLoad

    data object TimedOut : QiblaLocationLoad
    data class Failed(val message: String?) : QiblaLocationLoad
}