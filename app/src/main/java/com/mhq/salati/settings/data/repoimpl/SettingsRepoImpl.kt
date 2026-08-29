package com.mhq.salati.settings.data.repoimpl

import com.mhq.salati.settings.data.local.SettingsDataStore
import com.mhq.salati.settings.domain.model.AdhanSound
import com.mhq.salati.settings.domain.model.AppLanguage
import com.mhq.salati.settings.domain.model.AppSettings
import com.mhq.salati.settings.domain.model.CalculationMethod
import com.mhq.salati.settings.domain.model.Madhab
import com.mhq.salati.settings.domain.model.ThemeMode
import com.mhq.salati.settings.domain.repo.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsRepoImpl @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) : SettingsRepository {

    override fun observeSettings(): Flow<AppSettings> = settingsDataStore.settingsFlow

    override suspend fun setNotificationsEnabled(enabled: Boolean) =
        settingsDataStore.setNotificationsEnabled(enabled)

    override suspend fun setCalculationMethod(method: CalculationMethod) =
        settingsDataStore.setCalculationMethod(method)

    override suspend fun setMadhab(madhab: Madhab) =
        settingsDataStore.setMadhab(madhab)

    override suspend fun setThemeMode(mode: ThemeMode) =
        settingsDataStore.setThemeMode(mode)

    override suspend fun setLanguage(language: AppLanguage) =
        settingsDataStore.setLanguage(language)

    override suspend fun setLanguageSelected(isSelected: Boolean) =
        settingsDataStore.setLanguageSelected(isSelected)

    override suspend fun setAdhanSound(sound: AdhanSound) =
        settingsDataStore.setAdhanSound(sound)

    override suspend fun setHijriDateOffset(offset: Int) =
        settingsDataStore.setHijriDateOffset(offset)
}