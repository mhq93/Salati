package com.mhq.salati.prayertimes.domain.usecases

import com.mhq.salati.prayertimes.domain.model.PrayerTimesResult
import com.mhq.salati.prayertimes.domain.repo.PrayerTimesRepository
import com.mhq.salati.settings.domain.repo.SettingsRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetCachedPrayerTimesUseCase @Inject constructor(
    private val prayerTimesRepository: PrayerTimesRepository,
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(
        date: String,
        latitude: Double,
        longitude: Double
    ): PrayerTimesResult? {
        val settings = settingsRepository.observeSettings().first()
        return prayerTimesRepository.getCachedTimings(
            date,
            latitude,
            longitude,
            method = settings.calculationMethod.apiMethodId,
            madhab = settings.madhab
        )
    }
}