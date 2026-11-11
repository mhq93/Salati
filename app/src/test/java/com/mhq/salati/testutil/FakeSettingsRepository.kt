package com.mhq.salati.testutil

import com.mhq.salati.settings.domain.model.AdhanSound
import com.mhq.salati.settings.domain.model.AppLanguage
import com.mhq.salati.settings.domain.model.AppSettings
import com.mhq.salati.settings.domain.model.CalculationMethod
import com.mhq.salati.settings.domain.model.CustomAlarmSound
import com.mhq.salati.settings.domain.model.Madhab
import com.mhq.salati.settings.domain.model.ThemeMode
import com.mhq.salati.settings.domain.model.TimeFormat
import com.mhq.salati.settings.domain.repo.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeSettingsRepository(
    private val settings: AppSettings = AppSettings()
) : SettingsRepository {
    override fun observeSettings(): Flow<AppSettings> = flowOf(settings)
    override suspend fun setNotificationsEnabled(enabled: Boolean) = Unit
    override suspend fun setCalculationMethod(method: CalculationMethod) = Unit
    override suspend fun setMadhab(madhab: Madhab) = Unit
    override suspend fun setThemeMode(mode: ThemeMode) = Unit
    override suspend fun setLanguage(language: AppLanguage) = Unit
    override suspend fun setAdhanSound(sound: AdhanSound) = Unit
    override suspend fun setCustomAlarmSound(sound: CustomAlarmSound) = Unit
    override suspend fun setHijriDateOffset(offset: Int) = Unit
    override suspend fun setTimeFormat(format: TimeFormat) = Unit
}