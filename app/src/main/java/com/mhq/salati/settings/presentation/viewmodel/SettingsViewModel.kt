package com.mhq.salati.settings.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.BuildConfig
import com.mhq.salati.R
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
import com.mhq.salati.shared.presentation.components.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

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

    private val _state = MutableStateFlow(
        SettingsContract.State(appVersion = BuildConfig.VERSION_NAME)
    )
    val state: StateFlow<SettingsContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<SettingsContract.Effect>()
    val effect: SharedFlow<SettingsContract.Effect> = _effect.asSharedFlow()

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
            is SettingsContract.Intent.IncrementHijriOffset -> updateHijriOffset(_state.value.hijriDateOffset + 1)
            is SettingsContract.Intent.DecrementHijriOffset -> updateHijriOffset(_state.value.hijriDateOffset - 1)
            is SettingsContract.Intent.OpenSelector -> _state.update { it.copy(activeSelector = intent.type) }
            is SettingsContract.Intent.CloseSelector -> _state.update { it.copy(activeSelector = null) }
            is SettingsContract.Intent.RateApp -> emitEffect(SettingsContract.Effect.OpenPlayStoreListing)
            is SettingsContract.Intent.ShareApp -> emitEffect(SettingsContract.Effect.LaunchShareSheet)
            is SettingsContract.Intent.ContactSupport -> emitEffect(SettingsContract.Effect.OpenEmailClient)
        }
    }

    private fun applySettingsToState(settings: AppSettings) {
        _state.update {
            it.copy(
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
    }

    private fun updateNotifications(enabled: Boolean) = viewModelScope.launch {
        try {
            updateNotificationsEnabledUseCase(enabled)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            val message = e.message?.let { UiText.Raw(it) }
                ?: UiText.Res(R.string.couldn_t_update_notification_settings)
            _state.update { it.copy(errorMessage = message) }
            _effect.emit(SettingsContract.Effect.ShowError(message))
        }
    }

    private fun updateCalculationMethod(method: CalculationMethod) = viewModelScope.launch {
        try {
            updateCalculationMethodUseCase(method)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            val message = e.message?.let { UiText.Raw(it) }
                ?: UiText.Res(R.string.couldn_t_update_calculation_method)
            _state.update { it.copy(errorMessage = message) }
            _effect.emit(SettingsContract.Effect.ShowError(message))
        }
    }

    private fun updateMadhab(madhab: Madhab) = viewModelScope.launch {
        try {
            updateMadhabUseCase(madhab)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            val message = e.message?.let { UiText.Raw(it) }
                ?: UiText.Res(R.string.couldn_t_update_madhab)
            _state.update { it.copy(errorMessage = message) }
            _effect.emit(SettingsContract.Effect.ShowError(message))
        }
    }

    private fun updateTheme(mode: ThemeMode) = viewModelScope.launch {
        try {
            updateThemeModeUseCase(mode)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            val message = e.message?.let { UiText.Raw(it) }
                ?: UiText.Res(R.string.couldn_t_update_theme)
            _state.update { it.copy(errorMessage = message) }
            _effect.emit(SettingsContract.Effect.ShowError(message))
        }
    }

    private fun updateLanguage(language: AppLanguage) = viewModelScope.launch {
        try {
            updateLanguageUseCase(language)
            _effect.emit(SettingsContract.Effect.LanguageChangedRestartRequired)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            val message = e.message?.let { UiText.Raw(it) }
                ?: UiText.Res(R.string.couldn_t_update_language)
            _state.update { it.copy(errorMessage = message) }
            _effect.emit(SettingsContract.Effect.ShowError(message))
        }
    }

    private fun updateAdhanSound(sound: AdhanSound) = viewModelScope.launch {
        try {
            updateAdhanSoundUseCase(sound)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            val message = e.message?.let { UiText.Raw(it) }
                ?: UiText.Res(R.string.couldn_t_update_adhan_sound)
            _state.update { it.copy(errorMessage = message) }
            _effect.emit(SettingsContract.Effect.ShowError(message))
        }
    }

    private fun updateHijriOffset(offset: Int) = viewModelScope.launch {
        val clamped = offset.coerceIn(-2, 2)
        try {
            updateHijriDateOffsetUseCase(clamped)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            val message = e.message?.let { UiText.Raw(it) }
                ?: UiText.Res(R.string.couldn_t_update_hijri_offset)
            _state.update { it.copy(errorMessage = message) }
            _effect.emit(SettingsContract.Effect.ShowError(message))
        }
    }

    private fun emitEffect(effect: SettingsContract.Effect) = viewModelScope.launch {
        _effect.emit(effect)
    }
}

//@HiltViewModel
//class SettingsViewModel @Inject constructor(
//    private val observeSettingsUseCase: ObserveSettingsUseCase,
//    private val updateNotificationsEnabledUseCase: UpdateNotificationsEnabledUseCase,
//    private val updateCalculationMethodUseCase: UpdateCalculationMethodUseCase,
//    private val updateMadhabUseCase: UpdateMadhabUseCase,
//    private val updateThemeModeUseCase: UpdateThemeModeUseCase,
//    private val updateLanguageUseCase: UpdateLanguageUseCase,
//    private val updateAdhanSoundUseCase: UpdateAdhanSoundUseCase,
//    private val updateHijriDateOffsetUseCase: UpdateHijriDateOffsetUseCase
//) : ViewModel() {
//
//    private val _state = MutableStateFlow(SettingsContract.State(appVersion = BuildConfig.VERSION_NAME))
//    val state: StateFlow<SettingsContract.State> = _state.asStateFlow()
//
//    private val _effect = MutableSharedFlow<SettingsContract.Effect>()
//    val effect: SharedFlow<SettingsContract.Effect> = _effect
//
//    init {
//        observeSettingsUseCase()
//            .onEach { settings -> applySettingsToState(settings) }
//            .launchIn(viewModelScope)
//    }
//
//    fun onIntent(intent: SettingsContract.Intent) {
//        when (intent) {
//            is SettingsContract.Intent.ToggleNotifications -> updateNotifications(intent.enabled)
//            is SettingsContract.Intent.SelectCalculationMethod -> updateCalculationMethod(intent.method)
//            is SettingsContract.Intent.SelectMadhab -> updateMadhab(intent.madhab)
//            is SettingsContract.Intent.SelectTheme -> updateTheme(intent.mode)
//            is SettingsContract.Intent.SelectLanguage -> updateLanguage(intent.language)
//            is SettingsContract.Intent.SelectAdhanSound -> updateAdhanSound(intent.sound)
//            is SettingsContract.Intent.IncrementHijriOffset -> updateHijriOffset(_state.value.hijriDateOffset + 1)
//            is SettingsContract.Intent.DecrementHijriOffset -> updateHijriOffset(_state.value.hijriDateOffset - 1)
//            is SettingsContract.Intent.OpenSelector -> _state.value =
//                _state.value.copy(activeSelector = intent.type)
//
//            is SettingsContract.Intent.CloseSelector -> _state.value =
//                _state.value.copy(activeSelector = null)
//
//            is SettingsContract.Intent.RateApp -> emitEffect(SettingsContract.Effect.OpenPlayStoreListing)
//            is SettingsContract.Intent.ShareApp -> emitEffect(SettingsContract.Effect.LaunchShareSheet)
//            is SettingsContract.Intent.ContactSupport -> emitEffect(SettingsContract.Effect.OpenEmailClient)
//        }
//    }
//
//    private fun applySettingsToState(settings: AppSettings) {
//        _state.value = _state.value.copy(
//            isLoading = false,
//            notificationsEnabled = settings.notificationsEnabled,
//            calculationMethod = settings.calculationMethod,
//            madhab = settings.madhab,
//            themeMode = settings.themeMode,
//            language = settings.language,
//            adhanSound = settings.adhanSound,
//            hijriDateOffset = settings.hijriDateOffset
//        )
//    }
//
//    private fun updateNotifications(enabled: Boolean) = viewModelScope.launch {
//        runCatching { updateNotificationsEnabledUseCase(enabled) }
//            .onFailure { throwable ->
//                val message = throwable.message?.let { UiText.Raw(it) }
//                    ?: UiText.Res(R.string.couldn_t_update_notification_settings)
//                viewModelScope.launch {
//                    _effect.emit(SettingsContract.Effect.ShowError(message))
//                }
//            }
//    }
//
//    private fun updateCalculationMethod(method: CalculationMethod) = viewModelScope.launch {
//        runCatching { updateCalculationMethodUseCase(method) }
//            .onFailure { throwable ->
//                val message = throwable.message?.let { UiText.Raw(it) }
//                    ?: UiText.Res(R.string.couldn_t_update_calculation_method)
//                viewModelScope.launch {
//                    _effect.emit(SettingsContract.Effect.ShowError(message))
//                }
//            }
//    }
//
//    private fun updateMadhab(madhab: Madhab) = viewModelScope.launch {
//        runCatching { updateMadhabUseCase(madhab) }
//            .onFailure { throwable ->
//                val message = throwable.message?.let { UiText.Raw(it) }
//                    ?: UiText.Res(R.string.couldn_t_update_madhab)
//                viewModelScope.launch {
//                    _effect.emit(SettingsContract.Effect.ShowError(message))
//                }
//            }
//    }
//
//    private fun updateTheme(mode: ThemeMode) = viewModelScope.launch {
//        runCatching { updateThemeModeUseCase(mode) }
//            .onFailure { throwable ->
//                val message = throwable.message?.let { UiText.Raw(it) }
//                    ?: UiText.Res(R.string.couldn_t_update_theme)
//                viewModelScope.launch {
//                    _effect.emit(SettingsContract.Effect.ShowError(message))
//                }
//            }
//    }
//
//    private fun updateLanguage(language: AppLanguage) = viewModelScope.launch {
//        runCatching { updateLanguageUseCase(language) }
//            .onSuccess { emitEffect(SettingsContract.Effect.LanguageChangedRestartRequired) }
//            .onFailure { throwable ->
//                val message = throwable.message?.let { UiText.Raw(it) }
//                    ?: UiText.Res(R.string.couldn_t_update_language)
//                viewModelScope.launch {
//                    _effect.emit(SettingsContract.Effect.ShowError(message))
//                }
//            }
//    }
//
//    private fun updateAdhanSound(sound: AdhanSound) = viewModelScope.launch {
//        runCatching { updateAdhanSoundUseCase(sound) }
//            .onFailure { throwable ->
//                val message = throwable.message?.let { UiText.Raw(it) }
//                    ?: UiText.Res(R.string.couldn_t_update_adhan_sound)
//                viewModelScope.launch {
//                    _effect.emit(SettingsContract.Effect.ShowError(message))
//                }
//            }
//    }
//
//    private fun updateHijriOffset(offset: Int) {
//        val clamped = offset.coerceIn(-2, 2)
//        viewModelScope.launch {
//            runCatching { updateHijriDateOffsetUseCase(clamped) }
//                .onFailure { throwable ->
//                    val message = throwable.message?.let { UiText.Raw(it) }
//                        ?: UiText.Res(R.string.couldn_t_update_hijri_offset)
//                    viewModelScope.launch {
//                        _effect.emit(SettingsContract.Effect.ShowError(message))
//                    }
//                }
//        }
//    }
//
//    private fun emitEffect(effect: SettingsContract.Effect) =
//        viewModelScope.launch { _effect.emit(effect) }
//}