package com.mhq.salati.shared.domain

object PrayerAlarmNames {
    val MAJOR = listOf("Fajr", "Dhuhr", "Asr", "Maghrib", "Isha")
    val MINOR = listOf("Imsak", "Shorouq", "First Third", "Midnight", "Last Third")
    val ALL = MAJOR + MINOR
}