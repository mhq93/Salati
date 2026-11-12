package com.mhq.salati.home.domain.model

import com.mhq.salati.prayertimes.domain.model.PrayerDate
import com.mhq.salati.prayertimes.domain.model.PrayerTimings

/**
 * The steps of loading a day's prayer times, in the order they happen.
 *
 * A load emits [FetchingFromNetwork] only on a cache miss,
 * then either [Loaded] or [Failed] exactly once; the flow completes after that.
 */
sealed interface PrayerTimesLoad {
    /** Nothing was cached, so a network request is now in flight. */
    data object FetchingFromNetwork : PrayerTimesLoad

    /** Times are on screen: the timings themselves, the date they belong to, and the day's status. */
    data class Loaded(
        val timings: PrayerTimings,
        val prayerDate: PrayerDate,
        val status: PrayerProgress
    ) : PrayerTimesLoad

    /** The load failed; see [PrayerTimesError] for why. */
    data class Failed(val error: PrayerTimesError) : PrayerTimesLoad
}