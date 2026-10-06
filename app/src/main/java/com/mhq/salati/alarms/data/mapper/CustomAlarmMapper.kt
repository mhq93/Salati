package com.mhq.salati.alarms.data.mapper

import com.mhq.salati.alarms.datasource.database.CustomAlarmEntity
import com.mhq.salati.alarms.domain.model.CustomAlarm
import com.mhq.salati.alarms.domain.model.OffsetDirection
import com.mhq.salati.shared.data.mapper.fromStorageKey
import com.mhq.salati.shared.data.mapper.storageKey
import com.mhq.salati.shared.domain.PrayerName

// Returns null for a row whose prayer key this version doesn't recognise; the repository skips such rows.
fun CustomAlarmEntity.toDomain(): CustomAlarm? {
    val prayer = PrayerName.fromStorageKey(prayerName) ?: return null
    return CustomAlarm(
        id = id,
        prayerName = prayer,
        label = label,
        offsetMinutes = offsetMinutes,
        offsetDirection = OffsetDirection.valueOf(offsetDirection),
        everyDay = everyDay,
        activeDays = activeDays.split(",").filter { it.isNotBlank() }.map { it.toInt() }.toSet(),
        isEnabled = isEnabled
    )
}

fun CustomAlarm.toEntity(): CustomAlarmEntity =
    CustomAlarmEntity(
        id = id,
        prayerName = prayerName.storageKey,
        label = label,
        offsetMinutes = offsetMinutes,
        offsetDirection = offsetDirection.name,
        everyDay = everyDay,
        activeDays = activeDays.joinToString(","),
        isEnabled = isEnabled
    )