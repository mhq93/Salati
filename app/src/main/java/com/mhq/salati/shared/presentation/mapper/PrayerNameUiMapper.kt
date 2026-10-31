package com.mhq.salati.shared.presentation.mapper

import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.presentation.model.PrayerNameUi

/** Both directions use an exhaustive `when` (no else):
 * adding a prayer to one enum without the other becomes a compile error */

fun PrayerName.toUi(): PrayerNameUi = when (this) {
    PrayerName.FAJR -> PrayerNameUi.FAJR
    PrayerName.DHUHR -> PrayerNameUi.DHUHR
    PrayerName.ASR -> PrayerNameUi.ASR
    PrayerName.MAGHRIB -> PrayerNameUi.MAGHRIB
    PrayerName.ISHA -> PrayerNameUi.ISHA
    PrayerName.IMSAK -> PrayerNameUi.IMSAK
    PrayerName.SHOROUQ -> PrayerNameUi.SHOROUQ
    PrayerName.FIRST_THIRD -> PrayerNameUi.FIRST_THIRD
    PrayerName.MIDNIGHT -> PrayerNameUi.MIDNIGHT
    PrayerName.LAST_THIRD -> PrayerNameUi.LAST_THIRD
}

fun PrayerNameUi.toDomain(): PrayerName = when (this) {
    PrayerNameUi.FAJR -> PrayerName.FAJR
    PrayerNameUi.DHUHR -> PrayerName.DHUHR
    PrayerNameUi.ASR -> PrayerName.ASR
    PrayerNameUi.MAGHRIB -> PrayerName.MAGHRIB
    PrayerNameUi.ISHA -> PrayerName.ISHA
    PrayerNameUi.IMSAK -> PrayerName.IMSAK
    PrayerNameUi.SHOROUQ -> PrayerName.SHOROUQ
    PrayerNameUi.FIRST_THIRD -> PrayerName.FIRST_THIRD
    PrayerNameUi.MIDNIGHT -> PrayerName.MIDNIGHT
    PrayerNameUi.LAST_THIRD -> PrayerName.LAST_THIRD
}

fun Set<PrayerName>.toUiSet(): Set<PrayerNameUi> = mapTo(mutableSetOf()) { it.toUi() }