package com.mhq.salati.prayertracker.domain.model

/**
 * How a calendar day went, as color-coded for the tracker calendar.
 * Derived per day from its records; see ObserveMonthDayStatusUseCase for the mapping rules.
 */
enum class DayStatus {
    /** The day has not happened yet; never colored. */
    FUTURE,

    /** All five daily prayers were marked PRAYED. */
    ALL_PRAYED,

    /** At least one prayer was marked MISSED. */
    HAS_MISSED,

    /** The day is today or a past day with neither a miss nor a full set. */
    IN_PROGRESS
}