package com.mhq.salati.qibla.presentation.handlers

import com.mhq.salati.location.domain.model.LocationPrerequisites
import com.mhq.salati.shared.domain.Coordinates

/** The steps of working out where the user is for the Qibla screen, in the order they happen. */
sealed interface QiblaLocationLoad {

    /** A place the Qibla bearing can be calculated for (the saved place or a fresh GPS fix). */
    data class Located(val coordinates: Coordinates) : QiblaLocationLoad

    /** The checks passed and the GPS fix is being awaited. */
    data object LookingUpCurrentLocation : QiblaLocationLoad

    /** There is no saved place and something needed to find one is missing. */
    data class Blocked(val prerequisites: LocationPrerequisites) : QiblaLocationLoad

    data object TimedOut : QiblaLocationLoad

    data object Failed : QiblaLocationLoad
}