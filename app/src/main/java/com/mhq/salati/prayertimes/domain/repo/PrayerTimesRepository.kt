package com.mhq.salati.prayertimes.domain.repo

import com.mhq.salati.prayertimes.domain.model.PrayerTimesResult

interface PrayerTimesRepository {
    suspend fun getPrayerTimings(
        date: String,
        latitude: Double,
        longitude: Double,
        method: Int = 5
    ): Result<PrayerTimesResult>

    suspend fun getCachedTimings(
        date: String,
        latitude: Double,
        longitude: Double,
        method: Int = 5
    ): PrayerTimesResult?
}