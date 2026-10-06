package com.mhq.salati.home.domain.service

import com.mhq.salati.adhan.domain.repo.MutedPrayersRepository
import com.mhq.salati.adhan.domain.usecases.ScheduleDailyPrayerAlarmsUseCase
import com.mhq.salati.alarms.domain.usecases.ScheduleCustomAlarmsUseCase
import com.mhq.salati.home.domain.model.AlarmPermissions
import com.mhq.salati.permissions.domain.repo.PermissionChecker
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.shared.domain.Clock
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

class PrayerAlarmCoordinator @Inject constructor(
    private val clock: Clock,
    private val permissionChecker: PermissionChecker,
    private val mutedPrayersRepository: MutedPrayersRepository,
    private val scheduleDailyPrayerAlarmsUseCase: ScheduleDailyPrayerAlarmsUseCase,
    private val scheduleCustomAlarmsUseCase: ScheduleCustomAlarmsUseCase
) {
    private val dateKeyFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.US)

    suspend fun checkPermissions(): AlarmPermissions = AlarmPermissions(
        hasExactAlarm = permissionChecker.canScheduleExactAlarms(),
        hasNotification = permissionChecker.hasNotificationPermission()
    )

    suspend fun scheduleIfToday(timings: PrayerTimings?, browsedDate: LocalDate) {
        timings ?: return
        val browsedDateKey = browsedDate.format(dateKeyFormatter)
        val todayKey = clock.today().format(dateKeyFormatter)
        if (browsedDateKey != todayKey) return

        val todaysMutedPrayers = mutedPrayersRepository.getMutedPrayers(todayKey)
        scheduleDailyPrayerAlarmsUseCase(timings, todayKey, todaysMutedPrayers)
        scheduleCustomAlarmsUseCase(timings)
    }
}