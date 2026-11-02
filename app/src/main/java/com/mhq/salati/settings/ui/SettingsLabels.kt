package com.mhq.salati.settings.ui

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import com.mhq.salati.R
import com.mhq.salati.settings.presentation.model.AdhanSoundUi
import com.mhq.salati.settings.presentation.model.AppLanguageUi
import com.mhq.salati.settings.presentation.model.CalculationMethodUi
import com.mhq.salati.settings.presentation.model.CustomAlarmSoundUi
import com.mhq.salati.settings.presentation.model.MadhabUi
import com.mhq.salati.settings.presentation.model.ThemeModeUi
import com.mhq.salati.settings.presentation.model.TimeFormatUi

val AppLanguageUi.displayNameRes: Int
    @StringRes get() = when (this) {
        AppLanguageUi.ENGLISH -> R.string.language_english
        AppLanguageUi.ARABIC -> R.string.language_arabic
    }

val TimeFormatUi.displayNameRes: Int
    @StringRes get() = when (this) {
        TimeFormatUi.FORMAT_12_HOUR -> R.string.time_format_12_hour
        TimeFormatUi.FORMAT_24_HOUR -> R.string.time_format_24_hour
    }

val ThemeModeUi.displayNameRes: Int
    @StringRes get() = when (this) {
        ThemeModeUi.SYSTEM -> R.string.theme_system_default
        ThemeModeUi.LIGHT -> R.string.theme_light
        ThemeModeUi.DARK -> R.string.theme_dark
    }

val MadhabUi.displayNameRes: Int
    @StringRes get() = when (this) {
        MadhabUi.SHAFI -> R.string.madhab_shafi
        MadhabUi.HANAFI -> R.string.madhab_hanafi
    }

val CalculationMethodUi.displayNameRes: Int
    @StringRes get() = when (this) {
        CalculationMethodUi.EGYPTIAN_GENERAL_AUTHORITY -> R.string.calc_method_egyptian_general_authority
        CalculationMethodUi.UMM_AL_QURA -> R.string.calc_method_umm_al_qura
        CalculationMethodUi.MUSLIM_WORLD_LEAGUE -> R.string.calc_method_muslim_world_league
        CalculationMethodUi.ISLAMIC_SOCIETY_OF_NORTH_AMERICA -> R.string.calc_method_isna
        CalculationMethodUi.UNIVERSITY_OF_ISLAMIC_SCIENCES_KARACHI -> R.string.calc_method_karachi
    }

val AdhanSoundUi.displayNameRes: Int
    @StringRes get() = when (this) {
        AdhanSoundUi.DEFAULT -> R.string.adhan_sound_default
        AdhanSoundUi.EGYPT -> R.string.adhan_sound_egypt
        AdhanSoundUi.MAKKAH -> R.string.adhan_sound_makkah
        AdhanSoundUi.MADINAH -> R.string.adhan_sound_madinah
        AdhanSoundUi.SILENT -> R.string.adhan_sound_silent
    }

val CustomAlarmSoundUi.displayNameRes: Int
    @StringRes get() = when (this) {
        CustomAlarmSoundUi.DEFAULT -> R.string.custom_alarm_sound_default
        CustomAlarmSoundUi.ALERT -> R.string.custom_alarm_sound_alert
    }

/** The bundled sound file, or null to use the system default. */
val CustomAlarmSoundUi.rawRes: Int?
    @RawRes get() = when (this) {
        CustomAlarmSoundUi.DEFAULT -> null
        CustomAlarmSoundUi.ALERT -> R.raw.minor_timing_alert
    }