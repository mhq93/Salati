package com.mhq.salati.home.domain.usecases

import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.domain.MINUTES_PER_DAY
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.domain.toMinuteOfDay
import javax.inject.Inject

class CalculateCurrentPrayerNameUseCase @Inject constructor(
    private val clock: Clock
) {
    operator fun invoke(timings: PrayerTimings): PrayerName? {
        // Anything earlier than Fajr belongs to the tail of the previous day's cycle.
        val fajrMinutes = timings.fajr.toMinuteOfDay()
        fun normalize(minutes: Int) = if (minutes < fajrMinutes) minutes + MINUTES_PER_DAY else minutes
        val nowMinutes = normalize(clock.now().toLocalTime().toMinuteOfDay())

        return PrayerName.majorEntries
            .map { name -> name to normalize(timings[name].toMinuteOfDay()) }
            .filter { (_, minutes) -> minutes <= nowMinutes }
            .maxByOrNull { (_, minutes) -> minutes }
            ?.first
    }
}