package com.mhq.salati.home.domain.model

import com.mhq.salati.shared.domain.PrayerName

/**
 * Where the day stands right now: the countdown window,
 * the prayer in progress and the ones already passed.
 *
 * @property window the countdown window the screen draws its ring and timer from
 * @property currentPrayer the prayer whose window is running now,
 * or null when the browsed day is not today (past and future days have no "current" prayer)
 * @property pastPrayers the day's timings that have already passed
 */
data class PrayerProgress(
    val window: PrayerWindow,
    val currentPrayer: PrayerName?,
    val pastPrayers: Set<PrayerName>
)