package com.mhq.salati.shared.domain

/**
 * Turns the text a prayer travels as (inside alarm Intents and saved rows) back into a [PrayerName].
 * Implemented in Data, which owns that text.
 *
 * The string keys are owned by the Data layer (alarm Intent extras and persisted rows),
 * so the mapping lives there too. Domain code only sees the resolved [PrayerName],
 * or null when the key is unknown or stale.
 */
interface PrayerNameResolver {
    /**
     * Resolves [key] to the [PrayerName] it stands for.
     *
     * @return the matching prayer, or null when the key is not a known prayer key
     */
    fun resolve(key: String): PrayerName?
}