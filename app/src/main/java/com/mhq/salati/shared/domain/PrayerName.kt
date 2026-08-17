package com.mhq.salati.shared.domain

import androidx.annotation.StringRes
import com.mhq.salati.R

enum class PrayerName(
    @StringRes val labelRes: Int,
    val storageKey: String,
    val isMinorTiming: Boolean
) {
    FAJR(R.string.fajr, "Fajr", false),
    DHUHR(R.string.dhuhr, "Dhuhr", false),
    ASR(R.string.asr, "Asr", false),
    MAGHRIB(R.string.maghrib, "Maghrib", false),
    ISHA(R.string.isha, "Isha", false),
    IMSAK(R.string.imsak, "Imsak", true),
    SHOROUQ(R.string.shorouq, "Shorouq", true),
    FIRST_THIRD(R.string.first_third, "First Third", true),
    MIDNIGHT(R.string.midnight, "Midnight", true),
    LAST_THIRD(R.string.last_third, "Last Third", true);

    companion object {
        fun fromStorageKey(key: String): PrayerName? = entries.find { it.storageKey == key }
        val majorEntries: List<PrayerName> = entries.filter { !it.isMinorTiming }
    }
}