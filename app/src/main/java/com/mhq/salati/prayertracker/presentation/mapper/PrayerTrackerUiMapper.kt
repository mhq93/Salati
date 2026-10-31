package com.mhq.salati.prayertracker.presentation.mapper

import com.mhq.salati.prayertracker.domain.model.DayStatus
import com.mhq.salati.prayertracker.domain.model.PrayerStatus
import com.mhq.salati.prayertracker.presentation.model.DayStatusUi
import com.mhq.salati.prayertracker.presentation.model.PrayerStatusUi
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.presentation.mapper.toUi
import com.mhq.salati.shared.presentation.model.PrayerNameUi

fun DayStatus.toUi(): DayStatusUi = when (this) {
    DayStatus.FUTURE -> DayStatusUi.FUTURE
    DayStatus.ALL_PRAYED -> DayStatusUi.ALL_PRAYED
    DayStatus.HAS_MISSED -> DayStatusUi.HAS_MISSED
    DayStatus.IN_PROGRESS -> DayStatusUi.IN_PROGRESS
}

fun PrayerStatus.toUi(): PrayerStatusUi = when (this) {
    PrayerStatus.PENDING -> PrayerStatusUi.PENDING
    PrayerStatus.PRAYED -> PrayerStatusUi.PRAYED
    PrayerStatus.MISSED -> PrayerStatusUi.MISSED
}

fun PrayerStatusUi.toDomain(): PrayerStatus = when (this) {
    PrayerStatusUi.PENDING -> PrayerStatus.PENDING
    PrayerStatusUi.PRAYED -> PrayerStatus.PRAYED
    PrayerStatusUi.MISSED -> PrayerStatus.MISSED
}

fun Map<PrayerName, PrayerStatus>.toUiMap(): Map<PrayerNameUi, PrayerStatusUi> =
    entries.associate { (name, status) -> name.toUi() to status.toUi() }