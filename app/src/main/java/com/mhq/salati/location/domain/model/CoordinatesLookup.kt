package com.mhq.salati.location.domain.model

import com.mhq.salati.shared.domain.Coordinates

/** The steps of getting the device's position through GPS, in the order they happen. */

sealed interface CoordinatesLookup {
    data object ServicesDisabled : CoordinatesLookup
    data object PermissionDenied : CoordinatesLookup

    /** The checks passed and the GPS fix is now being awaited. */
    data object Locating : CoordinatesLookup

    data class Found(val coordinates: Coordinates) : CoordinatesLookup
    data class Failed(val message: String?) : CoordinatesLookup
    data object TimedOut : CoordinatesLookup
}