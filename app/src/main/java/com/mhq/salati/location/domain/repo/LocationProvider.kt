package com.mhq.salati.location.domain.repo

import com.mhq.salati.shared.domain.Coordinates

/**
 * The device's own positioning hardware, implemented in Data over the platform location APIs.
 *
 * Never touches the internet; network-side needs are checked separately by the connectivity layer.
 */
interface LocationProvider {
    /** Whether location services (GPS) are currently on. */
    fun isLocationEnabled(): Boolean

    /**
     * Waits for a GPS fix.
     *
     * @return the device's current position
     * @throws SecurityException if the location permission is not granted
     * @throws kotlinx.coroutines.TimeoutCancellationException if the caller applies a timeout
     */
    suspend fun getCurrentLocation(): Coordinates
}