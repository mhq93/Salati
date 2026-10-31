package com.mhq.salati.shared.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mhq.salati.R
import com.mhq.salati.shared.presentation.model.MessageUi

// The user-facing text of a message.
// Lives here (not in Presentation) because it is an Android resource.
val MessageUi.textRes: Int
    @StringRes get() = when (this) {
        MessageUi.ALARM_SAVED -> R.string.alarm_saved
        MessageUi.COULDNT_DELETE_ALARM -> R.string.couldn_t_delete_alarm
        MessageUi.COULDNT_DETERMINE_LOCATION_NAME -> R.string.couldn_t_determine_location_name
        MessageUi.COULDNT_LOAD_ALARMS -> R.string.couldn_t_load_alarms
        MessageUi.COULDNT_SAVE_ALARM -> R.string.couldn_t_save_alarm
        MessageUi.COULDNT_SAVE_LOCATION -> R.string.couldn_t_save_location
        MessageUi.COULDNT_UPDATE_ADHAN_SOUND -> R.string.couldn_t_update_adhan_sound
        MessageUi.COULDNT_UPDATE_ALARM -> R.string.couldn_t_update_alarm
        MessageUi.COULDNT_UPDATE_CALCULATION_METHOD -> R.string.couldn_t_update_calculation_method
        MessageUi.COULDNT_UPDATE_CUSTOM_ALARM_SOUND -> R.string.couldn_t_update_custom_alarm_sound
        MessageUi.COULDNT_UPDATE_HIJRI_OFFSET -> R.string.couldn_t_update_hijri_offset
        MessageUi.COULDNT_UPDATE_LANGUAGE -> R.string.couldn_t_update_language
        MessageUi.COULDNT_UPDATE_MADHAB -> R.string.couldn_t_update_madhab
        MessageUi.COULDNT_UPDATE_NOTIFICATION_SETTINGS -> R.string.couldn_t_update_notification_settings
        MessageUi.COULDNT_UPDATE_THEME -> R.string.couldn_t_update_theme
        MessageUi.COULDNT_UPDATE_TIME_FORMAT -> R.string.couldn_t_update_time_format
        MessageUi.DUPLICATE_ALARM_EXISTS -> R.string.duplicate_alarm_exists
        MessageUi.FAILED_TO_GET_LOCATION -> R.string.failed_to_get_location
        MessageUi.FAILED_TO_GET_LOCATION_NAME -> R.string.failed_to_get_location_name
        MessageUi.FAILED_TO_LOAD_QIBLA_DIRECTION -> R.string.failed_to_load_qibla_direction
        MessageUi.FAILED_TO_UPDATE_PRAYER_STATUS -> R.string.failed_to_update_prayer_status
        MessageUi.LOCATION_PERMISSION_IS_REQUIRED_TO_SHOW_PRAYER_TIMES -> R.string.location_permission_is_required_to_show_prayer_times
        MessageUi.LOCATION_PERMISSION_PERMANENTLY_DENIED -> R.string.location_permission_permanently_denied
        MessageUi.LOCATION_PERMISSION_REQUIRED -> R.string.location_permission_required
        MessageUi.LOCATION_SERVICES_DISABLED -> R.string.location_services_disabled
        MessageUi.NO_INTERNET_CONNECTION -> R.string.no_internet_connection
        MessageUi.REQUEST_TIMED_OUT_CHECK_CONNECTION -> R.string.request_timed_out_check_connection
        MessageUi.SEARCH_FAILED_CHECK_CONNECTION -> R.string.search_failed_check_connection
        MessageUi.SOMETHING_WENT_WRONG -> R.string.something_went_wrong
    }

@Composable
fun MessageUi.asString(): String = stringResource(textRes)

fun MessageUi.asString(context: Context): String = context.getString(textRes)