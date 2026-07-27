package com.mhq.salati.adhan.domain.usecases

import com.mhq.salati.adhan.domain.model.PrayerAlarm
import com.mhq.salati.adhan.domain.repo.AlarmScheduler
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
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
                "Fajr" to timings.fajr,
                "Dhuhr" to timings.dhuhr,
                "Asr" to timings.asr,
                "Maghrib" to timings.maghrib,
                "Isha" to timings.isha,
                "Imsak" to timings.imsak,
                "Shorouq" to timings.sunrise,
                "First Third" to timings.firstThird,
                "Midnight" to timings.midnight,
                "Last Third" to timings.lastThird
                )

            prayerMap.forEach { (name, time) ->
                if (name in mutedPrayers) {
                    alarmScheduler.cancel(name)
                    return@forEach
                }

                val triggerMillis = parseToEpochMillis(date, time, zoneId)
                if (triggerMillis > System.currentTimeMillis()) {
                    alarmScheduler.schedule(PrayerAlarm(name, triggerMillis))
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