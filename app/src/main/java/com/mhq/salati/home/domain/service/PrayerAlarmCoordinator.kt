package com.mhq.salati.home.domain.service

import com.mhq.salati.adhan.domain.usecases.RescheduleAllAlarmsUseCase
import com.mhq.salati.home.domain.model.AlarmPermissions
import com.mhq.salati.permissions.domain.repo.PermissionChecker
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.shared.domain.Clock
import java.time.LocalDate
import javax.inject.Inject

class PrayerAlarmCoordinator @Inject constructor(
    private val clock: Clock,
    private val permissionChecker: PermissionChecker,
    private val rescheduleAllAlarmsUseCase: RescheduleAllAlarmsUseCase
) {
    suspend fun checkPermissions(): AlarmPermissions = AlarmPermissions(
        hasExactAlarm = permissionChecker.canScheduleExactAlarms(),
        hasNotification = permissionChecker.hasNotificationPermission()
    )

    suspend fun scheduleIfToday(timings: PrayerTimings?, browsedDate: LocalDate) {
        timings ?: return
        if (browsedDate != clock.today()) return
        rescheduleAllAlarmsUseCase()
    }
}