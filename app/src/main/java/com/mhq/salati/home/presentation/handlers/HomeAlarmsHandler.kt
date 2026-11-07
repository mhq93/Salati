package com.mhq.salati.home.presentation.handlers

import com.mhq.salati.adhan.domain.model.AlarmPermissions
import com.mhq.salati.adhan.domain.service.PrayerAlarmCoordinator
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import java.time.LocalDate
import javax.inject.Inject

/** Prayer alarms for the Home screen: when to schedule them again. */
class HomeAlarmsHandler @Inject constructor(
    private val prayerAlarmCoordinator: PrayerAlarmCoordinator
) {
    /** Checks the permissions and, when exact alarms are allowed, schedules today's alarms again. */
    suspend fun recheck(timings: PrayerTimings?, browsedDate: LocalDate): AlarmPermissions =
        prayerAlarmCoordinator.recheck(timings, browsedDate)

    suspend fun schedule(timings: PrayerTimings?, browsedDate: LocalDate) {
        prayerAlarmCoordinator.scheduleIfToday(timings, browsedDate)
    }
}