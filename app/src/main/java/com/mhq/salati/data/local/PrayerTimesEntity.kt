package com.mhq.salati.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prayer_timings")
data class PrayerTimesEntity(
    @PrimaryKey
    val date: String,
    val fajr: String,
    val sunrise: String,
    val dhuhr: String,
    val asr: String,
    val sunset: String,
    val maghrib: String,
    val isha: String,
    val imsak: String,
    val midnight: String,
    val firstThird: String,
    val lastThird: String,
    val readableDate: String,
    val gregorianDate: String,
    val hijriDate: String,
    val hijriMonthName: String,
    val hijriYear: String,
    val latitude: Double,
    val longitude: Double
)