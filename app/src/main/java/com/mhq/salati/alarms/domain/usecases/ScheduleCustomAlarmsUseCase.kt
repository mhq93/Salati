package com.mhq.salati.alarms.domain.usecases

import com.mhq.salati.alarms.domain.model.OffsetDirection
import com.mhq.salati.alarms.domain.repo.CustomAlarmRepository
import com.mhq.salati.alarms.domain.repo.CustomAlarmScheduler
import com.mhq.salati.location.domain.usecases.GetSavedLocationUseCase
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.prayertimes.domain.usecases.GetCachedPrayerTimesUseCase
import kotlinx.coroutines.flow.first
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class ScheduleCustomAlarmsUseCase @Inject constructor(
    private val customAlarmRepository: CustomAlarmRepository,
    private val customAlarmScheduler: CustomAlarmScheduler,
    private val getSavedLocationUseCase: GetSavedLocationUseCase,
    private val getCachedPrayerTimesUseCase: GetCachedPrayerTimesUseCase
) {
    suspend operator fun invoke(timings: PrayerTimings) {
        val now = Calendar.getInstance()
        val todayDow = now.get(Calendar.DAY_OF_WEEK)
        val dateKeyFormat = SimpleDateFormat("dd-MM-yyyy", Locale.US)
        val location = getSavedLocationUseCase().first()

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

            val prayerTime = timings[alarm.prayerName]

            val triggerCal = Calendar.getInstance().apply {
                set(Calendar.YEAR, now.get(Calendar.YEAR))
                set(Calendar.MONTH, now.get(Calendar.MONTH))
                set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH))
                set(Calendar.HOUR_OF_DAY, prayerTime.hour)
                set(Calendar.MINUTE, prayerTime.minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)

                val delta =
                    if (alarm.offsetDirection == OffsetDirection.BEFORE)
                        -alarm.offsetMinutes
                    else
                        alarm.offsetMinutes

                add(Calendar.MINUTE, delta)
            }

            if (triggerCal.timeInMillis <= now.timeInMillis) {
                val rolledForward = when {
                    alarm.everyDay -> {
                        triggerCal.add(Calendar.DAY_OF_YEAR, 1)
                        true
                    }

                    alarm.activeDays.isNotEmpty() -> {
                        var daysAhead = 0
                        do {
                            triggerCal.add(Calendar.DAY_OF_YEAR, 1)
                            daysAhead++
                        } while (
                            !alarm.activeDays.contains(triggerCal.get(Calendar.DAY_OF_WEEK)) &&
                            daysAhead < 7
                        )
                        true
                    }

                    else -> {
                        customAlarmScheduler.cancel(alarm.id)
                        false
                    }
                }
                if (!rolledForward) return@forEach

                // Re-anchor to that future day's ACTUAL cached prayer time
                // instead of reusing today's snapshotted time-of-day. Falls back
                // to the drifted estimate (already computed above) only if that
                // day genuinely isn't cached yet — e.g. crossing a year boundary
                // before the next annual calendar fetch — and self-corrects the
                // next time this use case reruns closer to that day.
                if (location != null) {
                    val futureDateKey = dateKeyFormat.format(triggerCal.time)
                    val futurePrayerTime = getCachedPrayerTimesUseCase(
                        futureDateKey, location.coordinates
                    )?.timings?.get(alarm.prayerName)

                    if (futurePrayerTime != null) {
                        val futureCal = Calendar.getInstance().apply {
                            set(Calendar.YEAR, triggerCal.get(Calendar.YEAR))
                            set(Calendar.MONTH, triggerCal.get(Calendar.MONTH))
                            set(Calendar.DAY_OF_MONTH, triggerCal.get(Calendar.DAY_OF_MONTH))
                            set(Calendar.HOUR_OF_DAY, futurePrayerTime.hour)
                            set(Calendar.MINUTE, futurePrayerTime.minute)
                            set(Calendar.SECOND, 0)
                            set(Calendar.MILLISECOND, 0)

                            val delta =
                                if (alarm.offsetDirection == OffsetDirection.BEFORE)
                                    -alarm.offsetMinutes
                                else
                                    alarm.offsetMinutes

                            add(Calendar.MINUTE, delta)
                        }
                        triggerCal.timeInMillis = futureCal.timeInMillis
                    }
                }
            }
            customAlarmScheduler.schedule(
                alarm,
                triggerCal.timeInMillis
            )
        }
    }
}