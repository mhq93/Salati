package com.mhq.salati.settings.domain.model

data class AppSettings(
    val notificationsEnabled: Boolean = true,
    val adhanSound: AdhanSound = AdhanSound.DEFAULT,
    val customAlarmSound: CustomAlarmSound = CustomAlarmSound.DEFAULT,
    val calculationMethod: CalculationMethod = CalculationMethod.EGYPTIAN_GENERAL_AUTHORITY,
    val madhab: Madhab = Madhab.SHAFI,
    val timeFormat: TimeFormat = TimeFormat.FORMAT_12_HOUR,
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val language: AppLanguage = AppLanguage.ENGLISH,
    val hijriDateOffset: Int = 0,
)