package com.mhq.salati.domain.usecases

import com.mhq.salati.domain.model.prayers.PrayerTimesResult
import com.mhq.salati.domain.repo.PrayerTimesRepository
import javax.inject.Inject

class GetPrayerTimesUseCase @Inject constructor(
    private val repository: PrayerTimesRepository
) {
    suspend operator fun invoke(
        date: String,
        latitude: Double,
        longitude: Double,
        method: Int = 5
    ): Result<PrayerTimesResult> {
        return repository.getTimings(
            date,
            latitude,
            longitude,
            method
        )
    }
}