package com.mhq.salati.home.domain.model

import com.mhq.salati.prayertimes.domain.model.PrayerDate
import com.mhq.salati.prayertimes.domain.model.PrayerTimings

/** The steps of loading a day's prayer times, in the order they happen. */
sealed interface PrayerTimesLoad {
    /** Nothing was cached, so a network request is now in flight. */
    data object FetchingFromNetwork : PrayerTimesLoad

    data class Loaded(
        val timings: PrayerTimings,
        val prayerDate: PrayerDate,
        val status: PrayerProgress
    ) : PrayerTimesLoad

    data class Failed(val error: PrayerTimesError) : PrayerTimesLoad
}

sealed interface PrayerTimesError {
    data object Offline : PrayerTimesError
    data object TimedOut : PrayerTimesError
    data class Other(val message: String?) : PrayerTimesError
}