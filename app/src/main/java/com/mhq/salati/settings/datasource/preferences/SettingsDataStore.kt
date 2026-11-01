package com.mhq.salati.settings.datasource.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.settingsDataStore by preferencesDataStore(name = "settings_prefs")

@Singleton
class SettingsDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object Keys {
        val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
        val CALCULATION_METHOD = stringPreferencesKey("calculation_method")
        val MADHAB = stringPreferencesKey("madhab")
        val THEME_MODE = stringPreferencesKey("theme_mode")
        val LANGUAGE = stringPreferencesKey("language")
        val IS_LANGUAGE_SELECTED = booleanPreferencesKey("is_language_selected")
        val ADHAN_SOUND = stringPreferencesKey("adhan_sound")
        val CUSTOM_ALARM_SOUND = stringPreferencesKey("custom_alarm_sound")
        val HIJRI_OFFSET = intPreferencesKey("hijri_date_offset")
        val TIME_FORMAT = stringPreferencesKey("time_format")
    }

    val storedSettings: Flow<StoredSettings> = context.settingsDataStore.data.map { prefs ->
        StoredSettings(
            notificationsEnabled = prefs[Keys.NOTIFICATIONS_ENABLED] ?: true,
            calculationMethod = prefs[Keys.CALCULATION_METHOD],
            madhab = prefs[Keys.MADHAB],
            themeMode = prefs[Keys.THEME_MODE],
            language = prefs[Keys.LANGUAGE],
            isLanguageSelected = prefs[Keys.IS_LANGUAGE_SELECTED] ?: false,
            adhanSound = prefs[Keys.ADHAN_SOUND],
            customAlarmSound = prefs[Keys.CUSTOM_ALARM_SOUND],
            hijriDateOffset = prefs[Keys.HIJRI_OFFSET] ?: 0,
            timeFormat = prefs[Keys.TIME_FORMAT]
        )
    }

    suspend fun setNotificationsEnabled(enabled: Boolean) {
        context.settingsDataStore.edit { it[Keys.NOTIFICATIONS_ENABLED] = enabled }
    }

    suspend fun setCalculationMethod(value: String) {
        context.settingsDataStore.edit { it[Keys.CALCULATION_METHOD] = value }
    }

    suspend fun setMadhab(value: String) {
        context.settingsDataStore.edit { it[Keys.MADHAB] = value }
    }

    suspend fun setThemeMode(value: String) {
        context.settingsDataStore.edit { it[Keys.THEME_MODE] = value }
    }

    suspend fun setLanguage(value: String) {
        context.settingsDataStore.edit { it[Keys.LANGUAGE] = value }
    }

    suspend fun setLanguageSelected(isSelected: Boolean) {
        context.settingsDataStore.edit { it[Keys.IS_LANGUAGE_SELECTED] = isSelected }
    }

    suspend fun setAdhanSound(value: String) {
        context.settingsDataStore.edit { it[Keys.ADHAN_SOUND] = value }
    }

    suspend fun setCustomAlarmSound(value: String) {
        context.settingsDataStore.edit { it[Keys.CUSTOM_ALARM_SOUND] = value }
    }

    suspend fun setHijriDateOffset(offset: Int) {
        context.settingsDataStore.edit { it[Keys.HIJRI_OFFSET] = offset }
    }

    suspend fun setTimeFormat(value: String) {
        context.settingsDataStore.edit { it[Keys.TIME_FORMAT] = value }
    }
}