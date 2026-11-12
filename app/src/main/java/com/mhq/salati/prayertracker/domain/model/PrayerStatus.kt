package com.mhq.salati.prayertracker.domain.model

/**
 * What the user recorded for one prayer on one day.
 *
 * [PENDING] is the default state of an unmarked prayer;
 * it is usually implicit (absent from storage) rather than written as a row.
 */
enum class PrayerStatus {
    /** Not marked yet. */
    PENDING,

    /** Marked as prayed. */
    PRAYED,

    /** Marked as missed. */
    MISSED
}