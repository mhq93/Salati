package com.mhq.salati.onboarding.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.onboarding.domain.usecases.CheckOnboardingPermissionsUseCase
import com.mhq.salati.onboarding.domain.usecases.CompleteOnboardingUseCase
import com.mhq.salati.onboarding.presentation.contract.OnboardingContract
import com.mhq.salati.onboarding.presentation.contract.OnboardingContract.Effect
import com.mhq.salati.onboarding.presentation.contract.OnboardingContract.Intent
import com.mhq.salati.onboarding.presentation.contract.OnboardingContract.PrimaryActionUi
import com.mhq.salati.onboarding.presentation.contract.OnboardingContract.StepUi
import com.mhq.salati.settings.domain.model.AppSettings
import com.mhq.salati.settings.domain.usecases.ObserveSettingsUseCase
import com.mhq.salati.settings.domain.usecases.UpdateAdhanSoundUseCase
import com.mhq.salati.settings.domain.usecases.UpdateCalculationMethodUseCase
import com.mhq.salati.settings.domain.usecases.UpdateLanguageUseCase
import com.mhq.salati.settings.domain.usecases.UpdateMadhabUseCase
import com.mhq.salati.settings.domain.usecases.UpdateNotificationsEnabledUseCase
import com.mhq.salati.settings.domain.usecases.UpdateThemeModeUseCase
import com.mhq.salati.settings.domain.usecases.UpdateTimeFormatUseCase
import com.mhq.salati.settings.presentation.mapper.toDomain
import com.mhq.salati.settings.presentation.mapper.toUi
import com.mhq.salati.settings.presentation.model.AdhanSoundUi
import com.mhq.salati.settings.presentation.model.AppLanguageUi
import com.mhq.salati.settings.presentation.model.CalculationMethodUi
import com.mhq.salati.settings.presentation.model.MadhabUi
import com.mhq.salati.settings.presentation.model.ThemeModeUi
import com.mhq.salati.settings.presentation.model.TimeFormatUi
import com.mhq.salati.shared.presentation.model.MessageUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    observeSettingsUseCase: ObserveSettingsUseCase,
    private val updateLanguageUseCase: UpdateLanguageUseCase,
    private val updateThemeModeUseCase: UpdateThemeModeUseCase,
    private val updateTimeFormatUseCase: UpdateTimeFormatUseCase,
    private val updateCalculationMethodUseCase: UpdateCalculationMethodUseCase,
    private val updateMadhabUseCase: UpdateMadhabUseCase,
    private val updateAdhanSoundUseCase: UpdateAdhanSoundUseCase,
    private val updateNotificationsEnabledUseCase: UpdateNotificationsEnabledUseCase,
    private val checkOnboardingPermissionsUseCase: CheckOnboardingPermissionsUseCase,
    private val completeOnboardingUseCase: CompleteOnboardingUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(OnboardingContract.State())
    val state = _state.asStateFlow()

    private val _effect = Channel<Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        // Seeding from settings keeps the defaults honest (e.g. a theme chosen
        // before a process death), and echoes every live change back idempotently:
        // the flow re-emits exactly the value the user just picked.
        observeSettingsUseCase()
            .onEach { settings -> applySettingsToState(settings) }
            .launchIn(viewModelScope)

        refreshPermissions()
    }

    fun onIntent(intent: Intent) {
        when (intent) {
            is Intent.ScreenResumed -> refreshPermissions()
            is Intent.InternetStatusChanged ->
                _state.update { it.copy(isInternetConnected = intent.connected) }
            is Intent.LocationServicesStatusChanged ->
                _state.update { it.copy(areLocationServicesEnabled = intent.enabled) }
            is Intent.LocationPermissionResult -> _state.update {
                it.copy(
                    isLocationPermissionGranted = intent.granted,
                    isLocationPermanentlyDenied = intent.permanentlyDenied
                )
            }
            is Intent.LanguageSelected -> selectLanguage(intent.language)
            is Intent.ThemeSelected -> selectTheme(intent.mode)
            is Intent.TimeFormatSelected -> selectTimeFormat(intent.format)
            is Intent.CalculationMethodSelected -> selectCalculationMethod(intent.method)
            is Intent.MadhabSelected -> selectMadhab(intent.madhab)
            is Intent.AdhanSoundSelected -> selectAdhanSound(intent.sound)
            is Intent.NotificationsToggled -> toggleNotifications(intent.enabled)
            is Intent.NotificationPermissionResult -> onNotificationPermissionResult(intent.granted, intent.permanentlyDenied)
            is Intent.InternetActionRequested -> onInternetActionRequested()
            is Intent.LocationActionRequested -> onLocationActionRequested()
            is Intent.PrimaryActionRequested -> performPrimaryAction()
            is Intent.Skipped -> skipStep()
            is Intent.BackRequested -> goBack()
        }
    }

    private fun applySettingsToState(settings: AppSettings) {
        _state.update {
            it.copy(
                language = settings.language.toUi(),
                themeMode = settings.themeMode.toUi(),
                timeFormat = settings.timeFormat.toUi(),
                calculationMethod = settings.calculationMethod.toUi(),
                madhab = settings.madhab.toUi(),
                adhanSound = settings.adhanSound.toUi(),
                notificationsEnabled = settings.notificationsEnabled
            )
        }
    }

    // ---- Live choices ------------------------------------------------------

    private fun selectLanguage(language: AppLanguageUi) {
        _state.update { it.copy(language = language) }
        viewModelScope.launch {
            try {
                val domainLanguage = language.toDomain()
                updateLanguageUseCase(domainLanguage)
                // Applying the locale recreates the Activity. This ViewModel —
                // and with it the whole onboarding state — survives recreation,
                // so the flow resumes exactly where the user left off.
                _effect.send(Effect.ApplyLanguage(domainLanguage.code))
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _effect.send(Effect.ShowError(MessageUi.COULDNT_UPDATE_LANGUAGE))
            }
        }
    }

    private fun selectTheme(mode: ThemeModeUi) {
        _state.update { it.copy(themeMode = mode) }
        persistChoice(MessageUi.COULDNT_UPDATE_THEME) { updateThemeModeUseCase(mode.toDomain()) }
    }

    private fun selectTimeFormat(format: TimeFormatUi) {
        _state.update { it.copy(timeFormat = format) }
        persistChoice(MessageUi.COULDNT_UPDATE_TIME_FORMAT) {
            updateTimeFormatUseCase(format.toDomain())
        }
    }

    private fun selectCalculationMethod(method: CalculationMethodUi) {
        _state.update { it.copy(calculationMethod = method) }
        persistChoice(MessageUi.COULDNT_UPDATE_CALCULATION_METHOD) {
            updateCalculationMethodUseCase(method.toDomain())
        }
    }

    private fun selectMadhab(madhab: MadhabUi) {
        _state.update { it.copy(madhab = madhab) }
        persistChoice(MessageUi.COULDNT_UPDATE_MADHAB) { updateMadhabUseCase(madhab.toDomain()) }
    }

    private fun selectAdhanSound(sound: AdhanSoundUi) {
        _state.update { it.copy(adhanSound = sound) }
        persistChoice(MessageUi.COULDNT_UPDATE_ADHAN_SOUND) { updateAdhanSoundUseCase(sound.toDomain()) }
    }

    /** Persists a live choice; a failure shows a message but never blocks the flow. */
    private fun persistChoice(message: MessageUi, block: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                block()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _effect.send(Effect.ShowError(message))
            }
        }
    }

    // ---- Connect row actions (each row owns its button) ---------------------

    private fun onInternetActionRequested() {
        if (!_state.value.isInternetConnected) {
            emitEffect(Effect.OpenInternetSettings)
        }
    }

    private fun onLocationActionRequested() {
        val current = _state.value
        val effect = when {
            current.isLocationReady -> null
            !current.areLocationServicesEnabled -> Effect.OpenLocationSettings
            current.isLocationPermanentlyDenied -> Effect.OpenAppSettings
            else -> Effect.RequestLocationPermission
        }
        effect?.let { emitEffect(it) }
    }

    // ---- Permissions --------------------------------------------------------

    private fun refreshPermissions() {
        val permissions = checkOnboardingPermissionsUseCase()
        _state.update {
            it.copy(
                isLocationPermissionGranted = permissions.hasLocation,
                isLocationPermanentlyDenied = it.isLocationPermanentlyDenied && !permissions.hasLocation,
                isNotificationPermissionGranted = permissions.hasNotification,
                isNotificationPermanentlyDenied = it.isNotificationPermanentlyDenied && !permissions.hasNotification,
                isExactAlarmGranted = permissions.hasExactAlarm
            )
        }
    }

    /**
     * Turning on without the OS permission only asks for it; the choice is persisted once
     * the permission is confirmed (same rule as the Settings screen), so state and storage
     * never disagree.
     */
    private fun toggleNotifications(enabled: Boolean) {
        if (enabled && !_state.value.isNotificationPermissionGranted) {
            viewModelScope.launch { requestMissingAlarmAccess() }
            return
        }
        setNotificationsEnabled(enabled)
    }

    private fun onNotificationPermissionResult(granted: Boolean, permanentlyDenied: Boolean) {
        _state.update {
            it.copy(
                isNotificationPermissionGranted = granted,
                isNotificationPermanentlyDenied = permanentlyDenied && !granted
            )
        }
        if (granted) setNotificationsEnabled(true)
    }

    private fun setNotificationsEnabled(enabled: Boolean) {
        _state.update { it.copy(notificationsEnabled = enabled) }
        persistChoice(MessageUi.COULDNT_UPDATE_NOTIFICATION_SETTINGS) {
            updateNotificationsEnabledUseCase(enabled)
        }
    }

    /** Asks for notifications first, then exact alarms; a permanently denied permission opens app settings. */
    private suspend fun requestMissingAlarmAccess() {
        val current = _state.value
        when {
            !current.isNotificationPermissionGranted -> _effect.send(
                if (current.isNotificationPermanentlyDenied) {
                    Effect.OpenAppSettings
                } else {
                    Effect.RequestNotificationPermission
                }
            )
            !current.isExactAlarmGranted -> _effect.send(Effect.RequestExactAlarmPermission)
        }
    }

    // ---- Navigation ---------------------------------------------------------

    private fun performPrimaryAction() {
        viewModelScope.launch {
            when (_state.value.primaryAction) {
                PrimaryActionUi.CONTINUE -> advance()
                PrimaryActionUi.GET_STARTED -> finish()
                PrimaryActionUi.ALLOW_NOTIFICATIONS,
                PrimaryActionUi.OPEN_APP_SETTINGS,
                PrimaryActionUi.ALLOW_EXACT_ALARMS -> requestMissingAlarmAccess()
            }
        }
    }

    private fun skipStep() {
        if (_state.value.step == StepUi.ALERTS) {
            finish()
        } else {
            advance()
        }
    }

    private fun advance() {
        _state.update {
            val next = StepUi.entries.getOrNull(it.step.ordinal + 1) ?: return@update it
            it.copy(step = next, isMovingForward = true)
        }
    }

    private fun goBack() {
        _state.update {
            val previous = StepUi.entries.getOrNull(it.step.ordinal - 1) ?: return@update it
            it.copy(step = previous, isMovingForward = false)
        }
    }

    private fun finish() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                completeOnboardingUseCase()
                _effect.send(Effect.NavigateToHome)
            } catch (e: CancellationException) {
                _state.update { it.copy(isLoading = false) }
                throw e
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false) }
                _effect.send(Effect.ShowError(MessageUi.SOMETHING_WENT_WRONG))
            }
        }
    }

    private fun emitEffect(effect: Effect) {
        viewModelScope.launch { _effect.send(effect) }
    }
}