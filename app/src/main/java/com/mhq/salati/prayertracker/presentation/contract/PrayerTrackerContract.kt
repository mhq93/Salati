package com.mhq.salati.prayertracker.presentation.contract

import com.mhq.salati.prayertracker.presentation.model.DayStatusUi
import com.mhq.salati.prayertracker.presentation.model.PrayerStatusUi
import com.mhq.salati.shared.presentation.model.PrayerNameUi
import com.mhq.salati.shared.presentation.model.MessageUi
import java.time.LocalDate
import java.time.YearMonth

object PrayerTrackerContract {
    data class State(
        val today: LocalDate,
        val selectedMonth: YearMonth,
        val selectedDate: LocalDate,
        val monthDayStatus: Map<LocalDate, DayStatusUi> = emptyMap(),
        val selectedDateRecords: Map<PrayerNameUi, PrayerStatusUi> = emptyMap(),
        val dialogPrayer: PrayerNameUi? = null,
        val currentStreak: Int = 0,
        val isLoading: Boolean = true,
        val duePrayers: Set<PrayerNameUi> = PrayerNameUi.majorEntries.toSet()
    )

    sealed interface Intent {
        data class DateSelected(val date: LocalDate) : Intent
        data class MonthChanged(val delta: Int) : Intent
        data class PrayerTileTapped(val prayer: PrayerNameUi) : Intent
        data object ConfirmPrayed : Intent
        data object ConfirmMissed : Intent
        data object DismissDialog : Intent
        data object ScreenResumed : Intent
    }

    sealed interface Effect {
        data class ShowError(val message: MessageUi) : Effect
    }
}