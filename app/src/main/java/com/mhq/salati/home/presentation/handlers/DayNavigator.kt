package com.mhq.salati.home.presentation.handlers

import com.mhq.salati.home.domain.model.PrayerWindow
import com.mhq.salati.shared.domain.Clock
import java.time.LocalDate
import javax.inject.Inject

/** The rules for which day the Home screen may show. */
class DayNavigator @Inject constructor(
    private val clock: Clock
) {
    fun isToday(date: LocalDate): Boolean = date == clock.today()

    /** The day before [date], or null when [date] is today: browsing into the past isn't allowed. */
    fun previousOrNull(date: LocalDate): LocalDate? =
        if (isToday(date)) null else date.minusDays(1)

    fun next(date: LocalDate): LocalDate = date.plusDays(1)

    /** The following day when [window] runs past [date] (after Isha, into tomorrow's Fajr); otherwise null. */
    fun rolloverDate(date: LocalDate, window: PrayerWindow?): LocalDate? =
        if (window?.endsAfterDay(date, clock.zone()) == true) date.plusDays(1) else null
}