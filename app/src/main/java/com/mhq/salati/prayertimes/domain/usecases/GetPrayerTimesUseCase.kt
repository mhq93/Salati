package com.mhq.salati.prayertimes.domain.usecases

import com.mhq.salati.prayertimes.domain.model.PrayerTimesResult
import com.mhq.salati.prayertimes.domain.repo.PrayerTimesRepository
import javax.inject.Inject

class GetPrayerTimesUseCase @Inject constructor(
    private val prayerTimesRepository: PrayerTimesRepository
) {
    suspend operator fun invoke(
        date: String,
        latitude: Double,
        longitude: Double,
        method: Int = 5
    ): Result<PrayerTimesResult> {
        return prayerTimesRepository.getPrayerTimings(
            date,
            latitude,
            longitude,
            method
        )
    }
}