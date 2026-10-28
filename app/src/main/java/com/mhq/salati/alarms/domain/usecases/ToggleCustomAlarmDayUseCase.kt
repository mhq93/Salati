package com.mhq.salati.alarms.domain.usecases

import com.mhq.salati.alarms.domain.model.conflictsWith
import com.mhq.salati.alarms.domain.model.withDayToggled
import com.mhq.salati.alarms.domain.repo.CustomAlarmRepository
import javax.inject.Inject

/** Outcome of switching one weekday on an alarm. */
enum class DayToggleResult {
    /** The change was saved and alarms were rescheduled. */
    APPLIED,

    /** Nothing changed (alarm missing, or the tap would remove the last remaining day). */
    UNCHANGED,

    /** The change would make this alarm fire at the same time as another alarm. */
    CONFLICT
}

/**
 * Switches one weekday on or off for the alarm with the given [id],
 * then reschedules so the change takes effect immediately.
 *
 * Reads the alarm from the repository (the source of truth) so rapid taps never act on a stale copy.
 */
class ToggleCustomAlarmDayUseCase @Inject constructor(
    private val customAlarmRepository: CustomAlarmRepository,
    private val rescheduleCustomAlarmsUseCase: RescheduleCustomAlarmsUseCase
) {
    suspend operator fun invoke(id: Long, calendarDay: Int): DayToggleResult {
        val alarm = customAlarmRepository.getAlarmById(id) ?: return DayToggleResult.UNCHANGED
        val updated = alarm.withDayToggled(calendarDay)
        if (updated == alarm) return DayToggleResult.UNCHANGED

        val hasConflict = customAlarmRepository.getAlarms()
            .any { it.id != id && it.conflictsWith(updated) }
        if (hasConflict) return DayToggleResult.CONFLICT

        customAlarmRepository.updateAlarm(updated)
        rescheduleCustomAlarmsUseCase()
        return DayToggleResult.APPLIED
    }
}