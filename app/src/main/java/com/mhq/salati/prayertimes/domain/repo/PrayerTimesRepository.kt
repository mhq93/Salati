package com.mhq.salati.prayertimes.domain.repo

import com.mhq.salati.prayertimes.domain.model.PrayerTimesResult
import com.mhq.salati.settings.domain.model.Madhab
import com.mhq.salati.shared.domain.Coordinates

/**
 * Source of prayer times, implemented in Data over the calculation API and cache.
 * Both methods take the calculation parameters explicitly (with defaults
 * matching the API's own defaults) so callers decide which configuration a
 * result belongs to; cached results are only valid for the same parameters.
 */
interface PrayerTimesRepository {
    /**
     * Fetches the day's timings from the network.
     *
     * @param date the day as its "dd-MM-yyyy" storage key
     * @param coordinates the location to calculate times for
     * @param method the calculation method's API id
     * @param madhab the madhab used for Asr
     * @param adjustment minute-level adjustment applied to every timing
     * @return the timings, or the failure reason
     */
    suspend fun getPrayerTimings(
        date: String,
        coordinates: Coordinates,
        method: Int = 5,
        madhab: Madhab = Madhab.SHAFI,
        adjustment: Int = 0
    ): Result<PrayerTimesResult>

    /**
     * Reads the day's timings from cache only; never touches the network.
     *
     * Same parameters as [getPrayerTimings]; a cache entry stored under
     * different parameters is not returned.
     *
     * @return the cached timings, or null on a cache miss
     */
    suspend fun getCachedTimings(
        date: String,
        coordinates: Coordinates,
        method: Int = 5,
        madhab: Madhab = Madhab.SHAFI,
        adjustment: Int = 0
    ): PrayerTimesResult?
}