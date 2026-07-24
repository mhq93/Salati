package com.mhq.salati.data.repoimpl.settings

import com.mhq.salati.data.local.settings.SettingsDataStore
import com.mhq.salati.domain.model.settings.AdhanSound
import com.mhq.salati.domain.model.settings.AppLanguage
import com.mhq.salati.domain.model.settings.AppSettings
import com.mhq.salati.domain.model.settings.CalculationMethod
import com.mhq.salati.domain.model.settings.Madhab
import com.mhq.salati.domain.model.settings.ThemeMode
import com.mhq.salati.domain.repo.settings.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsRepoImpl @Inject constructor(
    private val dataStore: SettingsDataStore
) : SettingsRepository {

    override fun observeSettings(): Flow<AppSettings> = dataStore.settingsFlow

    override suspend fun setNotificationsEnabled(enabled: Boolean) =
        dataStore.setNotificationsEnabled(enabled)

    override suspend fun setCalculationMethod(method: CalculationMethod) =
        dataStore.setCalculationMethod(method)

    override suspend fun setMadhab(madhab: Madhab) =
        dataStore.setMadhab(madhab)

    override suspend fun setThemeMode(mode: ThemeMode) =
        dataStore.setThemeMode(mode)

    override suspend fun setLanguage(language: AppLanguage) =
        dataStore.setLanguage(language)

    override suspend fun setAdhanSound(sound: AdhanSound) =
        dataStore.setAdhanSound(sound)

    override suspend fun setHijriDateOffset(offset: Int) =
        dataStore.setHijriDateOffset(offset)
}