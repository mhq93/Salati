package com.mhq.salati.prayertimes.domain.model

import com.mhq.salati.shared.domain.PrayerName
import java.time.LocalTime

/**
 * All eleven timings of one day, in the order the calculation API returns them.
 * Every [PrayerName] has a timing here, including the minor ones (Imsak,
 * Shorouq, the third-of-night marks) that are displayed but not tracked.
 */
data class PrayerTimings(
    val imsak: LocalTime,
    val fajr: LocalTime,
    val sunrise: LocalTime,
    val dhuhr: LocalTime,
    val asr: LocalTime,
    val sunset: LocalTime,
    val maghrib: LocalTime,
    val isha: LocalTime,
    val midnight: LocalTime,
    val firstThird: LocalTime,
    val lastThird: LocalTime
) {
    /**
     * The time a [PrayerName] refers to on this day.
     * Note the two renames the API needs: SHOROUQ maps to [sunrise].
     */
    operator fun get(name: PrayerName): LocalTime = when (name) {
        PrayerName.IMSAK -> imsak
        PrayerName.FAJR -> fajr
        PrayerName.DHUHR -> dhuhr
        PrayerName.ASR -> asr
        PrayerName.MAGHRIB -> maghrib
        PrayerName.ISHA -> isha
        PrayerName.SHOROUQ -> sunrise
        PrayerName.FIRST_THIRD -> firstThird
        PrayerName.MIDNIGHT -> midnight
        PrayerName.LAST_THIRD -> lastThird
    }
}