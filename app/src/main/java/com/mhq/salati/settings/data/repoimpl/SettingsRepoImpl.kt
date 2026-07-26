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