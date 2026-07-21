package com.mhq.salati.domain.usecases.prayers

import com.mhq.salati.domain.model.prayers.PrayerTimesResult
import com.mhq.salati.domain.repo.prayers.PrayerTimesRepository
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