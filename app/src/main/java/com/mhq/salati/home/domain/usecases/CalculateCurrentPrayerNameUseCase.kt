package com.mhq.salati.home.domain.usecases

import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.domain.usecases.ParseTimeToMinutesUseCase
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

class CalculateCurrentPrayerNameUseCase @Inject constructor(
    private val clock: Clock,
    private val parseTimeToMinutesUseCase: ParseTimeToMinutesUseCase
) {
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.US)

    operator fun invoke(timings: PrayerTimings): PrayerName? {
        val prayers = listOf(
            PrayerName.FAJR to timings.fajr,
            PrayerName.DHUHR to timings.dhuhr,
            PrayerName.ASR to timings.asr,
            PrayerName.MAGHRIB to timings.maghrib,
            PrayerName.ISHA to timings.isha
        )

        val fajrMinutes = parseTimeToMinutesUseCase(timings.fajr)
        fun normalize(minutes: Int) = if (minutes < fajrMinutes) minutes + 24 * 60 else minutes

        val rawNowMinutes = parseTimeToMinutesUseCase(timeFormatter.format(clock.now()))
        val nowMinutes = normalize(rawNowMinutes)

        return prayers
            .map { it.first to normalize(parseTimeToMinutesUseCase(it.second)) }
            .filter { it.second <= nowMinutes }
            .maxByOrNull { it.second }
            ?.first
    }
}