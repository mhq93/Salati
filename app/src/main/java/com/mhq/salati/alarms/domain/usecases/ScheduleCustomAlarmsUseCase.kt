package com.mhq.salati.alarms.domain.usecases

import com.mhq.salati.alarms.domain.model.CustomAlarm
import com.mhq.salati.alarms.domain.model.OffsetDirection
import com.mhq.salati.alarms.domain.repo.CustomAlarmRepository
import com.mhq.salati.alarms.domain.repo.CustomAlarmScheduler
import com.mhq.salati.location.domain.usecases.GetSavedLocationUseCase
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.prayertimes.domain.usecases.GetCachedPrayerTimesUseCase
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.domain.toDateKey
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

class ScheduleCustomAlarmsUseCase @Inject constructor(
    private val clock: Clock,
    private val customAlarmRepository: CustomAlarmRepository,
    private val customAlarmScheduler: CustomAlarmScheduler,
    private val getSavedLocationUseCase: GetSavedLocationUseCase,
    private val getCachedPrayerTimesUseCase: GetCachedPrayerTimesUseCase
) {
    suspend operator fun invoke(timings: PrayerTimings) {
        val now = clock.now()
        val zone = clock.zone()
        val location = getSavedLocationUseCase().first()

        customAlarmRepository.getAlarms().forEach { alarm ->
            if (!alarm.isEnabled || !alarm.runsOn(now.toLocalDate())) {
                customAlarmScheduler.cancel(alarm.id)
                return@forEach
            }

            var trigger = alarm.triggerOn(now.toLocalDate(), timings[alarm.prayerName])

            if (!trigger.isAfter(now)) {
                when {
                    alarm.everyDay -> trigger = trigger.plusDays(1)

                    alarm.activeDays.isNotEmpty() -> {
                        var daysAhead = 0
                        do {
                            trigger = trigger.plusDays(1)
                            daysAhead++
                        } while (
                            trigger.toLocalDate().calendarDayOfWeek() !in alarm.activeDays &&
                            daysAhead < 7
                        )
                    }

                    else -> {
                        // A one-off alarm whose time has passed.
                        customAlarmScheduler.cancel(alarm.id)
                        return@forEach
                    }
                }

                // Re-anchor to that future day's ACTUAL cached prayer time instead of reusing
                // today's time-of-day. Falls back to the drifted estimate above only if that day
                // isn't cached yet (e.g. across a year boundary); it self-corrects on the next run.
                if (location != null) {
                    val futureDate = trigger.toLocalDate()
                    val futurePrayerTime = getCachedPrayerTimesUseCase(
                        futureDate.toDateKey(), location.coordinates
                    )?.timings?.get(alarm.prayerName)

                    if (futurePrayerTime != null) {
                        trigger = alarm.triggerOn(futureDate, futurePrayerTime)
                    }
                }
            }

            customAlarmScheduler.schedule(alarm, trigger.atZone(zone).toInstant().toEpochMilli())
        }
    }

    private fun CustomAlarm.runsOn(date: LocalDate): Boolean =
        everyDay || date.calendarDayOfWeek() in activeDays

    private fun CustomAlarm.triggerOn(date: LocalDate, prayerTime: LocalTime): LocalDateTime {
        val offset = if (offsetDirection == OffsetDirection.BEFORE) -offsetMinutes else offsetMinutes
        return date.atTime(prayerTime.hour, prayerTime.minute).plusMinutes(offset.toLong())
    }

    // Stored days use java.util.Calendar numbering: Sunday = 1 ... Saturday = 7.
    private fun LocalDate.calendarDayOfWeek(): Int = dayOfWeek.value % 7 + 1
}