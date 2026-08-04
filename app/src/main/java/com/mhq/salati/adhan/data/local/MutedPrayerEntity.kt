package com.mhq.salati.adhan.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "muted_prayers",
    primaryKeys = ["date", "prayerName"]
)
data class MutedPrayerEntity(
    val date: String,
    val prayerName: String,
    val epochDay: Long
)