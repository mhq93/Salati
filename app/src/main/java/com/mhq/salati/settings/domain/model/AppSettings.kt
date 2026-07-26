package com.mhq.salati.settings.domain.model

data class AppSettings(
    val notificationsEnabled: Boolean = true,
    val calculationMethod: CalculationMethod = CalculationMethod.EGYPTIAN,
    val madhab: Madhab = Madhab.SHAFI,
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val language: AppLanguage = AppLanguage.ENGLISH,
    val adhanSound: AdhanSound = AdhanSound.DEFAULT,
    val hijriDateOffset: Int = 0
)