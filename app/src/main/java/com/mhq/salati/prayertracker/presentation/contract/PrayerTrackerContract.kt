package com.mhq.salati.prayertracker.presentation.contract

import com.mhq.salati.prayertracker.domain.model.DayStatus
import com.mhq.salati.prayertracker.domain.model.PrayerStatus
import com.mhq.salati.prayertracker.domain.model.PrayerType
import java.time.LocalDate
import java.time.YearMonth

object PrayerTrackerContract {
    data class State(
        val selectedMonth: YearMonth = YearMonth.now(),
        val selectedDate: LocalDate = LocalDate.now(),
        val monthDayStatus: Map<LocalDate, DayStatus> = emptyMap(),
        val selectedDateRecords: Map<PrayerType, PrayerStatus> = emptyMap(),
        val dialogPrayer: PrayerType? = null,
        val currentStreak: Int = 0,
        val isLoading: Boolean = true
    )

    sealed interface Intent {
        data class DateSelected(val date: LocalDate) : Intent
        data class MonthChanged(val delta: Int) : Intent
        data class PrayerTileTapped(val prayer: PrayerType) : Intent
        data object ConfirmPrayed : Intent
        data object ConfirmMissed : Intent
        data object DismissDialog : Intent
    }

    sealed interface Effect {
        data class ShowError(val message: String) : Effect
    }
}