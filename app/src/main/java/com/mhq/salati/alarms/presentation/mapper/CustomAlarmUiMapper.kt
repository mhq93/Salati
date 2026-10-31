package com.mhq.salati.alarms.presentation.mapper

import com.mhq.salati.alarms.domain.model.CustomAlarm
import com.mhq.salati.alarms.domain.model.OffsetDirection
import com.mhq.salati.alarms.domain.model.conflictsWith as domainConflictsWith
import com.mhq.salati.alarms.presentation.model.CustomAlarmUi
import com.mhq.salati.alarms.presentation.model.OffsetDirectionUi
import com.mhq.salati.shared.presentation.mapper.toDomain
import com.mhq.salati.shared.presentation.mapper.toUi

fun OffsetDirection.toUi(): OffsetDirectionUi = when (this) {
    OffsetDirection.BEFORE -> OffsetDirectionUi.BEFORE
    OffsetDirection.AFTER -> OffsetDirectionUi.AFTER
}

fun OffsetDirectionUi.toDomain(): OffsetDirection = when (this) {
    OffsetDirectionUi.BEFORE -> OffsetDirection.BEFORE
    OffsetDirectionUi.AFTER -> OffsetDirection.AFTER
}

/**
 * Reads the direction key the custom alarm receiver gets in its Intent.
 * CustomAlarmSchedulerImpl writes it as `OffsetDirection.name`.
 * A missing or unknown key gives null.
 */
fun String?.toOffsetDirectionUiOrNull(): OffsetDirectionUi? =
    this?.let { key -> runCatching { OffsetDirection.valueOf(key) }.getOrNull() }?.toUi()

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