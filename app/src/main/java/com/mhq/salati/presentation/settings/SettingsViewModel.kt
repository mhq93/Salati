package com.mhq.salati.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.BuildConfig
import com.mhq.salati.domain.model.settings.AdhanSound
import com.mhq.salati.domain.model.settings.AppLanguage
import com.mhq.salati.domain.model.settings.AppSettings
import com.mhq.salati.domain.model.settings.CalculationMethod
import com.mhq.salati.domain.model.settings.Madhab
import com.mhq.salati.domain.model.settings.ThemeMode
import com.mhq.salati.domain.usecases.settings.ObserveSettingsUseCase
import com.mhq.salati.domain.usecases.settings.UpdateAdhanSoundUseCase
import com.mhq.salati.domain.usecases.settings.UpdateCalculationMethodUseCase
import com.mhq.salati.domain.usecases.settings.UpdateHijriDateOffsetUseCase
import com.mhq.salati.domain.usecases.settings.UpdateLanguageUseCase
import com.mhq.salati.domain.usecases.settings.UpdateMadhabUseCase
import com.mhq.salati.domain.usecases.settings.UpdateNotificationsEnabledUseCase
import com.mhq.salati.domain.usecases.settings.UpdateThemeModeUseCase
import com.mhq.salati.presentation.settings.SettingsContract.Effect
import com.mhq.salati.presentation.settings.SettingsContract.Intent
import com.mhq.salati.presentation.settings.SettingsContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val observeSettingsUseCase: ObserveSettingsUseCase,
    private val updateNotificationsEnabledUseCase: UpdateNotificationsEnabledUseCase,
    private val updateCalculationMethodUseCase: UpdateCalculationMethodUseCase,
    private val updateMadhabUseCase: UpdateMadhabUseCase,
    private val updateThemeModeUseCase: UpdateThemeModeUseCase,
    private val updateLanguageUseCase: UpdateLanguageUseCase,
    private val updateAdhanSoundUseCase: UpdateAdhanSoundUseCase,
    private val updateHijriDateOffsetUseCase: UpdateHijriDateOffsetUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(State(appVersion = BuildConfig.VERSION_NAME))
    val state: StateFlow<State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<Effect>()
    val effect: SharedFlow<Effect> = _effect

    init {
        observeSettingsUseCase()
            .onEach { settings -> applySettingsToState(settings) }
            .launchIn(viewModelScope)
    }

    fun onIntent(intent: Intent) {
        when (intent) {
            is Intent.ToggleNotifications -> updateNotifications(intent.enabled)
            is Intent.SelectCalculationMethod -> updateCalculationMethod(intent.method)
            is Intent.SelectMadhab -> updateMadhab(intent.madhab)
            is Intent.SelectTheme -> updateTheme(intent.mode)
            is Intent.SelectLanguage -> updateLanguage(intent.language)
            is Intent.SelectAdhanSound -> updateAdhanSound(intent.sound)
            Intent.IncrementHijriOffset -> updateHijriOffset(_state.value.hijriDateOffset + 1)
            Intent.DecrementHijriOffset -> updateHijriOffset(_state.value.hijriDateOffset - 1)
            Intent.RateApp -> emitEffect(Effect.OpenPlayStoreListing)
            Intent.ShareApp -> emitEffect(Effect.LaunchShareSheet)
            Intent.ContactSupport -> emitEffect(Effect.OpenEmailClient)
        }
    }

    private fun applySettingsToState(settings: AppSettings) {
        _state.value = _state.value.copy(
            isLoading = false,
            notificationsEnabled = settings.notificationsEnabled,
            calculationMethod = settings.calculationMethod,
            madhab = settings.madhab,
            themeMode = settings.themeMode,
            language = settings.language,
            adhanSound = settings.adhanSound,
            hijriDateOffset = settings.hijriDateOffset
        )
    }

    private fun updateNotifications(enabled: Boolean) = viewModelScope.launch {
        runCatching { updateNotificationsEnabledUseCase(enabled) }
            .onFailure { emitEffect(Effect.ShowError("Couldn't update notification settings")) }
    }

    private fun updateCalculationMethod(method: CalculationMethod) = viewModelScope.launch {
        runCatching { updateCalculationMethodUseCase(method) }
            .onFailure { emitEffect(Effect.ShowError("Couldn't update calculation method")) }
    }

    private fun updateMadhab(madhab: Madhab) = viewModelScope.launch {
        runCatching { updateMadhabUseCase(madhab) }
            .onFailure { emitEffect(Effect.ShowError("Couldn't update madhab")) }
    }

    private fun updateTheme(mode: ThemeMode) = viewModelScope.launch {
        runCatching { updateThemeModeUseCase(mode) }
            .onFailure { emitEffect(Effect.ShowError("Couldn't update theme")) }
    }

    private fun updateLanguage(language: AppLanguage) = viewModelScope.launch {
        runCatching { updateLanguageUseCase(language) }
            .onSuccess { emitEffect(Effect.LanguageChangedRestartRequired) }
            .onFailure { emitEffect(Effect.ShowError("Couldn't update language")) }
    }

    private fun updateAdhanSound(sound: AdhanSound) = viewModelScope.launch {
        runCatching { updateAdhanSoundUseCase(sound) }
            .onFailure { emitEffect(Effect.ShowError("Couldn't update adhan sound")) }
    }

    private fun updateHijriOffset(offset: Int) {
        val clamped = offset.coerceIn(-2, 2)
        viewModelScope.launch {
            runCatching { updateHijriDateOffsetUseCase(clamped) }
                .onFailure { emitEffect(Effect.ShowError("Couldn't update Hijri offset")) }
        }
    }

    private fun emitEffect(effect: Effect) =
        viewModelScope.launch { _effect.emit(effect) }
}