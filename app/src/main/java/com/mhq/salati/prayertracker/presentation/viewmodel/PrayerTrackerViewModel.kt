package com.mhq.salati.prayertracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.prayertracker.domain.model.DayStatus
import com.mhq.salati.prayertracker.domain.model.PrayerStatus
import com.mhq.salati.prayertracker.domain.model.PrayerType
import com.mhq.salati.prayertracker.domain.usecases.GetCurrentStreakUseCase
import com.mhq.salati.prayertracker.domain.usecases.ObservePrayerRecordsForDateUseCase
import com.mhq.salati.prayertracker.domain.usecases.ObservePrayerRecordsForMonthUseCase
import com.mhq.salati.prayertracker.domain.usecases.SetPrayerStatusUseCase
import com.mhq.salati.prayertracker.presentation.contract.PrayerTrackerContract.Effect
import com.mhq.salati.prayertracker.presentation.contract.PrayerTrackerContract.Intent
import com.mhq.salati.prayertracker.presentation.contract.PrayerTrackerContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class PrayerTrackerViewModel @Inject constructor(
    private val observeMonth: ObservePrayerRecordsForMonthUseCase,
    private val observeDate: ObservePrayerRecordsForDateUseCase,
    private val setStatus: SetPrayerStatusUseCase,
    private val getCurrentStreak: GetCurrentStreakUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<Effect>()
    val effect = _effect.asSharedFlow()

    private var monthJob: Job? = null
    private var dateJob: Job? = null

    init {
        loadMonth(_state.value.selectedMonth)
        loadDate(_state.value.selectedDate)
        refreshStreak()
    }

    fun onIntent(intent: Intent) {
        when (intent) {
            is Intent.DateSelected -> {
                _state.update { it.copy(selectedDate = intent.date) }
                loadDate(intent.date)
            }

            is Intent.MonthChanged -> {
                val newMonth = _state.value.selectedMonth.plusMonths(intent.delta.toLong())
                _state.update { it.copy(selectedMonth = newMonth) }
                loadMonth(newMonth)
            }

            is Intent.PrayerTileTapped -> {
                if (_state.value.selectedDate.isAfter(LocalDate.now())) return
                _state.update { it.copy(dialogPrayer = intent.prayer) }
            }

            Intent.ConfirmPrayed -> applyDialogResult(PrayerStatus.PRAYED)
            Intent.ConfirmMissed -> applyDialogResult(PrayerStatus.MISSED)
            Intent.DismissDialog -> _state.update { it.copy(dialogPrayer = null) }
        }
    }

    private fun applyDialogResult(status: PrayerStatus) {
        val prayer = _state.value.dialogPrayer ?: return
        val date = _state.value.selectedDate
        viewModelScope.launch {
            setStatus(date, prayer, status)
            _state.update { it.copy(dialogPrayer = null) }
            refreshStreak()
        }
    }

    private fun loadMonth(month: java.time.YearMonth) {
        monthJob?.cancel()
        monthJob = observeMonth(month)
            .onEach { recordsByDate ->
                val today = LocalDate.now()
                val statusMap = recordsByDate.mapValues { (date, records) ->
                    dayStatusFor(
                        date,
                        records,
                        today
                    )
                }
                _state.update { it.copy(monthDayStatus = statusMap, isLoading = false) }
            }
            .launchIn(viewModelScope)
    }

    private fun loadDate(date: LocalDate) {
        dateJob?.cancel()
        dateJob = observeDate(date)
            .onEach { records -> _state.update { it.copy(selectedDateRecords = records) } }
            .launchIn(viewModelScope)
    }

    private fun refreshStreak() {
        viewModelScope.launch {
            _state.update { it.copy(currentStreak = getCurrentStreak()) }
        }
    }

    private fun dayStatusFor(
        date: LocalDate,
        records: Map<PrayerType, PrayerStatus>,
        today: LocalDate
    ): DayStatus = when {
        date.isAfter(today) -> DayStatus.FUTURE
        records.values.any { it == PrayerStatus.MISSED } -> DayStatus.HAS_MISSED
        PrayerType.entries.all { records[it] == PrayerStatus.PRAYED } -> DayStatus.ALL_PRAYED
        else -> DayStatus.IN_PROGRESS
    }
}