package com.mhq.salati.settings.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.settings.domain.model.AdhanSound
import com.mhq.salati.settings.domain.model.AppLanguage
import com.mhq.salati.settings.domain.model.AppSettings
import com.mhq.salati.settings.domain.model.CalculationMethod
import com.mhq.salati.settings.domain.model.Madhab
import com.mhq.salati.settings.domain.model.ThemeMode
import com.mhq.salati.settings.domain.usecases.ObserveSettingsUseCase
import com.mhq.salati.settings.domain.usecases.UpdateAdhanSoundUseCase
import com.mhq.salati.settings.domain.usecases.UpdateCalculationMethodUseCase
import com.mhq.salati.settings.domain.usecases.UpdateHijriDateOffsetUseCase
import com.mhq.salati.settings.domain.usecases.UpdateLanguageUseCase
import com.mhq.salati.settings.domain.usecases.UpdateMadhabUseCase
import com.mhq.salati.settings.domain.usecases.UpdateNotificationsEnabledUseCase
import com.mhq.salati.settings.domain.usecases.UpdateThemeModeUseCase
import com.mhq.salati.settings.presentation.contract.SettingsContract
import com.mhq.salati.BuildConfig
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

    private val _state =
        MutableStateFlow(SettingsContract.State(appVersion = BuildConfig.VERSION_NAME))
    val state: StateFlow<SettingsContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<SettingsContract.Effect>()
    val effect: SharedFlow<SettingsContract.Effect> = _effect

    init {
        observeSettingsUseCase()
            .onEach { settings -> applySettingsToState(settings) }
            .launchIn(viewModelScope)
    }

    fun onIntent(intent: SettingsContract.Intent) {
        when (intent) {
            is SettingsContract.Intent.ToggleNotifications -> updateNotifications(intent.enabled)
            is SettingsContract.Intent.SelectCalculationMethod -> updateCalculationMethod(intent.method)
            is SettingsContract.Intent.SelectMadhab -> updateMadhab(intent.madhab)
            is SettingsContract.Intent.SelectTheme -> updateTheme(intent.mode)
            is SettingsContract.Intent.SelectLanguage -> updateLanguage(intent.language)
            is SettingsContract.Intent.SelectAdhanSound -> updateAdhanSound(intent.sound)
            SettingsContract.Intent.IncrementHijriOffset -> updateHijriOffset(_state.value.hijriDateOffset + 1)
            SettingsContract.Intent.DecrementHijriOffset -> updateHijriOffset(_state.value.hijriDateOffset - 1)
            is SettingsContract.Intent.OpenSelector -> _state.value = _state.value.copy(activeSelector = intent.type)
            SettingsContract.Intent.CloseSelector -> _state.value = _state.value.copy(activeSelector = null)
            SettingsContract.Intent.RateApp -> emitEffect(SettingsContract.Effect.OpenPlayStoreListing)
            SettingsContract.Intent.ShareApp -> emitEffect(SettingsContract.Effect.LaunchShareSheet)
            SettingsContract.Intent.ContactSupport -> emitEffect(SettingsContract.Effect.OpenEmailClient)
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
            .onFailure { emitEffect(SettingsContract.Effect.ShowError("Couldn't update notification settings")) }
    }

    private fun updateCalculationMethod(method: CalculationMethod) = viewModelScope.launch {
        runCatching { updateCalculationMethodUseCase(method) }
            .onFailure { emitEffect(SettingsContract.Effect.ShowError("Couldn't update calculation method")) }
    }

    private fun updateMadhab(madhab: Madhab) = viewModelScope.launch {
        runCatching { updateMadhabUseCase(madhab) }
            .onFailure { emitEffect(SettingsContract.Effect.ShowError("Couldn't update madhab")) }
    }

    private fun updateTheme(mode: ThemeMode) = viewModelScope.launch {
        runCatching { updateThemeModeUseCase(mode) }
            .onFailure { emitEffect(SettingsContract.Effect.ShowError("Couldn't update theme")) }
    }

    private fun updateLanguage(language: AppLanguage) = viewModelScope.launch {
        runCatching { updateLanguageUseCase(language) }
            .onSuccess { emitEffect(SettingsContract.Effect.LanguageChangedRestartRequired) }
            .onFailure { emitEffect(SettingsContract.Effect.ShowError("Couldn't update language")) }
    }

    private fun updateAdhanSound(sound: AdhanSound) = viewModelScope.launch {
        runCatching { updateAdhanSoundUseCase(sound) }
            .onFailure { emitEffect(SettingsContract.Effect.ShowError("Couldn't update adhan sound")) }
    }

    private fun updateHijriOffset(offset: Int) {
        val clamped = offset.coerceIn(-2, 2)
        viewModelScope.launch {
            runCatching { updateHijriDateOffsetUseCase(clamped) }
                .onFailure { emitEffect(SettingsContract.Effect.ShowError("Couldn't update Hijri offset")) }
        }
    }

    private fun emitEffect(effect: SettingsContract.Effect) =
        viewModelScope.launch { _effect.emit(effect) }
}