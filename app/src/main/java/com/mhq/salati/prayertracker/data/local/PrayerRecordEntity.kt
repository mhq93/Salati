package com.mhq.salati.prayertracker.data.local

import androidx.room.Entity

@Entity(tableName = "prayer_records", primaryKeys = ["date", "prayer"])
data class PrayerRecordEntity(
    val date: String,   // ISO_LOCAL_DATE, e.g. 2026-07-26
    val prayer: String, // PrayerType.name
    val status: String  // PrayerStatus.name
)