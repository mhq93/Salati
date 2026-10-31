package com.mhq.salati.home.presentation.model

import com.mhq.salati.shared.presentation.model.PrayerNameUi
import java.time.LocalTime

/**
 * The clock time of every prayer and timing for one day.
 * The mapper fills one entry per [PrayerNameUi], so [get] never misses.
 * Formatting for the screen (12/24 hour, locale) is the UI's job.
 */
data class PrayerTimingsUi(
    private val times: Map<PrayerNameUi, LocalTime>
) {
    operator fun get(name: PrayerNameUi): LocalTime = times.getValue(name)
}