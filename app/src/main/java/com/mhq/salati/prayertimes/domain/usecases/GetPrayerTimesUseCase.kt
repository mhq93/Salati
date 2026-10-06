package com.mhq.salati.prayertimes.domain.usecases

import com.mhq.salati.prayertimes.domain.model.applyHijriAdjustment
import com.mhq.salati.prayertimes.domain.model.PrayerTimesResult
import com.mhq.salati.prayertimes.domain.repo.PrayerTimesRepository
import com.mhq.salati.settings.domain.repo.SettingsRepository
import com.mhq.salati.shared.domain.Coordinates
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetPrayerTimesUseCase @Inject constructor(
    private val prayerTimesRepository: PrayerTimesRepository,
    private val settingsRepository: SettingsRepository
) {
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