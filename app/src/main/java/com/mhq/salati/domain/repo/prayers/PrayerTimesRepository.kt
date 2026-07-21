package com.mhq.salati.domain.repo.prayers

import com.mhq.salati.domain.model.prayers.PrayerTimesResult

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