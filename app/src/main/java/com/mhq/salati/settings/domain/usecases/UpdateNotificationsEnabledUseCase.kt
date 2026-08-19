package com.mhq.salati.settings.domain.usecases

import com.mhq.salati.adhan.domain.repo.AlarmScheduler
import com.mhq.salati.adhan.domain.repo.MutedPrayersRepository
import com.mhq.salati.adhan.domain.usecases.ScheduleDailyPrayerAlarmsUseCase
import com.mhq.salati.location.domain.usecases.GetSavedLocationUseCase
import com.mhq.salati.prayertimes.domain.repo.PrayerTimesRepository
import com.mhq.salati.settings.domain.repo.SettingsRepository
import kotlinx.coroutines.flow.first
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class UpdateNotificationsEnabledUseCase @Inject constructor(
    private val alarmScheduler: AlarmScheduler,
    private val settingsRepository: SettingsRepository,
    private val prayerTimesRepository: PrayerTimesRepository,
    private val mutedPrayersRepository: MutedPrayersRepository,
    private val getSavedLocationUseCase: GetSavedLocationUseCase,
    private val scheduleDailyPrayerAlarmsUseCase: ScheduleDailyPrayerAlarmsUseCase
) {
    suspend operator fun invoke(enabled: Boolean) {
        settingsRepository.setNotificationsEnabled(enabled)
        if (!enabled) {
            alarmScheduler.cancelAll()
            return
        }
        rearmTodayAlarms()
    }

    private suspend fun rearmTodayAlarms() {
        val settings = settingsRepository.observeSettings().first()
        val location = getSavedLocationUseCase().first() ?: return
        val today = SimpleDateFormat("dd-MM-yyyy", Locale.US).format(Date())

        val cached = prayerTimesRepository.getCachedTimings(
            today,
            location.latitude,
            location.longitude,
            method = settings.calculationMethod.apiMethodId,
            madhab = settings.madhab
        ) ?: return

        val mutedPrayers = mutedPrayersRepository.getMutedPrayers(today)
        scheduleDailyPrayerAlarmsUseCase(cached.timings, today, mutedPrayers)
    }
}