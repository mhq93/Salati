package com.mhq.salati.shared.domain

/**
 * Turns the text a prayer travels as (inside alarm Intents and saved rows) back into a [PrayerName].
 * Implemented in Data, which owns that text.
 */
interface PrayerNameResolver {
    fun resolve(key: String): PrayerName?
}