package com.mhq.salati.home.presentation.handlers

import com.mhq.salati.home.domain.service.PrayerAlarmCoordinator
import com.mhq.salati.home.domain.model.AlarmPermissions
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import java.time.LocalDate
import javax.inject.Inject

/** Prayer alarms for the Home screen: when to schedule them and which permissions they need. */

class HomeAlarmsHandler @Inject constructor(
    private val prayerAlarmCoordinator: PrayerAlarmCoordinator
) {
    private var firstLoadChecked = false

    /** The alarm permissions to ask the user about after the very first load; null on every later load. */
    suspend fun checkPermissionsOnFirstLoad(): AlarmPermissions? {
        if (firstLoadChecked) return null
        firstLoadChecked = true
        return prayerAlarmCoordinator.checkPermissions()
    }

    /** Checks the permissions and, when exact alarms are allowed, schedules today's alarms again. */
    suspend fun recheck(timings: PrayerTimings?, browsedDate: LocalDate): AlarmPermissions {
        val permissions = prayerAlarmCoordinator.checkPermissions()
        if (permissions.hasExactAlarm) {
            prayerAlarmCoordinator.scheduleIfToday(timings, browsedDate)
        }
        return permissions
    }

    suspend fun schedule(timings: PrayerTimings?, browsedDate: LocalDate) {
        prayerAlarmCoordinator.scheduleIfToday(timings, browsedDate)
    }
}