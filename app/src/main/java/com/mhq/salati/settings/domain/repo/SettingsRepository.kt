package com.mhq.salati.settings.domain.repo

import com.mhq.salati.settings.domain.model.AdhanSound
import com.mhq.salati.settings.domain.model.AppLanguage
import com.mhq.salati.settings.domain.model.AppSettings
import com.mhq.salati.settings.domain.model.CalculationMethod
import com.mhq.salati.settings.domain.model.Madhab
import com.mhq.salati.settings.domain.model.ThemeMode
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun observeSettings(): Flow<AppSettings>
    suspend fun setNotificationsEnabled(enabled: Boolean)
    suspend fun setCalculationMethod(method: CalculationMethod)
    suspend fun setMadhab(madhab: Madhab)
    suspend fun setThemeMode(mode: ThemeMode)
    suspend fun setLanguage(language: AppLanguage)
    suspend fun setLanguageSelected(isSelected: Boolean)
    suspend fun setAdhanSound(sound: AdhanSound)
    suspend fun setHijriDateOffset(offset: Int)
}