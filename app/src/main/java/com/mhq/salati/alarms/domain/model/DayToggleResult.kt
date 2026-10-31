package com.mhq.salati.alarms.domain.model

/** Outcome of switching one weekday on an alarm. */
enum class DayToggleResult {
    /** The change was saved and alarms were rescheduled. */
    APPLIED,
    /** Nothing changed (alarm missing, or the tap would remove the last remaining day). */
    UNCHANGED,
    /** The change would make this alarm fire at the same time as another alarm. */
    CONFLICT
}