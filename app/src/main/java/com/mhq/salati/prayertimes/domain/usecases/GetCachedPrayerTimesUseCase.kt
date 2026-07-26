package com.mhq.salati.prayertimes.domain.usecases

import com.mhq.salati.prayertimes.domain.model.PrayerTimesResult
import com.mhq.salati.prayertimes.domain.repo.PrayerTimesRepository
import javax.inject.Inject

class GetCachedPrayerTimesUseCase @Inject constructor(
    private val prayerTimesRepository: PrayerTimesRepository
) {
    suspend operator fun invoke(
        date: String,
        latitude: Double,
        longitude: Double,
        method: Int = 5
    ): PrayerTimesResult? {
        return prayerTimesRepository.getCachedTimings(date, latitude, longitude, method)
    }
}