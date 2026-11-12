package com.mhq.salati.prayertimes.domain.usecases

import com.mhq.salati.prayertimes.domain.model.applyHijriAdjustment
import com.mhq.salati.prayertimes.domain.model.PrayerTimesResult
import com.mhq.salati.prayertimes.domain.repo.PrayerTimesRepository
import com.mhq.salati.settings.domain.repo.SettingsRepository
import com.mhq.salati.shared.domain.Coordinates
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Fetches a day's timings from the network, configured with the user's current settings.
 * Fills in the calculation method and madhab from [SettingsRepository]
 * and applies the stored Hijri date offset to the result,
 * mirroring [GetCachedPrayerTimesUseCase] so both sources return identically shaped data.
 */
class GetPrayerTimesUseCase @Inject constructor(
    private val prayerTimesRepository: PrayerTimesRepository,
    private val settingsRepository: SettingsRepository
) {
    /**
     * @param date the day as its "dd-MM-yyyy" storage key
     * @param coordinates the location to calculate times for
     * @return the adjusted timings, or the failure reason
     */
    suspend operator fun invoke(
        date: String,
        coordinates: Coordinates
    ): Result<PrayerTimesResult> {
        val settings = settingsRepository.observeSettings().first()
        return prayerTimesRepository.getPrayerTimings(
            date,
            coordinates,
            method = settings.calculationMethod.apiMethodId,
            madhab = settings.madhab
        ).map { result ->
            result.copy(date = result.date.applyHijriAdjustment(settings.hijriDateOffset))
        }
    }
}