package com.mhq.salati.alarms.presentation.contract

import com.mhq.salati.R
import com.mhq.salati.alarms.domain.model.CustomAlarm
import com.mhq.salati.shared.ui.UiText

/**
 * MVI contract for the custom alarms screen,
 * where users manage alarms that fire at an offset before or after a prayer time
 * (for example, "15 minutes before Fajr").
 *
 * The screen renders a single [State],
 * reports user actions as [Intent]s,
 * and reacts to one-shot [Effect]s
 * (Snack-bars and similar) emitted by the ViewModel.
 */
object AlarmsContract {

    /**
     * Immutable UI state for the alarms screen.
     *
     * @property isLoading
     * `true` until the first emission of the alarm list arrives.
     * Defaults to `true` so the screen shows a loading indicator on first composition.
     * @property alarms
     * All persisted custom alarms, kept in sync with the database.
     * @property isEditorVisible
     * Whether the add/edit alarm editor is currently shown.
     * @property editingAlarm
     * The alarm being edited.
     * `null` while the editor is closed,
     * and also `null` when the editor is open in "create" mode ([isEditorVisible] is `true`).
     * @property errorMessage
     * The latest error to surface inline, or `null` when there is none.
     */
    data class State(
        val isLoading: Boolean = true,
        val alarms: List<CustomAlarm> = emptyList(),
        val isEditorVisible: Boolean = false,
        val editingAlarm: CustomAlarm? = null,
        val errorMessage: UiText? = null
    )

    /** User actions and lifecycle events the alarms screen can send to its ViewModel. */
    sealed interface Intent {

        /** (Re)starts observing the persisted alarm list.
         * The ViewModel also does this on init. */
        data object LoadAlarms : Intent

        /** Opens the editor in "create" mode with no pre-filled alarm. */
        data object AddAlarm : Intent

        /** Opens the editor pre-filled with [alarm]. */
        data class EditAlarm(val alarm: CustomAlarm) : Intent

        /**
         * Persists [alarm] and closes the editor.
         *
         * An alarm with `id == 0L` is treated as new and created;
         * any other id updates the existing alarm.
         * Creating an alarm equivalent to an existing one
         * (same prayer, offset,direction and active days) is rejected with [Effect.ShowError].
         */
        data class SaveAlarm(val alarm: CustomAlarm) : Intent

        /** Permanently deletes the alarm with the given [id]. */
        data class DeleteAlarm(val id: Long) : Intent

        /** Enables or disables the alarm with the given [id] without deleting it. */
        data class ToggleAlarm(val id: Long, val enabled: Boolean) : Intent

        /** Closes the editor and discards any unsaved edits. */
        data object DismissEditor : Intent
    }

    /** One-shot side effects, consumed once by the container. */
    sealed interface Effect {

        /** Shows [message] to the user as a transient error (for example, in a snackbar). */
        data class ShowError(val message: UiText) : Effect

        /** Confirms that an alarm was saved.
         * [message] defaults to the standard "alarm saved" text. */
        data class AlarmSaved(val message: UiText = UiText.Res(R.string.alarm_saved)) : Effect
    }
}