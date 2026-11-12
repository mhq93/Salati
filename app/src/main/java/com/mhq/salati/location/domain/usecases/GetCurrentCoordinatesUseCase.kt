package com.mhq.salati.location.domain.usecases

import com.mhq.salati.location.domain.model.CoordinatesLookup
import com.mhq.salati.location.domain.repo.LocationProvider
import com.mhq.salati.permissions.domain.repo.PermissionChecker
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withTimeout
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

/** Hard cap on waiting for the GPS fix. */
private val GPS_TIMEOUT = 5_000L.milliseconds

/**
 * The device's position as a short one-shot flow of [CoordinatesLookup] states.
 *
 * Needs location services and permission, never the internet.
 * Ends with the GPS fix or the reason there isn't one.
 * Emits a blocker immediately when a check fails,
 * otherwise [CoordinatesLookup.Locating] and then exactly one outcome within [GPS_TIMEOUT].
 */
class GetCurrentCoordinatesUseCase @Inject constructor(
    private val locationProvider: LocationProvider,
    private val permissionChecker: PermissionChecker
) {
    /** @return a flow ending in [CoordinatesLookup.Found] or one of the failure states */
    operator fun invoke(): Flow<CoordinatesLookup> = flow {
        if (!locationProvider.isLocationEnabled()) {
            emit(CoordinatesLookup.ServicesDisabled)
            return@flow
        }
        if (!permissionChecker.hasLocationPermission()) {
            emit(CoordinatesLookup.PermissionDenied)
            return@flow
        }

        emit(CoordinatesLookup.Locating)
        emit(currentLocation())
    }

    /** Maps a failed fix to its lookup state; cancellation always propagates. */
    private suspend fun currentLocation(): CoordinatesLookup = try {
        CoordinatesLookup.Found(
            withTimeout(GPS_TIMEOUT) { locationProvider.getCurrentLocation() }
        )
    } catch (e: TimeoutCancellationException) {
        CoordinatesLookup.TimedOut
    } catch (e: SecurityException) {
        CoordinatesLookup.PermissionDenied
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        CoordinatesLookup.Failed(e.message)
    }
}