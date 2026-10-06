package com.mhq.salati.shared.domain

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
        val majorEntries: List<PrayerName> = entries.filter { !it.isMinorTiming }
    }
}