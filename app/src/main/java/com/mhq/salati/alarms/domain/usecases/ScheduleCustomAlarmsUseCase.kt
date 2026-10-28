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

/** How far ahead to look for an alarm's next active day (today plus a full week). */
private const val MAX_DAYS_AHEAD = 7

/**
 * Arms every enabled custom alarm for its next occurrence, which may be today or a later active day.
 * Disabled alarms, and alarms with no active day, are cancelled.
 */
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
            val trigger = if (alarm.isEnabled) {
                nextTrigger(alarm, now, timings) { date ->
                    // The actual cached prayer time for a future day; null when that day isn't cached yet.
                    location?.let {
                        getCachedPrayerTimesUseCase(date.toDateKey(), it.coordinates)
                            ?.timings?.get(alarm.prayerName)
                    }
                }
            } else {
                null
            }

            if (trigger == null) {
                customAlarmScheduler.cancel(alarm.id)
            } else {
                customAlarmScheduler.schedule(alarm, trigger.atZone(zone).toInstant().toEpochMilli())
            }
        }
    }

    /**
     * The first moment after [now] at which [alarm] should ring, or null if it never will.
     *
     * Walks forward day by day, skipping days the alarm doesn't run on. Today uses [todayTimings];
     * later days use [futurePrayerTime], falling back to today's prayer time if that day isn't
     * cached yet (it self-corrects on the next run).
     */
    private suspend fun nextTrigger(
        alarm: CustomAlarm,
        now: LocalDateTime,
        todayTimings: PrayerTimings,
        futurePrayerTime: suspend (LocalDate) -> LocalTime?
    ): LocalDateTime? {
        val today = now.toLocalDate()
        val todayPrayerTime = todayTimings[alarm.prayerName]

        for (daysAhead in 0..MAX_DAYS_AHEAD) {
            val date = today.plusDays(daysAhead.toLong())
            if (!alarm.runsOn(date)) continue

            val prayerTime = if (daysAhead == 0) {
                todayPrayerTime
            } else {
                futurePrayerTime(date) ?: todayPrayerTime
            }
            val trigger = alarm.triggerOn(date, prayerTime)
            if (trigger.isAfter(now)) return trigger
        }
        return null
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