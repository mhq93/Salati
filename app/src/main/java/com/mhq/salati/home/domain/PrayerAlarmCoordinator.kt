package com.mhq.salati.home.domain

import com.mhq.salati.adhan.domain.repo.MutedPrayersRepository
import com.mhq.salati.adhan.domain.usecases.ScheduleDailyPrayerAlarmsUseCase
import com.mhq.salati.alarms.domain.usecases.ScheduleCustomAlarmsUseCase
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.domain.PrayerName
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

class PrayerAlarmCoordinator @Inject constructor(
    private val clock: Clock,
    private val mutedPrayersRepository: MutedPrayersRepository,
    private val scheduleDailyPrayerAlarmsUseCase: ScheduleDailyPrayerAlarmsUseCase,
    private val scheduleCustomAlarmsUseCase: ScheduleCustomAlarmsUseCase
) {
    private val dateKeyFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.US)

    suspend fun scheduleIfToday(timings: PrayerTimings?, browsedDate: LocalDate) {
        timings ?: return
        val browsedDateKey = browsedDate.format(dateKeyFormatter)
        val todayKey = clock.today().format(dateKeyFormatter)
        if (browsedDateKey != todayKey) return

        val todaysMutedPrayers = mutedPrayersRepository.getMutedPrayers(todayKey)
        scheduleDailyPrayerAlarmsUseCase(timings, todayKey, todaysMutedPrayers)
        scheduleCustomAlarmsUseCase(timings.toNameMap())
    }

    private fun PrayerTimings.toNameMap(): Map<String, String> = mapOf(
        PrayerName.IMSAK.storageKey to imsak,
        PrayerName.FAJR.storageKey to fajr,
        PrayerName.SHOROUQ.storageKey to sunrise,
        PrayerName.DHUHR.storageKey to dhuhr,
        PrayerName.ASR.storageKey to asr,
        PrayerName.MAGHRIB.storageKey to maghrib,
        PrayerName.ISHA.storageKey to isha,
        PrayerName.FIRST_THIRD.storageKey to firstThird,
        PrayerName.MIDNIGHT.storageKey to midnight,
        PrayerName.LAST_THIRD.storageKey to lastThird
    )
}