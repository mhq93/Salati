package com.mhq.salati.settings.presentation.mapper

import com.mhq.salati.settings.domain.model.AdhanSound
import com.mhq.salati.settings.domain.model.AppLanguage
import com.mhq.salati.settings.domain.model.CalculationMethod
import com.mhq.salati.settings.domain.model.CustomAlarmSound
import com.mhq.salati.settings.domain.model.Madhab
import com.mhq.salati.settings.domain.model.ThemeMode
import com.mhq.salati.settings.domain.model.TimeFormat
import com.mhq.salati.settings.presentation.model.AdhanSoundUi
import com.mhq.salati.settings.presentation.model.AppLanguageUi
import com.mhq.salati.settings.presentation.model.CalculationMethodUi
import com.mhq.salati.settings.presentation.model.CustomAlarmSoundUi
import com.mhq.salati.settings.presentation.model.MadhabUi
import com.mhq.salati.settings.presentation.model.ThemeModeUi
import com.mhq.salati.settings.presentation.model.TimeFormatUi

/** Every conversion is an exhaustive `when` (no else),
 * so adding a value to one side without the other fails to compile. */

// ---- AdhanSound
fun AdhanSound.toUi(): AdhanSoundUi = when (this) {
    AdhanSound.DEFAULT -> AdhanSoundUi.DEFAULT
    AdhanSound.EGYPT -> AdhanSoundUi.EGYPT
    AdhanSound.MAKKAH -> AdhanSoundUi.MAKKAH
    AdhanSound.MADINAH -> AdhanSoundUi.MADINAH
    AdhanSound.SILENT -> AdhanSoundUi.SILENT
}

fun AdhanSoundUi.toDomain(): AdhanSound = when (this) {
    AdhanSoundUi.DEFAULT -> AdhanSound.DEFAULT
    AdhanSoundUi.EGYPT -> AdhanSound.EGYPT
    AdhanSoundUi.MAKKAH -> AdhanSound.MAKKAH
    AdhanSoundUi.MADINAH -> AdhanSound.MADINAH
    AdhanSoundUi.SILENT -> AdhanSound.SILENT
}

/**
 * Reads the sound key the playback service receives in its Intent.
 * AdhanPlaybackRepoImpl writes it as `AdhanSound.name`.
 * An unknown or missing key falls back to the default sound.
 */
fun String?.toAdhanSoundUi(): AdhanSoundUi =
    this?.let { key -> runCatching { AdhanSound.valueOf(key) }.getOrNull() }
        ?.toUi()
        ?: AdhanSoundUi.DEFAULT

// ---- AppLanguage
fun AppLanguage.toUi(): AppLanguageUi = when (this) {
    AppLanguage.ENGLISH -> AppLanguageUi.ENGLISH
    AppLanguage.ARABIC -> AppLanguageUi.ARABIC
}

fun AppLanguageUi.toDomain(): AppLanguage = when (this) {
    AppLanguageUi.ENGLISH -> AppLanguage.ENGLISH
    AppLanguageUi.ARABIC -> AppLanguage.ARABIC
}

// ---- CalculationMethod
fun CalculationMethod.toUi(): CalculationMethodUi = when (this) {
    CalculationMethod.EGYPTIAN_GENERAL_AUTHORITY -> CalculationMethodUi.EGYPTIAN_GENERAL_AUTHORITY
    CalculationMethod.UMM_AL_QURA -> CalculationMethodUi.UMM_AL_QURA
    CalculationMethod.MUSLIM_WORLD_LEAGUE -> CalculationMethodUi.MUSLIM_WORLD_LEAGUE
    CalculationMethod.ISLAMIC_SOCIETY_OF_NORTH_AMERICA -> CalculationMethodUi.ISLAMIC_SOCIETY_OF_NORTH_AMERICA
    CalculationMethod.UNIVERSITY_OF_ISLAMIC_SCIENCES_KARACHI -> CalculationMethodUi.UNIVERSITY_OF_ISLAMIC_SCIENCES_KARACHI
}

fun CalculationMethodUi.toDomain(): CalculationMethod = when (this) {
    CalculationMethodUi.EGYPTIAN_GENERAL_AUTHORITY -> CalculationMethod.EGYPTIAN_GENERAL_AUTHORITY
    CalculationMethodUi.UMM_AL_QURA -> CalculationMethod.UMM_AL_QURA
    CalculationMethodUi.MUSLIM_WORLD_LEAGUE -> CalculationMethod.MUSLIM_WORLD_LEAGUE
    CalculationMethodUi.ISLAMIC_SOCIETY_OF_NORTH_AMERICA -> CalculationMethod.ISLAMIC_SOCIETY_OF_NORTH_AMERICA
    CalculationMethodUi.UNIVERSITY_OF_ISLAMIC_SCIENCES_KARACHI -> CalculationMethod.UNIVERSITY_OF_ISLAMIC_SCIENCES_KARACHI
}

// ---- CustomAlarmSound
fun CustomAlarmSound.toUi(): CustomAlarmSoundUi = when (this) {
    CustomAlarmSound.DEFAULT -> CustomAlarmSoundUi.DEFAULT
    CustomAlarmSound.ALERT -> CustomAlarmSoundUi.ALERT
}

fun CustomAlarmSoundUi.toDomain(): CustomAlarmSound = when (this) {
    CustomAlarmSoundUi.DEFAULT -> CustomAlarmSound.DEFAULT
    CustomAlarmSoundUi.ALERT -> CustomAlarmSound.ALERT
}

// ---- Madhab
fun Madhab.toUi(): MadhabUi = when (this) {
    Madhab.SHAFI -> MadhabUi.SHAFI
    Madhab.HANAFI -> MadhabUi.HANAFI
}

fun MadhabUi.toDomain(): Madhab = when (this) {
    MadhabUi.SHAFI -> Madhab.SHAFI
    MadhabUi.HANAFI -> Madhab.HANAFI
}

// ---- ThemeMode
fun ThemeMode.toUi(): ThemeModeUi = when (this) {
    ThemeMode.SYSTEM -> ThemeModeUi.SYSTEM
    ThemeMode.LIGHT -> ThemeModeUi.LIGHT
    ThemeMode.DARK -> ThemeModeUi.DARK
}

fun ThemeModeUi.toDomain(): ThemeMode = when (this) {
    ThemeModeUi.SYSTEM -> ThemeMode.SYSTEM
    ThemeModeUi.LIGHT -> ThemeMode.LIGHT
    ThemeModeUi.DARK -> ThemeMode.DARK
}

// ---- TimeFormat
fun TimeFormat.toUi(): TimeFormatUi = when (this) {
    TimeFormat.HOUR_12 -> TimeFormatUi.HOUR_12
    TimeFormat.HOUR_24 -> TimeFormatUi.HOUR_24
}

fun TimeFormatUi.toDomain(): TimeFormat = when (this) {
    TimeFormatUi.HOUR_12 -> TimeFormat.HOUR_12
    TimeFormatUi.HOUR_24 -> TimeFormat.HOUR_24
}