package com.mhq.salati.prayertracker.datasource.database

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "prayer_records",
    primaryKeys = ["date", "prayer"],
    indices = [Index(value = ["date"])]
)
data class PrayerRecordEntity(
    val date: String,
    val prayer: String,
    val status: String
)