package com.mhq.salati.alarms.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "custom_alarms")
data class CustomAlarmEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val prayerName: String,
    val label: String,
    val offsetMinutes: Int,
    val offsetDirection: String,
    val everyDay: Boolean,
    val activeDays: String,
    val isEnabled: Boolean
)