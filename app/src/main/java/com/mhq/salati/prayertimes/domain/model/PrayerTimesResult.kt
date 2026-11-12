package com.mhq.salati.prayertimes.domain.model

/**
 * A day's prayer times together with the date they belong to.
 *
 * @property timings the day's timings
 * @property date the Gregorian and Hijri dates those timings are for
 */
data class PrayerTimesResult(
    val timings: PrayerTimings,
    val date: PrayerDate
)