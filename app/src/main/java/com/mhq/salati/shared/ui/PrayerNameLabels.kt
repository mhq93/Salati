package com.mhq.salati.shared.ui

import androidx.annotation.StringRes
import com.mhq.salati.R
import com.mhq.salati.shared.presentation.model.PrayerNameUi

val PrayerNameUi.labelRes: Int
    @StringRes get() = when (this) {
        PrayerNameUi.FAJR -> R.string.fajr
        PrayerNameUi.DHUHR -> R.string.dhuhr
        PrayerNameUi.ASR -> R.string.asr
        PrayerNameUi.MAGHRIB -> R.string.maghrib
        PrayerNameUi.ISHA -> R.string.isha
        PrayerNameUi.IMSAK -> R.string.imsak
        PrayerNameUi.SHOROUQ -> R.string.shorouq
        PrayerNameUi.FIRST_THIRD -> R.string.first_third
        PrayerNameUi.MIDNIGHT -> R.string.midnight
        PrayerNameUi.LAST_THIRD -> R.string.last_third
    }