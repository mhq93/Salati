package com.mhq.salati.alarms.presentation.mapper

import com.mhq.salati.alarms.domain.model.CustomAlarm
import com.mhq.salati.alarms.presentation.model.CustomAlarmUi
import com.mhq.salati.shared.presentation.mapper.toDomain
import com.mhq.salati.shared.presentation.mapper.toUi
import com.mhq.salati.alarms.domain.model.conflictsWith as domainConflictsWith

fun CustomAlarm.toUi(): CustomAlarmUi = CustomAlarmUi(
    id = id,
    prayerName = prayerName.toUi(),
    label = label,
    offsetMinutes = offsetMinutes,
    offsetDirection = offsetDirection.toUi(),
    everyDay = everyDay,
    activeDays = activeDays,
    isEnabled = isEnabled
)

fun CustomAlarmUi.toDomain(): CustomAlarm = CustomAlarm(
    id = id,
    prayerName = prayerName.toDomain(),
    label = label,
    offsetMinutes = offsetMinutes,
    offsetDirection = offsetDirection.toDomain(),
    everyDay = everyDay,
    activeDays = activeDays,
    isEnabled = isEnabled
)

/**
 * True when this alarm and [other] would ring at the same time on a shared day.
 * The rule itself stays in the domain; this only lends it to the screens.
 */
fun CustomAlarmUi.conflictsWith(other: CustomAlarmUi): Boolean =
    toDomain().domainConflictsWith(other.toDomain())