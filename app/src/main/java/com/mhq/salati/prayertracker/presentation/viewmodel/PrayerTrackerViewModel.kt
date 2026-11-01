package com.mhq.salati.prayertracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.prayertracker.domain.model.PrayerStatus
import com.mhq.salati.prayertracker.domain.usecases.GetCurrentStreakUseCase
import com.mhq.salati.prayertracker.domain.usecases.GetDuePrayersUseCase
import com.mhq.salati.prayertracker.domain.usecases.ObservePrayerRecordsForDateUseCase
import com.mhq.salati.prayertracker.domain.usecases.ObserveMonthDayStatusUseCase
import com.mhq.salati.prayertracker.domain.usecases.SetPrayerStatusUseCase
import com.mhq.salati.prayertracker.presentation.contract.PrayerTrackerContract
import com.mhq.salati.prayertracker.presentation.mapper.toUi
import com.mhq.salati.prayertracker.presentation.mapper.toUiMap
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.presentation.mapper.toDomain
import com.mhq.salati.shared.presentation.mapper.toUiSet
import com.mhq.salati.shared.presentation.model.MessageUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.YearMonth
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class PrayerTrackerViewModel @Inject constructor(
    private val clock: Clock,
    private val observeMonthDayStatusUseCase: ObserveMonthDayStatusUseCase,
    private val observePrayerRecordsForDateUseCase: ObservePrayerRecordsForDateUseCase,
    private val setPrayerStatusUseCase: SetPrayerStatusUseCase,
    private val getCurrentStreakUseCase: GetCurrentStreakUseCase,
    private val getDuePrayersUseCase: GetDuePrayersUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(
        clock.today().let { today ->
            PrayerTrackerContract.State(
                today = today,
                selectedMonth = YearMonth.from(today),
                selectedDate = today
            )
        }
    )

    val state = _state.asStateFlow()

    private val _effect = Channel<PrayerTrackerContract.Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        state.map { it.selectedMonth }
            .distinctUntilChanged()
            .flatMapLatest { month -> observeMonthDayStatusUseCase(month) }
            .onEach { statusByDate ->
                _state.update {
                    it.copy(
                        today = clock.today(),
                        monthDayStatus = statusByDate.mapValues { (_, status) -> status.toUi() },
                        isLoading = false
                    )
                }
            }
            .launchIn(viewModelScope)

        state.map { it.selectedDate }
            .distinctUntilChanged()
            .flatMapLatest { date -> observePrayerRecordsForDateUseCase(date) }
            .onEach { records ->
                _state.update { it.copy(selectedDateRecords = records.toUiMap()) }
            }
            .launchIn(viewModelScope)

        refreshStreak()
    }

    fun onIntent(intent: PrayerTrackerContract.Intent) {
        when (intent) {
            is PrayerTrackerContract.Intent.DateSelected -> {
                val today = clock.today()
                if (intent.date.isAfter(today)) return
                _state.update {
                    it.copy(selectedDate = intent.date, selectedDateRecords = emptyMap(), today = today)
                }
                refreshDuePrayers()
            }

            is PrayerTrackerContract.Intent.MonthChanged -> {
                val newMonth = _state.value.selectedMonth.plusMonths(intent.delta.toLong())
                if (newMonth.isAfter(YearMonth.from(clock.today()))) return
                _state.update { it.copy(selectedMonth = newMonth) }
            }

            is PrayerTrackerContract.Intent.PrayerTileTapped -> {
                // Re-checked at tap time, so a stale screen can never mark a prayer that isn't due.
                viewModelScope.launch {
                    val due = getDuePrayersUseCase(_state.value.selectedDate)
                    _state.update { it.copy(duePrayers = due.toUiSet()) }
                    if (intent.prayer.toDomain() in due) {
                        _state.update { it.copy(dialogPrayer = intent.prayer) }
                    }
                }
            }

            PrayerTrackerContract.Intent.ScreenResumed -> refreshDuePrayers()
            PrayerTrackerContract.Intent.ConfirmPrayed -> applyDialogResult(PrayerStatus.PRAYED)
            PrayerTrackerContract.Intent.ConfirmMissed -> applyDialogResult(PrayerStatus.MISSED)
            PrayerTrackerContract.Intent.DismissDialog -> _state.update { it.copy(dialogPrayer = null) }
        }
    }

    private fun refreshDuePrayers() {
        viewModelScope.launch {
            try {
                val due = getDuePrayersUseCase(_state.value.selectedDate)
                _state.update { it.copy(today = clock.today(), duePrayers = due.toUiSet()) }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                // Keep the previous lock state; the tap-time check still protects marking.
            }
        }
    }

    private fun applyDialogResult(status: PrayerStatus) {
        val prayer = _state.value.dialogPrayer?.toDomain() ?: return
        val date = _state.value.selectedDate
        viewModelScope.launch {
            try {
                setPrayerStatusUseCase(date, prayer, status)
                _state.update { it.copy(dialogPrayer = null) }
                refreshStreak()
            } catch (e: Exception) {
                _state.update { it.copy(dialogPrayer = null) }
                _effect.send(
                    PrayerTrackerContract.Effect.ShowError(
                        MessageUi.FAILED_TO_UPDATE_PRAYER_STATUS
                    )
                )
            }
        }
    }

    private fun refreshStreak() {
        viewModelScope.launch {
            _state.update { it.copy(currentStreak = getCurrentStreakUseCase()) }
        }
    }
}