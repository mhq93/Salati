package com.mhq.salati.onboarding.domain.usecases

import com.mhq.salati.location.domain.model.LocationResolution
import com.mhq.salati.location.domain.usecases.GetSavedLocationUseCase
import com.mhq.salati.location.domain.usecases.ResolveLocationUseCase
import com.mhq.salati.prayertimes.domain.usecases.GetCachedPrayerTimesUseCase
import com.mhq.salati.prayertimes.domain.usecases.GetPrayerTimesUseCase
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.domain.toDateKey
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

/** Hard cap on the whole warm-up; past it Home simply does its own loading. */
private val PREPARE_TIMEOUT = 20_000L.milliseconds

/**
 * Runs once when onboarding finishes so Home opens with data instead of a spinner:
 * saves the user's place (the chosen city, or a fresh GPS lookup) and caches today's prayer times.
 *
 * Best effort: every failure and the timeout are swallowed, because Home has its own loading
 * and error handling. Cancellation always propagates.
 */
class PrepareFirstLaunchUseCase @Inject constructor(
    private val getSavedLocationUseCase: GetSavedLocationUseCase,
    private val resolveLocationUseCase: ResolveLocationUseCase,
    private val getCachedPrayerTimesUseCase: GetCachedPrayerTimesUseCase,
    private val getPrayerTimesUseCase: GetPrayerTimesUseCase,
    private val clock: Clock
) {
    suspend operator fun invoke() {
        withTimeoutOrNull(PREPARE_TIMEOUT) {
            try {
                val place = getSavedLocationUseCase().first()
                    ?: (resolveLocationUseCase() as? LocationResolution.Resolved)?.location
                    ?: return@withTimeoutOrNull
                val todayKey = clock.today().toDateKey()
                if (getCachedPrayerTimesUseCase(todayKey, place.coordinates) == null) {
                    getPrayerTimesUseCase(todayKey, place.coordinates)
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                // Best effort only.
            }
        }
    }
}