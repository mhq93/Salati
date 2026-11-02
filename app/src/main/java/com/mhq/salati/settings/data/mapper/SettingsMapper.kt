package com.mhq.salati.settings.data.mapper

import com.mhq.salati.settings.datasource.preferences.StoredSettings
import com.mhq.salati.settings.domain.model.AppSettings

private inline fun <reified E : Enum<E>> String?.toEnumOrNull(): E? =
    this?.let { stored -> enumValues<E>().firstOrNull { it.name == stored } }

/** A missing or unknown stored choice falls back to the default setting. */
fun StoredSettings.toDomain(): AppSettings {
    val defaults = AppSettings()
    return AppSettings(
        notificationsEnabled = notificationsEnabled,
        calculationMethod = calculationMethod.toEnumOrNull() ?: defaults.calculationMethod,
        madhab = madhab.toEnumOrNull() ?: defaults.madhab,
        themeMode = themeMode.toEnumOrNull() ?: defaults.themeMode,
        language = language.toEnumOrNull() ?: defaults.language,
        adhanSound = adhanSound.toEnumOrNull() ?: defaults.adhanSound,
        customAlarmSound = customAlarmSound.toEnumOrNull() ?: defaults.customAlarmSound,
        hijriDateOffset = hijriDateOffset,
        timeFormat = timeFormat.toEnumOrNull() ?: defaults.timeFormat
    )
}