package com.mhq.salati.settings.domain.model

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import com.mhq.salati.R

enum class CustomAlarmSound(@StringRes val displayNameRes: Int, @RawRes val rawRes: Int?) {
    DEFAULT(R.string.custom_alarm_sound_default, null),
    ALERT(R.string.custom_alarm_sound_alert, R.raw.minor_timing_alert)
}