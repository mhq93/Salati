package com.mhq.salati.settings.data.repoimpl

import com.mhq.salati.settings.data.mapper.toDomain
import com.mhq.salati.settings.datasource.preferences.SettingsDataStore
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
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsRepoImpl @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) : SettingsRepository {

    override fun observeSettings(): Flow<AppSettings> =
        settingsDataStore.storedSettings.map { it.toDomain() }

    override suspend fun setNotificationsEnabled(enabled: Boolean) =
        settingsDataStore.setNotificationsEnabled(enabled)

    override suspend fun setCalculationMethod(method: CalculationMethod) =
        settingsDataStore.setCalculationMethod(method.name)

    override suspend fun setMadhab(madhab: Madhab) =
        settingsDataStore.setMadhab(madhab.name)

    override suspend fun setThemeMode(mode: ThemeMode) =
        settingsDataStore.setThemeMode(mode.name)

    override suspend fun setLanguage(language: AppLanguage) =
        settingsDataStore.setLanguage(language.name)

    override suspend fun setLanguageSelected(isSelected: Boolean) =
        settingsDataStore.setLanguageSelected(isSelected)

    override suspend fun setAdhanSound(sound: AdhanSound) =
        settingsDataStore.setAdhanSound(sound.name)

    override suspend fun setCustomAlarmSound(sound: CustomAlarmSound) =
        settingsDataStore.setCustomAlarmSound(sound.name)

    override suspend fun setHijriDateOffset(offset: Int) =
        settingsDataStore.setHijriDateOffset(offset)

    override suspend fun setTimeFormat(format: TimeFormat) =
        settingsDataStore.setTimeFormat(format.name)
}