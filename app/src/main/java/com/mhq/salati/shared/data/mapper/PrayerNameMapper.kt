package com.mhq.salati.shared.data.mapper

import com.mhq.salati.shared.domain.PrayerName

// The text a prayer is saved as in Room rows and Intent extras.
// These values are already stored on users' devices (and inside scheduled alarms), so never change them.

val PrayerName.storageKey: String
    get() = when (this) {
        PrayerName.FAJR -> "Fajr"
        PrayerName.DHUHR -> "Dhuhr"
        PrayerName.ASR -> "Asr"
        PrayerName.MAGHRIB -> "Maghrib"
        PrayerName.ISHA -> "Isha"
        PrayerName.IMSAK -> "Imsak"
        PrayerName.SHOROUQ -> "Shorouq"
        PrayerName.FIRST_THIRD -> "First Third"
        PrayerName.MIDNIGHT -> "Midnight"
        PrayerName.LAST_THIRD -> "Last Third"
    }

// Declared on the companion so existing `PrayerName.fromStorageKey(key)` calls keep working (they only need this import).
fun PrayerName.Companion.fromStorageKey(key: String): PrayerName? =
    PrayerName.entries.find { it.storageKey == key }