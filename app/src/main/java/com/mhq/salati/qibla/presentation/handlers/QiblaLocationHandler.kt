package com.mhq.salati.qibla.presentation.handlers

import com.mhq.salati.location.domain.model.CoordinatesLookup
import com.mhq.salati.location.domain.model.LocationPrerequisites
import com.mhq.salati.location.domain.service.LocationLocalizer
import com.mhq.salati.location.domain.usecases.CheckLocationPrerequisitesUseCase
import com.mhq.salati.location.domain.usecases.GetCurrentCoordinatesUseCase
import com.mhq.salati.location.domain.usecases.GetNamedLocationUseCase
import com.mhq.salati.location.domain.usecases.GetSavedLocationUseCase
import com.mhq.salati.permissions.presentation.LocationPermissionDelegate
import com.mhq.salati.permissions.presentation.LocationPermissionEffect
import com.mhq.salati.permissions.presentation.LocationPermissionState
import com.mhq.salati.shared.domain.Coordinates
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Everything about "where is the user" for the Qibla screen. It never asks for the permission by
 * itself: that happens only when the user taps Allow on the blocked screen.
 */
class QiblaLocationHandler @Inject constructor(
    private val locationPermissionDelegate: LocationPermissionDelegate,
    private val checkLocationPrerequisitesUseCase: CheckLocationPrerequisitesUseCase,
    private val getCurrentCoordinatesUseCase: GetCurrentCoordinatesUseCase,
    private val getSavedLocationUseCase: GetSavedLocationUseCase,
    private val getNamedLocationUseCase: GetNamedLocationUseCase,
    private val locationLocalizer: LocationLocalizer
) {
    val permissionState: StateFlow<LocationPermissionState> = locationPermissionDelegate.state
    val permissionEffects: Flow<LocationPermissionEffect> = locationPermissionDelegate.effect

    /** The place's name in the new language, each time the app language changes. */
    val localizedNameChanges: Flow<String?> =
        locationLocalizer.observeLanguageChanges().map { it.displayName }

    /** The saved place if there is one, otherwise a fresh GPS lookup. */
    fun load(): Flow<QiblaLocationLoad> = flow {
        val saved = getSavedLocationUseCase().first()
        if (saved != null) {
            emit(QiblaLocationLoad.Saved(saved))
            return@flow
        }

        val prerequisites = checkLocationPrerequisitesUseCase()
        if (!prerequisites.areMet) {
            emit(QiblaLocationLoad.Blocked(prerequisites))
            return@flow
        }

        getCurrentCoordinatesUseCase().collect { lookup ->
            when (lookup) {
                // Changed between the check and the lookup.
                is CoordinatesLookup.ServicesDisabled ->
                    emit(QiblaLocationLoad.Blocked(prerequisites.copy(servicesEnabled = false)))
                is CoordinatesLookup.PermissionDenied ->
                    emit(QiblaLocationLoad.Blocked(prerequisites.copy(permissionGranted = false)))
                is CoordinatesLookup.Locating -> emit(QiblaLocationLoad.LookingUpCurrentLocation)
                is CoordinatesLookup.TimedOut -> emit(QiblaLocationLoad.TimedOut)
                is CoordinatesLookup.Failed -> emit(QiblaLocationLoad.Failed(lookup.message))
                is CoordinatesLookup.Found -> emit(namedLocation(lookup.coordinates))
            }
        }
    }

    suspend fun checkPrerequisites(): LocationPrerequisites =
        checkLocationPrerequisitesUseCase()

    private suspend fun namedLocation(coordinates: Coordinates): QiblaLocationLoad = try {
        QiblaLocationLoad.Found(getNamedLocationUseCase(coordinates))
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        QiblaLocationLoad.Failed(e.message)
    }

    /** The place's name in the app language, stored when it changed. */
    suspend fun localizedName(located: QiblaLocationLoad.Located): String? =
        locationLocalizer.localizeAndSave(located.location).displayName

    fun recheckPermissions() = locationPermissionDelegate.reset()

    fun requirePermission() = locationPermissionDelegate.requirePermission()

    fun onPermissionGranted() = locationPermissionDelegate.onPermissionGranted()

    fun onPermissionDenied(permanentlyDenied: Boolean) =
        locationPermissionDelegate.onPermissionDenied(permanentlyDenied)

    fun openAppSettings() = locationPermissionDelegate.requestAppSettings()

    fun openLocationSettings() = locationPermissionDelegate.requestLocationSettings()
}