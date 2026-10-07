package com.mhq.salati.adhan.domain.usecases

import com.mhq.salati.adhan.domain.model.PrayerAlarm
import com.mhq.salati.adhan.domain.repo.AlarmScheduler
import com.mhq.salati.adhan.domain.repo.MutedPrayersRepository
import com.mhq.salati.alarms.domain.usecases.RescheduleCustomAlarmsUseCase
import com.mhq.salati.location.domain.usecases.GetSavedLocationUseCase
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.prayertimes.domain.usecases.GetCachedPrayerTimesUseCase
import com.mhq.salati.settings.domain.usecases.ObserveSettingsUseCase
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.domain.Coordinates
import com.mhq.salati.shared.domain.PrayerName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

/**
 * Arms the next occurrence of every prayer alarm and every custom alarm.
 * Single entry point for: Home load, alarm fired, boot / time change / app update,
 * and the notifications toggle.
 */
class RescheduleAllAlarmsUseCase @Inject constructor(
    private val clock: Clock,
    private val alarmScheduler: AlarmScheduler,
    private val mutedPrayersRepository: MutedPrayersRepository,
    private val getSavedLocationUseCase: GetSavedLocationUseCase,
    private val getCachedPrayerTimesUseCase: GetCachedPrayerTimesUseCase,
    private val observeSettingsUseCase: ObserveSettingsUseCase,
    private val rescheduleCustomAlarmsUseCase: RescheduleCustomAlarmsUseCase
) {
    private val dateKeyFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.US)

    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        if (observeSettingsUseCase().first().notificationsEnabled) {
            schedulePrayerAlarms()
        }
        rescheduleCustomAlarmsUseCase()
    }

    private suspend fun schedulePrayerAlarms() {
        val coordinates = getSavedLocationUseCase().first()?.coordinates ?: return
        val now = clock.instant()
        val zone = clock.zone()
        val today = clock.today()
        val tomorrow = today.plusDays(1)

        val todayTimings = cachedTimings(today, coordinates)
        // Year boundary: tomorrow may not be cached yet. Reuse today's clock times
        // (off by a minute or two at most); the next Home load corrects it.
        val tomorrowTimings = cachedTimings(tomorrow, coordinates) ?: todayTimings

        val mutedToday = mutedPrayersRepository.getMutedPrayers(today.format(dateKeyFormatter))
        val mutedTomorrow = mutedPrayersRepository.getMutedPrayers(tomorrow.format(dateKeyFormatter))

        PrayerName.entries.forEach { name ->
            val todayTrigger = todayTimings
                ?.let { today.atTime(it[name]).atZone(zone).toInstant() }

            val alarm = when {
                todayTrigger != null && todayTrigger.isAfter(now) ->
                    PrayerAlarm(name, todayTrigger.toEpochMilli(), name in mutedToday)

                tomorrowTimings != null -> {
                    val trigger = tomorrow.atTime(tomorrowTimings[name]).atZone(zone).toInstant()
                    PrayerAlarm(name, trigger.toEpochMilli(), name in mutedTomorrow)
                }

                else -> null
            }
            alarm?.let { alarmScheduler.schedule(it) }
        }
    }

    private suspend fun cachedTimings(date: LocalDate, coordinates: Coordinates): PrayerTimings? =
        getCachedPrayerTimesUseCase(date.format(dateKeyFormatter), coordinates)?.timings
}