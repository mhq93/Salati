package com.mhq.salati.qibla.presentation.handlers

import com.mhq.salati.location.domain.service.LocationLocalizer
import com.mhq.salati.location.domain.model.CoordinatesLookup
import com.mhq.salati.location.domain.usecases.GetCurrentCoordinatesUseCase
import com.mhq.salati.location.domain.usecases.GetNamedLocationUseCase
import com.mhq.salati.location.domain.usecases.GetSavedLocationUseCase
import com.mhq.salati.permissions.presentation.LocationPermissionDelegate
import com.mhq.salati.permissions.presentation.LocationPermissionEffect
import com.mhq.salati.permissions.presentation.LocationPermissionState
import com.mhq.salati.shared.domain.Coordinates
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Everything about "where is the user" for the Qibla screen. A GPS fix is enough to calculate a
 * bearing, so unlike Home this never needs the internet: names are looked up only when possible.
 * It only reports what happened; the ViewModel decides what the screen shows.
 */
class QiblaLocationHandler @Inject constructor(
    private val permissionDelegate: LocationPermissionDelegate,
    private val getSavedLocationUseCase: GetSavedLocationUseCase,
    private val getCurrentCoordinatesUseCase: GetCurrentCoordinatesUseCase,
    private val getNamedLocationUseCase: GetNamedLocationUseCase,
    private val locationLocalizer: LocationLocalizer
) {
    val permissionState: StateFlow<LocationPermissionState> = permissionDelegate.state
    val permissionEffects: SharedFlow<LocationPermissionEffect> = permissionDelegate.effect

    /** The place's name in the new language, each time the app language changes. */
    val localizedNameChanges: Flow<String?> =
        locationLocalizer.observeLanguageChanges().map { it.displayName }

    /** The saved place if there is one, otherwise a fresh GPS lookup (which may ask for permission). */
    fun load(): Flow<QiblaLocationLoad> = flow {
        val saved = getSavedLocationUseCase().first()
        if (saved != null) {
            emit(QiblaLocationLoad.Saved(saved))
            return@flow
        }

        getCurrentCoordinatesUseCase().collect { lookup ->
            when (lookup) {
                is CoordinatesLookup.ServicesDisabled -> {
                    permissionDelegate.markServicesDisabled()
                    emit(QiblaLocationLoad.ServicesDisabled)
                }
                is CoordinatesLookup.PermissionDenied -> emit(permissionOutcome())
                is CoordinatesLookup.Locating -> emit(QiblaLocationLoad.LookingUpCurrentLocation)
                is CoordinatesLookup.TimedOut -> emit(QiblaLocationLoad.TimedOut)
                is CoordinatesLookup.Failed -> emit(QiblaLocationLoad.Failed(lookup.message))
                is CoordinatesLookup.Found -> emit(namedLocation(lookup.coordinates))
            }
        }
    }

    // A permission that was denied for good isn't asked for again; otherwise ask once, then just report it.
    private suspend fun permissionOutcome(): QiblaLocationLoad = when {
        permissionDelegate.state.value.permanentlyDenied -> QiblaLocationLoad.PermissionPermanentlyDenied
        permissionDelegate.requirePermissionOnce() -> QiblaLocationLoad.PermissionPrompted
        else -> QiblaLocationLoad.PermissionRequired
    }

    private suspend fun namedLocation(coordinates: Coordinates): QiblaLocationLoad = try {
        QiblaLocationLoad.Found(getNamedLocationUseCase(coordinates))
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        QiblaLocationLoad.Failed(e.message)
    }

    /** The place's name in the app language. A freshly found place also has its localized names stored. */
    suspend fun localizedName(located: QiblaLocationLoad.Located): String? {
        val localized = when (located) {
            is QiblaLocationLoad.Saved -> locationLocalizer.localize(located.location)
            is QiblaLocationLoad.Found -> locationLocalizer.localizeAndSave(located.location)
        }
        return localized.displayName
    }

    /** After a retry the permission prompt may be shown once more. */
    fun allowPermissionPromptAgain() = permissionDelegate.allowPromptAgain()

    fun recheckPermissions() = permissionDelegate.reset()

    suspend fun onPermissionGranted() = permissionDelegate.onPermissionGranted()

    suspend fun onPermissionDenied(permanentlyDenied: Boolean) =
        permissionDelegate.onPermissionDenied(permanentlyDenied)

    suspend fun openAppSettings() = permissionDelegate.requestAppSettings()

    suspend fun openLocationSettings() = permissionDelegate.requestLocationSettings()
}