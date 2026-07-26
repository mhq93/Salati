package com.mhq.salati.adhan.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "muted_prayers")
data class MutedPrayerEntity(
    @PrimaryKey val prayerName: String
)