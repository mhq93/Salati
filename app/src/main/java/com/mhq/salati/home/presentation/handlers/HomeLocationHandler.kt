package com.mhq.salati.home.presentation.handlers

import com.mhq.salati.location.domain.model.LocationPrerequisites
import com.mhq.salati.location.domain.model.LocationResolution
import com.mhq.salati.location.domain.service.LocationLocalizer
import com.mhq.salati.location.domain.usecases.CheckLocationPrerequisitesUseCase
import com.mhq.salati.location.domain.usecases.GetSavedLocationUseCase
import com.mhq.salati.location.domain.usecases.ResolveLocationUseCase
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
 * ViewModel decides what the screen shows. It never asks for the permission by itself: that
 * happens only when the user taps Allow on the blocked screen.
 */
class HomeLocationHandler @Inject constructor(
    private val permissionDelegate: LocationPermissionDelegate,
    private val checkLocationPrerequisitesUseCase: CheckLocationPrerequisitesUseCase,
    private val getSavedLocationUseCase: GetSavedLocationUseCase,
    private val resolveLocationUseCase: ResolveLocationUseCase,
    private val locationLocalizer: LocationLocalizer
) {
    val permissionState: StateFlow<LocationPermissionState> = permissionDelegate.state
    val permissionEffects: Flow<LocationPermissionEffect> = permissionDelegate.effect

    /** The place's name in the new language, each time the app language changes. */
    val localizedNameChanges: Flow<String?> =
        locationLocalizer.observeLanguageChanges().map { it.displayName }

    /** The saved place if there is one, otherwise a fresh GPS lookup. */
    fun load(): Flow<HomeLocationLoadingHandler> = flow {
        val saved = getSavedLocationUseCase().first()
        if (saved != null) {
            emit(HomeLocationLoadingHandler.Saved(saved))
            return@flow
        }

        permissionDelegate.reset()
        emit(HomeLocationLoadingHandler.LookingUpCurrentLocation)

        when (val resolution = resolveLocationUseCase()) {
            is LocationResolution.Resolved -> emit(HomeLocationLoadingHandler.Found(resolution.location))
            is LocationResolution.Blocked -> emit(HomeLocationLoadingHandler.Blocked(resolution.prerequisites))
            is LocationResolution.GeocodingFailed -> emit(HomeLocationLoadingHandler.GeocodingFailed)
            is LocationResolution.LocationUnavailable -> emit(HomeLocationLoadingHandler.Unavailable)
        }
    }

    suspend fun checkPrerequisites(): LocationPrerequisites =
        checkLocationPrerequisitesUseCase()

    /** The place's name in the app language, stored when it changed. */
    suspend fun localizedName(located: HomeLocationLoadingHandler.Located): String? =
        locationLocalizer.localizeAndSave(located.location).displayName

    fun recheckPermissions() = permissionDelegate.reset()

    fun requirePermission() = permissionDelegate.requirePermission()

    fun onPermissionGranted() = permissionDelegate.onPermissionGranted()

    fun onPermissionDenied(permanentlyDenied: Boolean) =
        permissionDelegate.onPermissionDenied(permanentlyDenied)

    fun openAppSettings() = permissionDelegate.requestAppSettings()

    fun openLocationSettings() = permissionDelegate.requestLocationSettings()
}