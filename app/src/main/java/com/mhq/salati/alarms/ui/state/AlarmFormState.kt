package com.mhq.salati.alarms.ui.state

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.mhq.salati.alarms.presentation.model.CustomAlarmUi
import com.mhq.salati.alarms.presentation.model.OffsetDirectionUi
import com.mhq.salati.alarms.ui.components.WeekDays
import com.mhq.salati.shared.presentation.model.PrayerNameUi

@Stable
class AlarmFormState(existing: CustomAlarmUi?) {
    val prayerName = mutableStateOf(existing?.prayerName ?: PrayerNameUi.entries.first())
    val label = mutableStateOf(existing?.label ?: "")
    val minutesText = mutableStateOf((existing?.offsetMinutes ?: 5).toString())
    val offsetDirection = mutableStateOf(existing?.offsetDirection ?: OffsetDirectionUi.BEFORE)
    val activeDays = mutableStateOf(
        if (existing == null || existing.everyDay) WeekDays.map { it.first }.toSet()
        else existing.activeDays
    )
}