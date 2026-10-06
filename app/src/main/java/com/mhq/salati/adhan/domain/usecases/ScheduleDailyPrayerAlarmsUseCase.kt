package com.mhq.salati.adhan.domain.usecases

import com.mhq.salati.adhan.domain.model.PrayerAlarm
import com.mhq.salati.adhan.domain.repo.AlarmScheduler
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.shared.domain.PrayerName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

class ScheduleDailyPrayerAlarmsUseCase @Inject constructor(
    private val alarmScheduler: AlarmScheduler
) {
    private val dateKeyFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.US)

    suspend operator fun invoke(timings: PrayerTimings, date: String, mutedPrayers: Set<PrayerName>) {
        withContext(Dispatchers.IO) {
            val zoneId = ZoneId.systemDefault()
            val day = LocalDate.parse(date, dateKeyFormatter)

            PrayerName.entries.forEach { name ->
                val triggerMillis = day
                    .atTime(timings[name])
                    .atZone(zoneId)
                    .toInstant()
                    .toEpochMilli()

                if (triggerMillis > System.currentTimeMillis()) {
                    alarmScheduler.schedule(
                        PrayerAlarm(
                            prayerName = name,
                            triggerAtMillis = triggerMillis,
                            isMuted = name in mutedPrayers
                        )
                    )
                }
            }
        }
    }
}