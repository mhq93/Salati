package com.mhq.salati.settings.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.mhq.salati.settings.domain.model.AdhanSound
import com.mhq.salati.settings.domain.model.AppLanguage
import com.mhq.salati.settings.domain.model.AppSettings
import com.mhq.salati.settings.domain.model.CalculationMethod
import com.mhq.salati.settings.domain.model.Madhab
import com.mhq.salati.settings.domain.model.ThemeMode
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
        val HIJRI_OFFSET = intPreferencesKey("hijri_date_offset")
    }

    val settingsFlow: Flow<AppSettings> = context.settingsDataStore.data.map { prefs ->
        AppSettings(
            notificationsEnabled = prefs[Keys.NOTIFICATIONS_ENABLED] ?: true,
            calculationMethod = prefs[Keys.CALCULATION_METHOD]
                ?.let { runCatching { CalculationMethod.valueOf(it) }.getOrNull() }
                ?: CalculationMethod.EGYPTIAN_GENERAL_AUTHORITY,
            madhab = prefs[Keys.MADHAB]
                ?.let { runCatching { Madhab.valueOf(it) }.getOrNull() }
                ?: Madhab.SHAFI,
            themeMode = prefs[Keys.THEME_MODE]
                ?.let { runCatching { ThemeMode.valueOf(it) }.getOrNull() }
                ?: ThemeMode.SYSTEM,
            language = prefs[Keys.LANGUAGE]
                ?.let { runCatching { AppLanguage.valueOf(it) }.getOrNull() }
                ?: AppLanguage.ENGLISH,
            isLanguageSelected = prefs[Keys.IS_LANGUAGE_SELECTED] ?: false,
            adhanSound = prefs[Keys.ADHAN_SOUND]
                ?.let { runCatching { AdhanSound.valueOf(it) }.getOrNull() }
                ?: AdhanSound.DEFAULT,
            hijriDateOffset = prefs[Keys.HIJRI_OFFSET] ?: 0
        )
    }

    suspend fun setNotificationsEnabled(enabled: Boolean) {
        context.settingsDataStore.edit { it[Keys.NOTIFICATIONS_ENABLED] = enabled }
    }

    suspend fun setCalculationMethod(method: CalculationMethod) {
        context.settingsDataStore.edit { it[Keys.CALCULATION_METHOD] = method.name }
    }

    suspend fun setMadhab(madhab: Madhab) {
        context.settingsDataStore.edit { it[Keys.MADHAB] = madhab.name }
    }

    suspend fun setThemeMode(mode: ThemeMode) {
        context.settingsDataStore.edit { it[Keys.THEME_MODE] = mode.name }
    }

    suspend fun setLanguage(language: AppLanguage) {
        context.settingsDataStore.edit { it[Keys.LANGUAGE] = language.name }
    }

    suspend fun setLanguageSelected(isSelected: Boolean) {
        context.settingsDataStore.edit { it[Keys.IS_LANGUAGE_SELECTED] = isSelected }
    }

    suspend fun setAdhanSound(sound: AdhanSound) {
        context.settingsDataStore.edit { it[Keys.ADHAN_SOUND] = sound.name }
    }

    suspend fun setHijriDateOffset(offset: Int) {
        context.settingsDataStore.edit { it[Keys.HIJRI_OFFSET] = offset }
    }
}