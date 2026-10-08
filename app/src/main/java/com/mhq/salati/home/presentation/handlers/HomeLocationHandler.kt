package com.mhq.salati.home.presentation.handlers

import com.mhq.salati.home.domain.model.LocationResolution
import com.mhq.salati.home.domain.usecases.ResolveLocationUseCase
import com.mhq.salati.location.domain.service.LocationLocalizer
import com.mhq.salati.location.domain.usecases.GetSavedLocationUseCase
import com.mhq.salati.permissions.presentation.LocationPermissionDelegate
import com.mhq.salati.permissions.presentation.LocationPermissionEffect
import com.mhq.salati.permissions.presentation.LocationPermissionState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Everything about "where is the user" for the Home screen: the saved place, the GPS lookup, the
 * location permission and the localized place name. It only reports what happened; the
 * ViewModel decides what the screen shows.
 */

class HomeLocationHandler @Inject constructor(
    private val permissionDelegate: LocationPermissionDelegate,
    private val getSavedLocationUseCase: GetSavedLocationUseCase,
    private val resolveLocationUseCase: ResolveLocationUseCase,
    private val locationLocalizer: LocationLocalizer
) {
    private var permissionPromptShown = false

    val permissionState: StateFlow<LocationPermissionState> = permissionDelegate.state
    val permissionEffects: Flow<LocationPermissionEffect> = permissionDelegate.effect

    /** The place's name in the new language, each time the app language changes. */
    val localizedNameChanges: Flow<String?> =
        locationLocalizer.observeLanguageChanges().map { it.displayName }

    /** The saved place if there is one, otherwise a fresh GPS lookup (which may ask for permission). */
    fun load(): Flow<HomeLocationLoadingHandler> = flow {
        val saved = getSavedLocationUseCase().first()
        if (saved != null) {
            emit(HomeLocationLoadingHandler.Saved(saved))
            return@flow
        }

        if (permissionDelegate.state.value.permanentlyDenied) {
            emit(HomeLocationLoadingHandler.PermissionPermanentlyDenied)
            return@flow
        }

        permissionDelegate.reset()
        emit(HomeLocationLoadingHandler.LookingUpCurrentLocation)

        when (val resolution = resolveLocationUseCase()) {
            is LocationResolution.Resolved -> {
                permissionPromptShown = false
                emit(HomeLocationLoadingHandler.Found(resolution.location))
            }
            is LocationResolution.PermissionDenied -> {
                if (!permissionPromptShown) {
                    permissionPromptShown = true
                    permissionDelegate.requirePermission()
                    emit(HomeLocationLoadingHandler.PermissionPrompted)
                } else {
                    emit(HomeLocationLoadingHandler.PermissionRequired)
                }
            }
            is LocationResolution.ServicesDisabled -> {
                permissionDelegate.markServicesDisabled()
                emit(HomeLocationLoadingHandler.ServicesDisabled)
            }
            is LocationResolution.NoInternet -> emit(HomeLocationLoadingHandler.NoInternet)
            is LocationResolution.GeocodingFailed -> emit(HomeLocationLoadingHandler.GeocodingFailed)
            is LocationResolution.LocationUnavailable -> emit(HomeLocationLoadingHandler.Unavailable)
        }
    }

    /** The place's name in the app language. A freshly found place also has its localized names stored. */
    suspend fun localizedName(located: HomeLocationLoadingHandler.Located): String? {
        val localized = when (located) {
            is HomeLocationLoadingHandler.Saved -> locationLocalizer.localize(located.location)
            is HomeLocationLoadingHandler.Found -> locationLocalizer.localizeAndSave(located.location)
        }
        return localized.displayName
    }

    /** After a retry the permission prompt may be shown once more. */
    fun allowPermissionPromptAgain() {
        permissionPromptShown = false
    }

    fun recheckPermissions() =
        permissionDelegate.reset()

    suspend fun requirePermission() =
        permissionDelegate.requirePermission()

    suspend fun onPermissionGranted() =
        permissionDelegate.onPermissionGranted()

    suspend fun onPermissionDenied(permanentlyDenied: Boolean) =
        permissionDelegate.onPermissionDenied(permanentlyDenied)

    suspend fun openAppSettings() =
        permissionDelegate.requestAppSettings()

    suspend fun openLocationSettings() =
        permissionDelegate.requestLocationSettings()
}