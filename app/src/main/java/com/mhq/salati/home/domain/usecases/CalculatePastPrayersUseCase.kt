package com.mhq.salati.home.domain.usecases

import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.domain.MINUTES_PER_DAY
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.domain.toMinuteOfDay
import javax.inject.Inject

class CalculatePastPrayersUseCase @Inject constructor(
    private val clock: Clock
) {
    operator fun invoke(timings: PrayerTimings): Set<PrayerName> {
        // Imsak is the first timing of the day; anything earlier than it is treated as after midnight.
        val imsakMinutes = timings.imsak.toMinuteOfDay()
        fun normalize(minutes: Int) = if (minutes < imsakMinutes) minutes + MINUTES_PER_DAY else minutes

        val nowMinutes = normalize(clock.now().toLocalTime().toMinuteOfDay())

        return PrayerName.entries
            .filter { name -> normalize(timings[name].toMinuteOfDay()) <= nowMinutes }
            .toSet()
    }
}