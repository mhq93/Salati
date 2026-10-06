package com.mhq.salati.adhan.datasource.database

import androidx.room.Entity

@Entity(
    tableName = "muted_prayers",
    primaryKeys = ["date", "prayerName"]
)
data class MutedPrayerEntity(
    val date: String,
    val prayerName: String,
    val epochDay: Long
)