package com.mhq.salati.shared.domain

/**
 * Every named timing the app tracks, major prayers first, then minor timings.
 *
 * @property isMinorTiming true for auxiliary timings (Imsak, Shorouq, the third-of-night marks)
 * displayed for reference but are not one of the five daily prayers a user can mark as prayed
 */
enum class PrayerName(val isMinorTiming: Boolean) {
    FAJR(false),
    DHUHR(false),
    ASR(false),
    MAGHRIB(false),
    ISHA(false),
    IMSAK(true),
    SHOROUQ(true),
    FIRST_THIRD(true),
    MIDNIGHT(true),
    LAST_THIRD(true);

    companion object {
        /** The five daily prayers, in observance order; the ones a user can track. */
        val majorEntries: List<PrayerName> = entries.filter { !it.isMinorTiming }
    }
}