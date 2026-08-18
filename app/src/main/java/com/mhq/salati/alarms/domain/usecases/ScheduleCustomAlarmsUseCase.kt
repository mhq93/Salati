package com.mhq.salati.alarms.domain.usecases

import com.mhq.salati.alarms.domain.scheduler.CustomAlarmScheduler
import com.mhq.salati.alarms.domain.model.OffsetDirection
import com.mhq.salati.alarms.domain.repo.CustomAlarmRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class ScheduleCustomAlarmsUseCase @Inject constructor(
    private val customAlarmRepository: CustomAlarmRepository,
    private val customAlarmScheduler: CustomAlarmScheduler
) {
    suspend operator fun invoke(timings: Map<String, String>) {
        val now = Calendar.getInstance()
        val todayDow = now.get(Calendar.DAY_OF_WEEK)
        val format = SimpleDateFormat("HH:mm", Locale.US)

        customAlarmRepository.getAlarms().forEach { alarm ->
            if (!alarm.isEnabled) {
                customAlarmScheduler.cancel(alarm.id)
                return@forEach
            }
            val runsToday = alarm.everyDay || alarm.activeDays.contains(todayDow)
            if (!runsToday) {
                customAlarmScheduler.cancel(alarm.id)
                return@forEach
            }

            val timeString = timings[alarm.prayerName] ?: return@forEach
            val parsed = runCatching { format.parse(timeString) }.getOrNull() ?: return@forEach

            val triggerCal = Calendar.getInstance().apply {
                time = parsed
                set(Calendar.YEAR, now.get(Calendar.YEAR))
                set(Calendar.MONTH, now.get(Calendar.MONTH))
                set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH))
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
                val delta = if (alarm.offsetDirection == OffsetDirection.BEFORE) -alarm.offsetMinutes else alarm.offsetMinutes
                add(Calendar.MINUTE, delta)
            }

            if (triggerCal.timeInMillis <= now.timeInMillis) {
                customAlarmScheduler.cancel(alarm.id)
                return@forEach
            }
            customAlarmScheduler.schedule(alarm, triggerCal.timeInMillis)
        }
    }
}