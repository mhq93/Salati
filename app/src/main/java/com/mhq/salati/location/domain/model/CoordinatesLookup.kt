package com.mhq.salati.location.domain.model

import com.mhq.salati.shared.domain.Coordinates

/**
 * The steps of getting the device's position through GPS, in the order they happen.
 *
 * A lookup emits at most two states: one of the two blockers if a check fails up front,
 * otherwise [Locating] followed by exactly one outcome.
 */
sealed interface CoordinatesLookup {
    /** Location services are off; the user must enable them in system settings. */
    data object ServicesDisabled : CoordinatesLookup

    /** The location permission is not granted. */
    data object PermissionDenied : CoordinatesLookup

    /** The checks passed and the GPS fix is now being awaited. */
    data object Locating : CoordinatesLookup

    /** The GPS fix arrived. */
    data class Found(val coordinates: Coordinates) : CoordinatesLookup

    /** The lookup failed for a reason other than timeout or permission. */
    data class Failed(val message: String?) : CoordinatesLookup

    /** The GPS fix did not arrive within the timeout. */
    data object TimedOut : CoordinatesLookup
}