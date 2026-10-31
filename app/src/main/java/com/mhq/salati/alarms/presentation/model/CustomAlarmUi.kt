package com.mhq.salati.alarms.presentation.model

import com.mhq.salati.shared.presentation.model.PrayerNameUi

/** A custom alarm as the alarms screen shows and edits it. */
data class CustomAlarmUi(
    val id: Long = 0L,
    val prayerName: PrayerNameUi,
    val label: String,
    val offsetMinutes: Int,
    val offsetDirection: OffsetDirectionUi,
    val everyDay: Boolean,
    val activeDays: Set<Int>,
    val isEnabled: Boolean = true
)