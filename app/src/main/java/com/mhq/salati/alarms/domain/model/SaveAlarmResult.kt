package com.mhq.salati.alarms.domain.model

/** Outcome of saving a custom alarm. */
enum class SaveAlarmResult {
    /** The alarm was created or updated. */
    SAVED,
    /** The alarm would fire at the same time as another alarm, so nothing was saved. */
    CONFLICT
}