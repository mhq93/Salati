package com.mhq.salati.shared.ui

import androidx.annotation.StringRes
import com.mhq.salati.R
import com.mhq.salati.shared.domain.PrayerName

// The user-facing name of a prayer.
// Lives here (not in the domain enum)
// Because it is an Android resource.

val PrayerName.labelRes: Int
    @StringRes get() = when (this) {
        PrayerName.FAJR -> R.string.fajr
        PrayerName.DHUHR -> R.string.dhuhr
        PrayerName.ASR -> R.string.asr
        PrayerName.MAGHRIB -> R.string.maghrib
        PrayerName.ISHA -> R.string.isha
        PrayerName.IMSAK -> R.string.imsak
        PrayerName.SHOROUQ -> R.string.shorouq
        PrayerName.FIRST_THIRD -> R.string.first_third
        PrayerName.MIDNIGHT -> R.string.midnight
        PrayerName.LAST_THIRD -> R.string.last_third
    }