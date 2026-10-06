package com.mhq.salati.location.domain.service

import com.mhq.salati.connectivity.domain.repo.ConnectivityChecker
import com.mhq.salati.location.domain.model.SavedLocation
import com.mhq.salati.location.domain.usecases.GetLocalizedLocationUseCase
import com.mhq.salati.location.domain.usecases.GetSavedLocationUseCase
import com.mhq.salati.location.domain.usecases.SaveManualLocationUseCase
import com.mhq.salati.settings.domain.usecases.ObserveSettingsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

private val LOOKUP_TIMEOUT = 500L.milliseconds

/** Puts a place's city and country names into the app language,
 * without ever making the screen wait for it. */
class LocationLocalizer @Inject constructor(
    private val getLocalizedLocation: GetLocalizedLocationUseCase,
    private val getSavedLocation: GetSavedLocationUseCase,
    private val saveManualLocation: SaveManualLocationUseCase,
    private val observeSettings: ObserveSettingsUseCase,
    private val connectivityChecker: ConnectivityChecker
) {
    /** [location] with localized names, or unchanged when offline, the lookup is slow, or nothing is found. */
    suspend fun localize(location: SavedLocation): SavedLocation {
        if (!connectivityChecker.isConnected()) return location
        val languageCode = observeSettings().first().language.code
        return withTimeoutOrNull(LOOKUP_TIMEOUT) {
            getLocalizedLocation(location, languageCode)
        } ?: location
    }

    /** Same as [localize], and stores the result when the names changed. */
    suspend fun localizeAndSave(location: SavedLocation): SavedLocation {
        val localized = localize(location)
        if (localized != location) saveManualLocation(localized)
        return localized
    }

    /** The saved location, re-localized, each time the app language changes (the initial value is skipped). */
    fun observeLanguageChanges(): Flow<SavedLocation> =
        observeSettings()
            .map { it.language.code }
            .distinctUntilChanged()
            .drop(1)
            .mapNotNull { getSavedLocation().first()?.let { saved -> localize(saved) } }
}