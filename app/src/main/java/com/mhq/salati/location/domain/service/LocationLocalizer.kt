package com.mhq.salati.location.domain.service

import com.mhq.salati.connectivity.domain.repo.ConnectivityChecker
import com.mhq.salati.location.domain.model.SavedLocation
import com.mhq.salati.location.domain.usecases.GetLocalizedLocationUseCase
import com.mhq.salati.location.domain.usecases.GetSavedLocationUseCase
import com.mhq.salati.location.domain.usecases.SaveManualLocationUseCase
import com.mhq.salati.settings.domain.usecases.ObserveSettingsUseCase
import com.mhq.salati.shared.domain.Coordinates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.milliseconds

private val LOOKUP_TIMEOUT = 5_000L.milliseconds

/** Puts a place's city and country names into the app language and stores them.
 * One lookup at a time for the whole app, and none repeated for a place already done in this language. */
@Singleton
class LocationLocalizer @Inject constructor(
    private val getLocalizedLocation: GetLocalizedLocationUseCase,
    private val getSavedLocation: GetSavedLocationUseCase,
    private val saveManualLocation: SaveManualLocationUseCase,
    private val observeSettings: ObserveSettingsUseCase,
    private val connectivityChecker: ConnectivityChecker
) {
    private val lookupLock = Mutex()
    private var lastLookup: Lookup? = null

    private data class Lookup(
        val coordinates: Coordinates,
        val languageCode: String,
        val result: SavedLocation
    )

    /** [location] with localized names (stored when they changed), or unchanged when offline,
     * the lookup is slow, or nothing is found. */
    suspend fun localizeAndSave(location: SavedLocation): SavedLocation = lookupLock.withLock {
        val languageCode = observeSettings().first().language.code

        lastLookup
            ?.takeIf { it.coordinates == location.coordinates && it.languageCode == languageCode }
            ?.let { return@withLock it.result }

        if (!connectivityChecker.isConnected()) return@withLock location

        val localized = withTimeoutOrNull(LOOKUP_TIMEOUT) {
            getLocalizedLocation(location, languageCode)
        } ?: return@withLock location

        lastLookup = Lookup(location.coordinates, languageCode, localized)
        if (localized != location) saveManualLocation(localized)
        localized
    }

    /** The saved location, re-localized, each time the app language changes (the initial value is skipped). */
    fun observeLanguageChanges(): Flow<SavedLocation> =
        observeSettings()
            .map { it.language.code }
            .distinctUntilChanged()
            .drop(1)
            .mapNotNull { getSavedLocation().first()?.let { saved -> localizeAndSave(saved) } }
}