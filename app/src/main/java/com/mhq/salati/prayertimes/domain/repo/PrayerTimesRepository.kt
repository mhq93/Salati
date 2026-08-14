package com.mhq.salati.prayertimes.domain.repo

import com.mhq.salati.prayertimes.domain.model.PrayerTimesResult
import com.mhq.salati.settings.domain.model.Madhab

interface PrayerTimesRepository {
    suspend fun getPrayerTimings(
        date: String,
        latitude: Double,
        longitude: Double,
        method: Int = 5,
        madhab: Madhab = Madhab.SHAFI
    ): Result<PrayerTimesResult>

    suspend fun getCachedTimings(
        date: String,
        latitude: Double,
        longitude: Double,
        method: Int = 5,
        madhab: Madhab = Madhab.SHAFI
    ): PrayerTimesResult?
}