package com.mhq.salati.testutil

import com.mhq.salati.prayertimes.domain.model.PrayerTimesResult
import com.mhq.salati.prayertimes.domain.repo.PrayerTimesRepository
import com.mhq.salati.settings.domain.model.Madhab
import com.mhq.salati.shared.domain.Coordinates
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

class FakePrayerTimesRepository(
    private val cached: Map<String, PrayerTimesResult> = emptyMap(),
    private val network: Map<String, Result<PrayerTimesResult>> = emptyMap(),
    private val networkDelayMillis: Long = 0
) : PrayerTimesRepository {
    override suspend fun getPrayerTimings(
        date: String,
        coordinates: Coordinates,
        method: Int,
        madhab: Madhab,
        adjustment: Int
    ): Result<PrayerTimesResult> {
        delay(networkDelayMillis.milliseconds)
        return network[date] ?: Result.failure(IllegalStateException("no network fixture for $date"))
    }

    override suspend fun getCachedTimings(
        date: String,
        coordinates: Coordinates,
        method: Int,
        madhab: Madhab,
        adjustment: Int
    ): PrayerTimesResult? = cached[date]
}