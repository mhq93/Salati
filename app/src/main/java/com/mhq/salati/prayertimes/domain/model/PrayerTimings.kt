package com.mhq.salati.prayertimes.domain.model

import com.mhq.salati.shared.domain.PrayerName
import java.time.LocalTime

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
    // Exhaustive `when` (no else):
    // adding a PrayerName without deciding which timing it maps to
    // becomes a compile error instead of a silent gap.
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