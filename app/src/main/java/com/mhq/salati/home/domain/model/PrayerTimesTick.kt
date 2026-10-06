package com.mhq.salati.home.domain.model

import java.time.LocalDate

/** What happens, in order, while a day's prayer times stay on screen. */

sealed interface PrayerTimesTick {
    data class Countdown(val countdown: PrayerCountdown) : PrayerTimesTick

    /** A countdown window ended and the next one has started. */
    data class StatusRefreshed(val status: PrayerProgress) : PrayerTimesTick

    /** The window ran into the next day, so the screen should move on to [date]. */
    data class DayRolledOver(val date: LocalDate) : PrayerTimesTick
}