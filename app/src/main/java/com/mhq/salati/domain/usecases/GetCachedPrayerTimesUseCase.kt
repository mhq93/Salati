package com.mhq.salati.domain.usecases

import com.mhq.salati.domain.model.prayers.PrayerTimesResult
import com.mhq.salati.domain.repo.PrayerTimesRepository
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