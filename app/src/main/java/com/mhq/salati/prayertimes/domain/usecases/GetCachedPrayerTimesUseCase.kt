package com.mhq.salati.prayertimes.domain.usecases

import com.mhq.salati.prayertimes.domain.model.applyHijriAdjustment
import com.mhq.salati.prayertimes.domain.model.PrayerTimesResult
import com.mhq.salati.prayertimes.domain.repo.PrayerTimesRepository
import com.mhq.salati.settings.domain.repo.SettingsRepository
import com.mhq.salati.shared.domain.Coordinates
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Reads a day's timings from cache, configured with the user's current settings.
 * Fills in the calculation method and madhab from [SettingsRepository] and
 * applies the stored Hijri date offset to the result, so cached timings come
 * out exactly as the user should see them. Cache only; never goes online.
 */
class GetCachedPrayerTimesUseCase @Inject constructor(
    private val prayerTimesRepository: PrayerTimesRepository,
    private val settingsRepository: SettingsRepository
) {
    /**
     * @param date the day as its "dd-MM-yyyy" storage key
     * @param coordinates the location the cached timings must match
     * @return the adjusted cached timings, or null on a cache miss
     */
    suspend operator fun invoke(
        date: String,
        coordinates: Coordinates
    ): PrayerTimesResult? {
        val settings = settingsRepository.observeSettings().first()
        return prayerTimesRepository.getCachedTimings(
            date,
            coordinates,
            method = settings.calculationMethod.apiMethodId,
            madhab = settings.madhab
        )?.let { result ->
            result.copy(date = result.date.applyHijriAdjustment(settings.hijriDateOffset))
        }
    }
}