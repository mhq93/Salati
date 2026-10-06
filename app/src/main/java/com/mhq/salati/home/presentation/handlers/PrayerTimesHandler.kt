package com.mhq.salati.home.presentation.handlers

import com.mhq.salati.home.domain.model.PrayerTimesLoad
import com.mhq.salati.home.domain.model.PrayerTimesTick
import com.mhq.salati.home.domain.model.PrayerWindow
import com.mhq.salati.home.domain.usecases.LoadPrayerTimesUseCase
import com.mhq.salati.home.domain.usecases.ObservePrayerCountdownUseCase
import com.mhq.salati.home.domain.usecases.RefreshPrayerStatusUseCase
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.shared.domain.Coordinates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import javax.inject.Inject

/** Loading a day's prayer times and keeping their countdown going. */

class PrayerTimesHandler @Inject constructor(
    private val loadPrayerTimesUseCase: LoadPrayerTimesUseCase,
    private val observePrayerCountdownUseCase: ObservePrayerCountdownUseCase,
    private val refreshPrayerStatusUseCase: RefreshPrayerStatusUseCase,
    private val dayNavigator: DayNavigator
) {
    fun load(date: LocalDate, coordinates: Coordinates): Flow<PrayerTimesLoad> =
        loadPrayerTimesUseCase(date, coordinates)

    /**
     * Counts down [window]; when it ends, either moves on to the next day or starts the next window.
     * Keeps going until the day rolls over, so collect it for as long as these times are on screen.
     */
    fun keepCountingDown(
        timings: PrayerTimings,
        date: LocalDate,
        coordinates: Coordinates,
        window: PrayerWindow
    ): Flow<PrayerTimesTick> = flow {
        var currentWindow = window
        while (true) {
            observePrayerCountdownUseCase(currentWindow).collect { emit(PrayerTimesTick.Countdown(it)) }

            val nextDate = dayNavigator.rolloverDate(date, currentWindow)
            if (nextDate != null) {
                emit(PrayerTimesTick.DayRolledOver(nextDate))
                return@flow
            }

            val status = refreshPrayerStatusUseCase(timings, date, coordinates)
            // A window that doesn't end later than the one that just ended would loop forever.
            if (!status.window.end.isAfter(currentWindow.end)) return@flow
            emit(PrayerTimesTick.StatusRefreshed(status))
            currentWindow = status.window
        }
    }
}