package com.mhq.salati.settings.ui

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import com.mhq.salati.R
import com.mhq.salati.settings.domain.model.AdhanSound
import com.mhq.salati.settings.domain.model.AppLanguage
import com.mhq.salati.settings.domain.model.CalculationMethod
import com.mhq.salati.settings.domain.model.CustomAlarmSound
import com.mhq.salati.settings.domain.model.Madhab
import com.mhq.salati.settings.domain.model.ThemeMode

val AppLanguage.displayNameRes: Int
    @StringRes get() = when (this) {
        AppLanguage.ENGLISH -> R.string.language_english
        AppLanguage.ARABIC -> R.string.language_arabic
    }

val ThemeMode.displayNameRes: Int
    @StringRes get() = when (this) {
        ThemeMode.SYSTEM -> R.string.theme_system_default
        ThemeMode.LIGHT -> R.string.theme_light
        ThemeMode.DARK -> R.string.theme_dark
    }

val Madhab.displayNameRes: Int
    @StringRes get() = when (this) {
        Madhab.SHAFI -> R.string.madhab_shafi
        Madhab.HANAFI -> R.string.madhab_hanafi
    }

val CalculationMethod.displayNameRes: Int
    @StringRes get() = when (this) {
        CalculationMethod.EGYPTIAN_GENERAL_AUTHORITY -> R.string.calc_method_egyptian_general_authority
        CalculationMethod.UMM_AL_QURA -> R.string.calc_method_umm_al_qura
        CalculationMethod.MUSLIM_WORLD_LEAGUE -> R.string.calc_method_muslim_world_league
        CalculationMethod.ISLAMIC_SOCIETY_OF_NORTH_AMERICA -> R.string.calc_method_isna
        CalculationMethod.UNIVERSITY_OF_ISLAMIC_SCIENCES_KARACHI -> R.string.calc_method_karachi
    }

val AdhanSound.displayNameRes: Int
    @StringRes get() = when (this) {
        AdhanSound.DEFAULT -> R.string.adhan_sound_default
        AdhanSound.EGYPT -> R.string.adhan_sound_egypt
        AdhanSound.MAKKAH -> R.string.adhan_sound_makkah
        AdhanSound.MADINAH -> R.string.adhan_sound_madinah
        AdhanSound.SILENT -> R.string.adhan_sound_silent
    }

val CustomAlarmSound.displayNameRes: Int
    @StringRes get() = when (this) {
        CustomAlarmSound.DEFAULT -> R.string.custom_alarm_sound_default
        CustomAlarmSound.ALERT -> R.string.custom_alarm_sound_alert
    }

/** The bundled sound file, or null to use the system default. */
val CustomAlarmSound.rawRes: Int?
    @RawRes get() = when (this) {
        CustomAlarmSound.DEFAULT -> null
        CustomAlarmSound.ALERT -> R.raw.minor_timing_alert
    }