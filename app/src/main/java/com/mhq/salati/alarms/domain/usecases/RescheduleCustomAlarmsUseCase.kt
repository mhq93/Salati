package com.mhq.salati.alarms.domain.usecases

import com.mhq.salati.alarms.domain.repo.CustomAlarmRepository
import com.mhq.salati.alarms.domain.repo.CustomAlarmScheduler
import com.mhq.salati.location.domain.usecases.GetSavedLocationUseCase
import com.mhq.salati.prayertimes.domain.usecases.GetCachedPrayerTimesUseCase
import com.mhq.salati.settings.domain.usecases.ObserveSettingsUseCase
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.domain.toDateKey
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Re-runs custom-alarm scheduling against today's cached prayer timings.
 * Used by callers outside Home's load flow (the Alarms screen itself)
 * so a newly created/edited/re-enabled alarm takes effect immediately
 * instead of waiting for Home to next reload prayer times.
 *
 * When notifications are turned off in Settings,
 * every custom alarm is canceled instead. The alarms stay stored and enabled,
 * and are armed again the next time this runs with notifications on.
 */
class RescheduleCustomAlarmsUseCase @Inject constructor(
    private val clock: Clock,
    private val observeSettingsUseCase: ObserveSettingsUseCase,
    private val customAlarmRepository: CustomAlarmRepository,
    private val customAlarmScheduler: CustomAlarmScheduler,
    private val getSavedLocationUseCase: GetSavedLocationUseCase,
    private val getCachedPrayerTimesUseCase: GetCachedPrayerTimesUseCase,
    private val scheduleCustomAlarmsUseCase: ScheduleCustomAlarmsUseCase
) {
    suspend operator fun invoke() {
        if (!observeSettingsUseCase().first().notificationsEnabled) {
            customAlarmRepository.getAlarms().forEach { customAlarmScheduler.cancel(it.id) }
            return
        }
        val location = getSavedLocationUseCase().first() ?: return
        val todayKey = clock.today().toDateKey()
        val cached = getCachedPrayerTimesUseCase(todayKey, location.coordinates)
            ?: return
        scheduleCustomAlarmsUseCase(cached.timings)
    }
}