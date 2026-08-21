package com.mhq.salati.home.domain.usecases

import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.domain.usecases.ParseTimeToMinutesUseCase
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

class CalculatePastPrayersUseCase @Inject constructor(
    private val parseTimeToMinutesUseCase: ParseTimeToMinutesUseCase,
    private val clock: Clock
) {
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.US)

    operator fun invoke(timings: PrayerTimings): Set<PrayerName> {
        val allTimings = listOf(
            PrayerName.IMSAK to timings.imsak,
            PrayerName.FAJR to timings.fajr,
            PrayerName.SHOROUQ to timings.sunrise,
            PrayerName.DHUHR to timings.dhuhr,
            PrayerName.ASR to timings.asr,
            PrayerName.MAGHRIB to timings.maghrib,
            PrayerName.ISHA to timings.isha,
            PrayerName.FIRST_THIRD to timings.firstThird,
            PrayerName.MIDNIGHT to timings.midnight,
            PrayerName.LAST_THIRD to timings.lastThird
        )

        val imsakMinutes = parseTimeToMinutesUseCase(timings.imsak)
        fun normalize(minutes: Int) = if (minutes < imsakMinutes) minutes + 24 * 60 else minutes

        val rawNowMinutes = parseTimeToMinutesUseCase(timeFormatter.format(clock.now()))
        val nowMinutes = normalize(rawNowMinutes)

        return allTimings
            .filter { (_, time) -> normalize(parseTimeToMinutesUseCase(time)) <= nowMinutes }
            .map { (name, _) -> name }
            .toSet()
    }
}