package com.mhq.salati.alarms.presentation.contract

import com.mhq.salati.alarms.domain.model.CustomAlarm
import com.mhq.salati.shared.presentation.components.UiText

object AlarmsContract {

    data class State(
        val isLoading: Boolean = true,
        val alarms: List<CustomAlarm> = emptyList(),
        val isEditorVisible: Boolean = false,
        val editingAlarm: CustomAlarm? = null,
        val errorMessage: UiText? = null
    )

    sealed interface Intent {
        data object LoadAlarms : Intent
        data object AddAlarmClicked : Intent
        data class EditAlarmClicked(val alarm: CustomAlarm) : Intent
        data object DismissEditor : Intent
        data class SaveAlarm(val alarm: CustomAlarm) : Intent
        data class DeleteAlarm(val id: Long) : Intent
        data class ToggleAlarm(val id: Long, val enabled: Boolean) : Intent
    }

    sealed interface Effect {
        data class ShowError(val message: UiText) : Effect
        data object AlarmSaved : Effect
    }
}