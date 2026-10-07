package com.mhq.salati.settings.presentation.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.BuildConfig
import com.mhq.salati.R
import com.mhq.salati.permissions.domain.repo.PermissionChecker
import com.mhq.salati.settings.domain.model.AdhanSound
import com.mhq.salati.settings.domain.model.AppLanguage
import com.mhq.salati.settings.domain.model.AppSettings
import com.mhq.salati.settings.domain.model.CalculationMethod
import com.mhq.salati.settings.domain.model.CustomAlarmSound
import com.mhq.salati.settings.domain.model.Madhab
import com.mhq.salati.settings.domain.model.ThemeMode
import com.mhq.salati.settings.domain.usecases.ObserveSettingsUseCase
import com.mhq.salati.settings.domain.usecases.UpdateAdhanSoundUseCase
import com.mhq.salati.settings.domain.usecases.UpdateCalculationMethodUseCase
import com.mhq.salati.settings.domain.usecases.UpdateCustomAlarmSoundUseCase
import com.mhq.salati.settings.domain.usecases.UpdateHijriDateOffsetUseCase
import com.mhq.salati.settings.domain.usecases.UpdateLanguageUseCase
import com.mhq.salati.settings.domain.usecases.UpdateMadhabUseCase
import com.mhq.salati.settings.domain.usecases.UpdateNotificationsEnabledUseCase
import com.mhq.salati.settings.domain.usecases.UpdateThemeModeUseCase
import com.mhq.salati.settings.presentation.contract.SettingsContract
import com.mhq.salati.shared.ui.UiText
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
    private val updateCustomAlarmSoundUseCase: UpdateCustomAlarmSoundUseCase,
    private val updateHijriDateOffsetUseCase: UpdateHijriDateOffsetUseCase,
    private val permissionChecker: PermissionChecker
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

        refreshNotificationPermissionState()
    }

    fun onIntent(intent: SettingsContract.Intent) {
        when (intent) {
            is SettingsContract.Intent.ToggleNotifications -> toggleNotifications(intent.enabled)
            is SettingsContract.Intent.NotificationPermissionResult -> onNotificationPermissionResult(intent.granted)
            is SettingsContract.Intent.RecheckNotificationPermission -> refreshNotificationPermissionState()
            is SettingsContract.Intent.SelectCalculationMethod -> updateCalculationMethod(intent.method)
            is SettingsContract.Intent.SelectMadhab -> updateMadhab(intent.madhab)
            is SettingsContract.Intent.SelectTheme -> updateTheme(intent.mode)
            is SettingsContract.Intent.SelectLanguage -> updateLanguage(intent.language)
            is SettingsContract.Intent.SelectAdhanSound -> updateAdhanSound(intent.sound)
            is SettingsContract.Intent.SelectCustomAlarmSound -> updateCustomAlarmSound(intent.sound)
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
                customAlarmSound = settings.customAlarmSound,
                hijriDateOffset = settings.hijriDateOffset
            )
        }
    }

    private fun refreshNotificationPermissionState() = viewModelScope.launch {
        val granted = permissionChecker.hasNotificationPermission()
        _state.update { it.copy(hasNotificationPermission = granted) }
    }

    // Turning the toggle on when the OS permission isn't granted must NOT persist
    // notificationsEnabled=true yet — that's what caused the toggle to show "on"
    // while notifications were actually blocked. Instead, we ask for the
    // permission first and only persist once we know the real outcome.
    private fun toggleNotifications(enabled: Boolean) {
        if (enabled && !_state.value.hasNotificationPermission) {
            emitEffect(SettingsContract.Effect.RequestNotificationPermission)
            return
        }
        updateNotifications(enabled)
    }

    private fun onNotificationPermissionResult(granted: Boolean) {
        _state.update { it.copy(hasNotificationPermission = granted) }
        // Only persist the app-level flag to true once permission is confirmed.
        // On denial, we deliberately leave notificationsEnabled untouched in
        // DataStore and let the UI derive "off" from hasNotificationPermission,
        // so the row doesn't silently flip user intent behind their back.
        if (granted) {
            updateNotifications(true)
        }
    }

    private fun launchUpdate(@StringRes errorRes: Int, block: suspend () -> Unit) =
        viewModelScope.launch {
            try {
                block()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                val message = UiText.Res(errorRes)
                _state.update { it.copy(errorMessage = message) }
                _effect.emit(SettingsContract.Effect.ShowError(message))
            }
        }

    private fun updateNotifications(enabled: Boolean) =
        launchUpdate(R.string.couldn_t_update_notification_settings) {
            updateNotificationsEnabledUseCase(enabled)
        }

    private fun updateCalculationMethod(method: CalculationMethod) =
        launchUpdate(R.string.couldn_t_update_calculation_method) {
            updateCalculationMethodUseCase(method)
        }

    private fun updateMadhab(madhab: Madhab) =
        launchUpdate(R.string.couldn_t_update_madhab) { updateMadhabUseCase(madhab) }

    private fun updateTheme(mode: ThemeMode) =
        launchUpdate(R.string.couldn_t_update_theme) { updateThemeModeUseCase(mode) }

    private fun updateLanguage(language: AppLanguage) =
        launchUpdate(R.string.couldn_t_update_language) {
            updateLanguageUseCase(language)
            _effect.emit(SettingsContract.Effect.LanguageChangedRestartRequired(language.code))
        }

    private fun updateAdhanSound(sound: AdhanSound) =
        launchUpdate(R.string.couldn_t_update_adhan_sound) { updateAdhanSoundUseCase(sound) }

    private fun updateCustomAlarmSound(sound: CustomAlarmSound) =
        launchUpdate(R.string.couldn_t_update_custom_alarm_sound) {
            updateCustomAlarmSoundUseCase(sound)
        }

    private fun updateHijriOffset(offset: Int) =
        launchUpdate(R.string.couldn_t_update_hijri_offset) {
            updateHijriDateOffsetUseCase(offset.coerceIn(-2, 2))
        }

    private fun emitEffect(effect: SettingsContract.Effect) =
        viewModelScope.launch {
            _effect.emit(effect)
        }
}