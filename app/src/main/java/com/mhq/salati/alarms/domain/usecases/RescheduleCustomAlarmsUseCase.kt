package com.mhq.salati.alarms.domain.usecases

import com.mhq.salati.location.domain.usecases.GetSavedLocationUseCase
import com.mhq.salati.prayertimes.domain.usecases.GetCachedPrayerTimesUseCase
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.domain.toDateKey
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Re-runs custom-alarm scheduling against today's cached prayer timings.
 * Used by callers outside Home's load flow (the Alarms screen itself) so a
 * newly created/edited/re-enabled alarm takes effect immediately instead of
 * waiting for Home to next reload prayer times.
 */
class RescheduleCustomAlarmsUseCase @Inject constructor(
    private val clock: Clock,
    private val getSavedLocationUseCase: GetSavedLocationUseCase,
    private val getCachedPrayerTimesUseCase: GetCachedPrayerTimesUseCase,
    private val scheduleCustomAlarmsUseCase: ScheduleCustomAlarmsUseCase
) {
    suspend operator fun invoke() {
        val location = getSavedLocationUseCase().first() ?: return
        val todayKey = clock.today().toDateKey()
        val cached = getCachedPrayerTimesUseCase(todayKey, location.coordinates)
            ?: return
        scheduleCustomAlarmsUseCase(cached.timings)
    }
}