package com.mhq.salati.domain.repo.settings

import com.mhq.salati.domain.model.settings.AdhanSound
import com.mhq.salati.domain.model.settings.AppLanguage
import com.mhq.salati.domain.model.settings.AppSettings
import com.mhq.salati.domain.model.settings.CalculationMethod
import com.mhq.salati.domain.model.settings.Madhab
import com.mhq.salati.domain.model.settings.ThemeMode
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun observeSettings(): Flow<AppSettings>
    suspend fun setNotificationsEnabled(enabled: Boolean)
    suspend fun setCalculationMethod(method: CalculationMethod)
    suspend fun setMadhab(madhab: Madhab)
    suspend fun setThemeMode(mode: ThemeMode)
    suspend fun setLanguage(language: AppLanguage)
    suspend fun setAdhanSound(sound: AdhanSound)
    suspend fun setHijriDateOffset(offset: Int)
}