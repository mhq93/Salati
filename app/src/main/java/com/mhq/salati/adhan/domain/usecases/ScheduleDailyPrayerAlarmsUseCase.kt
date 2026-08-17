package com.mhq.salati.adhan.domain.usecases

import com.mhq.salati.adhan.domain.model.PrayerAlarm
import com.mhq.salati.adhan.domain.repo.AlarmScheduler
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.shared.domain.PrayerName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ScheduleDailyPrayerAlarmsUseCase @Inject constructor(
    private val alarmScheduler: AlarmScheduler
) {
    suspend operator fun invoke(timings: PrayerTimings, date: String, mutedPrayers: Set<String>) {
        withContext(Dispatchers.IO) {
            val zoneId = ZoneId.systemDefault()
            val prayerMap = mapOf(
                PrayerName.FAJR to timings.fajr,
                PrayerName.DHUHR to timings.dhuhr,
                PrayerName.ASR to timings.asr,
                PrayerName.MAGHRIB to timings.maghrib,
                PrayerName.ISHA to timings.isha,
                PrayerName.IMSAK to timings.imsak,
                PrayerName.SHOROUQ to timings.sunrise,
                PrayerName.FIRST_THIRD to timings.firstThird,
                PrayerName.MIDNIGHT to timings.midnight,
                PrayerName.LAST_THIRD to timings.lastThird
            )

            prayerMap.forEach { (name, time) ->
                val triggerMillis = parseToEpochMillis(date, time, zoneId)
                if (triggerMillis > System.currentTimeMillis()) {
                    alarmScheduler.schedule(
                        PrayerAlarm(
                            prayerName = name.storageKey,
                            triggerAtMillis = triggerMillis,
                            isMinorTiming = name.isMinorTiming,
                            isMuted = name.storageKey in mutedPrayers
                        )
                    )
                }
            }
        }
    }

    private fun parseToEpochMillis(date: String, time: String, zoneId: ZoneId): Long {
        val dateTimeStr = "$date $time"
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
        return LocalDateTime.parse(dateTimeStr, formatter)
            .atZone(zoneId)
            .toInstant()
            .toEpochMilli()
    }
}