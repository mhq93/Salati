package com.mhq.salati.alarms.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.R
import com.mhq.salati.alarms.domain.model.CustomAlarm
import com.mhq.salati.alarms.domain.usecases.CreateCustomAlarmUseCase
import com.mhq.salati.alarms.domain.usecases.DeleteCustomAlarmUseCase
import com.mhq.salati.alarms.domain.usecases.ObserveCustomAlarmsUseCase
import com.mhq.salati.alarms.domain.usecases.ToggleCustomAlarmUseCase
import com.mhq.salati.alarms.domain.usecases.UpdateCustomAlarmUseCase
import com.mhq.salati.alarms.presentation.contract.AlarmsContract.Effect
import com.mhq.salati.alarms.presentation.contract.AlarmsContract.Intent
import com.mhq.salati.alarms.presentation.contract.AlarmsContract.State
import com.mhq.salati.shared.presentation.components.UiText
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
    private val createCustomAlarmUseCase: CreateCustomAlarmUseCase,
    private val updateCustomAlarmUseCase: UpdateCustomAlarmUseCase,
    private val deleteCustomAlarmUseCase: DeleteCustomAlarmUseCase,
    private val toggleCustomAlarmUseCase: ToggleCustomAlarmUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    private val _effect = Channel<Effect>()
    val effect = _effect.receiveAsFlow()

    init { observeAlarms() }

    fun onIntent(intent: Intent) {
        when (intent) {
            is Intent.LoadAlarms -> observeAlarms()
            is Intent.AddAlarmClicked -> _state.update { it.copy(isEditorVisible = true, editingAlarm = null) }
            is Intent.EditAlarmClicked -> _state.update { it.copy(isEditorVisible = true, editingAlarm = intent.alarm) }
            is Intent.DismissEditor -> _state.update { it.copy(isEditorVisible = false, editingAlarm = null) }
            is Intent.SaveAlarm -> saveAlarm(intent.alarm)
            is Intent.DeleteAlarm -> deleteAlarm(intent.id)
            is Intent.ToggleAlarm -> toggleAlarm(intent.id, intent.enabled)
        }
    }

    private fun observeAlarms() {
        viewModelScope.launch {
            try {
                observeCustomAlarmsUseCase().collect { alarms ->
                    _state.update { it.copy(isLoading = false, alarms = alarms) }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, errorMessage = UiText.Raw(e.message.orEmpty())) }
                _effect.send(Effect.ShowError(UiText.Raw(e.message.orEmpty())))
            }
        }
    }

    private fun saveAlarm(alarm: CustomAlarm) {
        viewModelScope.launch {
            try {
                val isDuplicate = alarm.id == 0L && _state.value.alarms.any { it.isEquivalentTo(alarm) }
                if (isDuplicate) {
                    _effect.send(Effect.ShowError(UiText.Res(R.string.duplicate_alarm_exists)))
                    return@launch
                }
                if (alarm.id == 0L) createCustomAlarmUseCase(alarm) else updateCustomAlarmUseCase(alarm)
                _state.update { it.copy(isEditorVisible = false, editingAlarm = null) }
                _effect.send(Effect.AlarmSaved)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _state.update { it.copy(errorMessage = UiText.Raw(e.message.orEmpty())) }
                _effect.send(Effect.ShowError(UiText.Raw(e.message.orEmpty())))
            }
        }
    }

    private fun CustomAlarm.isEquivalentTo(other: CustomAlarm): Boolean =
        prayerName == other.prayerName &&
                offsetMinutes == other.offsetMinutes &&
                offsetDirection == other.offsetDirection &&
                everyDay == other.everyDay &&
                activeDays == other.activeDays

    private fun deleteAlarm(id: Long) {
        viewModelScope.launch {
            try {
                deleteCustomAlarmUseCase(id)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _effect.send(Effect.ShowError(UiText.Raw(e.message.orEmpty())))
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
                _effect.send(Effect.ShowError(UiText.Raw(e.message.orEmpty())))
            }
        }
    }
}