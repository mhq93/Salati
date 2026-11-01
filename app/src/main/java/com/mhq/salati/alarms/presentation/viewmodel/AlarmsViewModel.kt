package com.mhq.salati.alarms.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.alarms.domain.model.DayToggleResult
import com.mhq.salati.alarms.domain.model.SaveAlarmResult
import com.mhq.salati.alarms.domain.usecases.DeleteCustomAlarmUseCase
import com.mhq.salati.alarms.domain.usecases.ObserveCustomAlarmsUseCase
import com.mhq.salati.alarms.domain.usecases.SaveCustomAlarmUseCase
import com.mhq.salati.alarms.domain.usecases.ToggleCustomAlarmDayUseCase
import com.mhq.salati.alarms.domain.usecases.ToggleCustomAlarmUseCase
import com.mhq.salati.alarms.presentation.contract.AlarmsContract.Effect
import com.mhq.salati.alarms.presentation.contract.AlarmsContract.Intent
import com.mhq.salati.alarms.presentation.contract.AlarmsContract.State
import com.mhq.salati.alarms.presentation.mapper.toDomain
import com.mhq.salati.alarms.presentation.mapper.toUi
import com.mhq.salati.alarms.presentation.model.CustomAlarmUi
import com.mhq.salati.shared.presentation.model.MessageUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmsViewModel @Inject constructor(
    private val observeCustomAlarmsUseCase: ObserveCustomAlarmsUseCase,
    private val saveCustomAlarmUseCase: SaveCustomAlarmUseCase,
    private val deleteCustomAlarmUseCase: DeleteCustomAlarmUseCase,
    private val toggleCustomAlarmUseCase: ToggleCustomAlarmUseCase,
    private val toggleCustomAlarmDayUseCase: ToggleCustomAlarmDayUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    private val _effect = Channel<Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init { observeAlarms() }

    fun onIntent(intent: Intent) {
        when (intent) {
            is Intent.LoadAlarms -> observeAlarms()
            is Intent.AddAlarm -> _state.update { it.copy(isEditorVisible = true, editingAlarm = null) }
            is Intent.EditAlarm -> _state.update { it.copy(isEditorVisible = true, editingAlarm = intent.alarm) }
            is Intent.DismissEditor -> _state.update { it.copy(isEditorVisible = false, editingAlarm = null) }
            is Intent.SaveAlarm -> saveAlarm(intent.alarm)
            is Intent.DeleteAlarm -> deleteAlarm(intent.id)
            is Intent.ToggleAlarm -> toggleAlarm(intent.id, intent.enabled)
            is Intent.AlarmDayToggled -> toggleAlarmDay(intent.id, intent.calendarDay)
        }
    }

    private fun observeAlarms() {
        viewModelScope.launch {
            try {
                observeCustomAlarmsUseCase().collect { alarms ->
                    _state.update { it.copy(isLoading = false, alarms = alarms.map { alarm -> alarm.toUi() }) }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                val message = MessageUi.COULDNT_LOAD_ALARMS
                _state.update { it.copy(isLoading = false, errorMessage = message) }
                _effect.send(Effect.ShowError(message))
            }
        }
    }

    private fun saveAlarm(alarm: CustomAlarmUi) {
        viewModelScope.launch {
            try {
                when (saveCustomAlarmUseCase(alarm.toDomain())) {
                    SaveAlarmResult.CONFLICT ->
                        _effect.send(Effect.ShowError(MessageUi.DUPLICATE_ALARM_EXISTS))
                    SaveAlarmResult.SAVED -> {
                        _state.update { it.copy(isEditorVisible = false, editingAlarm = null) }
                        _effect.send(Effect.AlarmSaved())
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                val message = MessageUi.COULDNT_SAVE_ALARM
                _state.update { it.copy(errorMessage = message) }
                _effect.send(Effect.ShowError(message))
            }
        }
    }

    private fun deleteAlarm(id: Long) {
        viewModelScope.launch {
            try {
                deleteCustomAlarmUseCase(id)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _effect.send(Effect.ShowError(MessageUi.COULDNT_DELETE_ALARM))
            }
        }
    }

    private fun toggleAlarm(id: Long, enabled: Boolean) {
        viewModelScope.launch {
            try {
                toggleCustomAlarmUseCase(id, enabled)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _effect.send(Effect.ShowError(MessageUi.COULDNT_UPDATE_ALARM))
            }
        }
    }

    private fun toggleAlarmDay(id: Long, calendarDay: Int) {
        viewModelScope.launch {
            try {
                if (toggleCustomAlarmDayUseCase(id, calendarDay) == DayToggleResult.CONFLICT) {
                    _effect.send(Effect.ShowError(MessageUi.DUPLICATE_ALARM_EXISTS))
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _effect.send(Effect.ShowError(MessageUi.COULDNT_UPDATE_ALARM))
            }
        }
    }
}