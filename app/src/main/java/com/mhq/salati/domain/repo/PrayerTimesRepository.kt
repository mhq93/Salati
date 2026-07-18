package com.mhq.salati.domain.repo

import com.mhq.salati.domain.model.PrayerTimesResult

interface PrayerTimesRepository {
    suspend fun getTimings(
        date: String,
        latitude: Double,
        longitude: Double,
        method: Int = 5
    ): Result<PrayerTimesResult>
}