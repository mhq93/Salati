package com.mhq.salati.alarms.data.local

import com.mhq.salati.alarms.domain.model.CustomAlarm
import com.mhq.salati.alarms.domain.model.OffsetDirection

fun CustomAlarmEntity.toDomain(): CustomAlarm =
    CustomAlarm(
        id = id,
        prayerName = prayerName,
        label = label,
        offsetMinutes = offsetMinutes,
        offsetDirection = OffsetDirection.valueOf(offsetDirection),
        everyDay = everyDay,
        activeDays = activeDays.split(",").filter { it.isNotBlank() }.map { it.toInt() }.toSet(),
        isEnabled = isEnabled
    )

fun CustomAlarm.toEntity(): CustomAlarmEntity =
    CustomAlarmEntity(
        id = id,
        prayerName = prayerName,
        label = label,
        offsetMinutes = offsetMinutes,
        offsetDirection = offsetDirection.name,
        everyDay = everyDay,
        activeDays = activeDays.joinToString(","),
        isEnabled = isEnabled
    )