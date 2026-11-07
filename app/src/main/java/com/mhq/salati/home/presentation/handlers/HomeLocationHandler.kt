package com.mhq.salati.home.presentation.handlers

import com.mhq.salati.location.domain.model.LocationResolution
import com.mhq.salati.location.domain.service.LocationLocalizer
import com.mhq.salati.location.domain.usecases.GetSavedLocationUseCase
import com.mhq.salati.location.domain.usecases.ResolveLocationUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * "Where is the user" for the Home screen: the saved place, the GPS lookup and the localized
 * place name. It only reports what happened; the ViewModel decides what the screen shows.
 */
class HomeLocationHandler @Inject constructor(
    private val getSavedLocationUseCase: GetSavedLocationUseCase,
    private val resolveLocationUseCase: ResolveLocationUseCase,
    private val locationLocalizer: LocationLocalizer
) {
    /** The place's name in the new language, each time the app language changes. */
    val localizedNameChanges: Flow<String?> =
        locationLocalizer.observeLanguageChanges().map { it.displayName }

    /** The saved place if there is one, otherwise a fresh GPS lookup. */
    fun load(): Flow<HomeLocationLoad> = flow {
        val saved = getSavedLocationUseCase().first()
        if (saved != null) {
            emit(HomeLocationLoad.Saved(saved))
            return@flow
        }

        emit(HomeLocationLoad.LookingUpCurrentLocation)

        when (val resolution = resolveLocationUseCase()) {
            is LocationResolution.Resolved -> emit(HomeLocationLoad.Found(resolution.location))
            is LocationResolution.Blocked -> emit(HomeLocationLoad.Blocked(resolution.prerequisites))
            is LocationResolution.GeocodingFailed -> emit(HomeLocationLoad.GeocodingFailed)
            is LocationResolution.LocationUnavailable -> emit(HomeLocationLoad.Unavailable)
        }
    }

    /** The place's name in the app language, stored when it changed. */
    suspend fun localizedName(located: HomeLocationLoad.Located): String? =
        locationLocalizer.localizeAndSave(located.location).displayName
}