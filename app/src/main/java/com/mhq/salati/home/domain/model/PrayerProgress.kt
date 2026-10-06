package com.mhq.salati.home.domain.model

import com.mhq.salati.shared.domain.PrayerName

/** Where the day stands right now: the countdown window,
 * the prayer in progress and the ones already passed. */
data class PrayerProgress(
    val window: PrayerWindow,
    val currentPrayer: PrayerName?,
    val pastPrayers: Set<PrayerName>
)