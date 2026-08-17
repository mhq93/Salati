package com.mhq.salati.shared.domain

object PrayerAlarmNames {
    val MAJOR = PrayerName.entries.filter { !it.isMinorTiming }.map { it.storageKey }
    val MINOR = PrayerName.entries.filter { it.isMinorTiming }.map { it.storageKey }
    val ALL = MAJOR + MINOR
}