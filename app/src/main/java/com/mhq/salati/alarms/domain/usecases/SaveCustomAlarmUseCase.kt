package com.mhq.salati.alarms.domain.usecases

import com.mhq.salati.alarms.domain.model.CustomAlarm
import com.mhq.salati.alarms.domain.model.SaveAlarmResult
import com.mhq.salati.alarms.domain.model.conflictsWith
import com.mhq.salati.alarms.domain.repo.CustomAlarmRepository
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

/**
 * Saves [alarm]: a new one when its id is 0, otherwise an update.
 * Refuses an alarm that would ring at the same time as another one,
 * and reschedules afterwards so the change takes effect immediately.
 *
 * Reads the other alarms from the repository (the source of truth), like [ToggleCustomAlarmDayUseCase].
 */
class SaveCustomAlarmUseCase @Inject constructor(
    private val customAlarmRepository: CustomAlarmRepository,
    private val createCustomAlarmUseCase: CreateCustomAlarmUseCase,
    private val updateCustomAlarmUseCase: UpdateCustomAlarmUseCase,
    private val rescheduleCustomAlarmsUseCase: RescheduleCustomAlarmsUseCase
) {
    suspend operator fun invoke(alarm: CustomAlarm): SaveAlarmResult {
        val hasConflict = customAlarmRepository.getAlarms()
            .any { it.id != alarm.id && it.conflictsWith(alarm) }
        if (hasConflict) return SaveAlarmResult.CONFLICT

        if (alarm.id == 0L) createCustomAlarmUseCase(alarm) else updateCustomAlarmUseCase(alarm)
        rescheduleQuietly()
        return SaveAlarmResult.SAVED
    }

    // Best effort: if today's timings aren't cached yet (e.g. the app was just opened),
    // Home's own load schedules everything anyway. A failure here must not
    // turn a successful save into an error.
    private suspend fun rescheduleQuietly() {
        try {
            rescheduleCustomAlarmsUseCase()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            // swallowed intentionally
        }
    }
}