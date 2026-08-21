package com.mhq.salati.prayertracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.R
import com.mhq.salati.prayertracker.domain.model.DayStatus
import com.mhq.salati.prayertracker.domain.model.PrayerStatus
import com.mhq.salati.prayertracker.domain.usecases.GetCurrentStreakUseCase
import com.mhq.salati.prayertracker.domain.usecases.ObservePrayerRecordsForDateUseCase
import com.mhq.salati.prayertracker.domain.usecases.ObservePrayerRecordsForMonthUseCase
import com.mhq.salati.prayertracker.domain.usecases.SetPrayerStatusUseCase
import com.mhq.salati.prayertracker.presentation.contract.PrayerTrackerContract
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.presentation.components.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class PrayerTrackerViewModel @Inject constructor(
    private val clock: Clock,
    private val observeMonth: ObservePrayerRecordsForMonthUseCase,
    private val observeDate: ObservePrayerRecordsForDateUseCase,
    private val setStatus: SetPrayerStatusUseCase,
    private val getCurrentStreak: GetCurrentStreakUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(PrayerTrackerContract.State())
    val state = _state.asStateFlow()

    // FIX: Channel instead of SharedFlow
    private val _effect = Channel<PrayerTrackerContract.Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    private var monthJob: Job? = null
    private var dateJob: Job? = null

    init {
        loadMonth(_state.value.selectedMonth)
        loadDate(_state.value.selectedDate)
        refreshStreak()
    }

    fun onIntent(intent: PrayerTrackerContract.Intent) {
        when (intent) {
            is PrayerTrackerContract.Intent.DateSelected -> {
                _state.update { it.copy(selectedDate = intent.date) }
                loadDate(intent.date)
            }
            is PrayerTrackerContract.Intent.MonthChanged -> {
                val newMonth = _state.value.selectedMonth.plusMonths(intent.delta.toLong())
                _state.update { it.copy(selectedMonth = newMonth) }
                loadMonth(newMonth)
            }
            is PrayerTrackerContract.Intent.PrayerTileTapped -> {
                // FIX: Use injected clock instead of LocalDate.now()
                if (_state.value.selectedDate.isAfter(clock.today())) return
                _state.update { it.copy(dialogPrayer = intent.prayer) }
            }
            PrayerTrackerContract.Intent.ConfirmPrayed -> applyDialogResult(PrayerStatus.PRAYED)
            PrayerTrackerContract.Intent.ConfirmMissed -> applyDialogResult(PrayerStatus.MISSED)
            PrayerTrackerContract.Intent.DismissDialog -> _state.update { it.copy(dialogPrayer = null) }
        }
    }

    private fun applyDialogResult(status: PrayerStatus) {
        val prayer = _state.value.dialogPrayer ?: return
        val date = _state.value.selectedDate
        viewModelScope.launch {
            try {
                setStatus(date, prayer, status)
                _state.update { it.copy(dialogPrayer = null) }
                refreshStreak()
            } catch (e: Exception) {
                _state.update { it.copy(dialogPrayer = null) }
                _effect.send(
                    PrayerTrackerContract.Effect.ShowError(
                        UiText.Res(R.string.failed_to_update_prayer_status)
                    )
                )
            }
        }
    }

    private fun loadMonth(month: YearMonth) {
        monthJob?.cancel()
        monthJob = observeMonth(month)
            .onEach { recordsByDate ->
                // FIX: Use clock.today() instead of LocalDate.now()
                val today = clock.today()
                val statusMap = recordsByDate.mapValues { (date, records) ->
                    dayStatusFor(date, records, today)
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
        records: Map<PrayerName, PrayerStatus>,
        today: LocalDate
    ): DayStatus = when {
        date.isAfter(today) -> DayStatus.FUTURE
        records.values.any { it == PrayerStatus.MISSED } -> DayStatus.HAS_MISSED
        PrayerName.majorEntries.all { records[it] == PrayerStatus.PRAYED } -> DayStatus.ALL_PRAYED
        else -> DayStatus.IN_PROGRESS
    }
}