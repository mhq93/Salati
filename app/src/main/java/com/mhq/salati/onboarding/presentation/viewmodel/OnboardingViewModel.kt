package com.mhq.salati.onboarding.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.location.domain.usecases.GetSavedLocationUseCase
import com.mhq.salati.location.presentation.handlers.LocationPrerequisitesHandler
import com.mhq.salati.onboarding.domain.usecases.CheckOnboardingPermissionsUseCase
import com.mhq.salati.onboarding.domain.usecases.CompleteOnboardingUseCase
import com.mhq.salati.onboarding.domain.usecases.PrepareFirstLaunchUseCase
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
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
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
    private val prerequisitesHandler: LocationPrerequisitesHandler,
    private val getSavedLocationUseCase: GetSavedLocationUseCase,
    private val prepareFirstLaunchUseCase: PrepareFirstLaunchUseCase,
    private val completeOnboardingUseCase: CompleteOnboardingUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(OnboardingContract.State())
    val state = _state.asStateFlow()

    private val _effect = Channel<Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    private var locationStatusJob: Job? = null

    /** Last value the UI reported: the system would still show its dialog for the location permission. */
    private var showLocationRationale = false

    init {
        observeSettingsUseCase()
            .onEach { settings -> applySettingsToState(settings) }
            .launchIn(viewModelScope)

        refreshPermissions()
        refreshLocationStatus()
    }

    fun onIntent(intent: Intent) {
        when (intent) {
            is Intent.ScreenResumed -> {
                showLocationRationale = intent.showLocationRationale
                refreshPermissions()
                refreshLocationStatus()
            }

            is Intent.LanguageSelected -> selectLanguage(intent.language)
            is Intent.ThemeSelected -> selectTheme(intent.mode)
            is Intent.TimeFormatSelected -> selectTimeFormat(intent.format)
            is Intent.CalculationMethodSelected -> selectCalculationMethod(intent.method)
            is Intent.MadhabSelected -> selectMadhab(intent.madhab)
            is Intent.AdhanSoundSelected -> selectAdhanSound(intent.sound)
            is Intent.NotificationsToggled -> toggleNotifications(intent.enabled)
            is Intent.NotificationPermissionResult -> onNotificationPermissionResult(intent.granted, intent.permanentlyDenied)

            is Intent.LocationPermissionRequested -> viewModelScope.launch { requestLocationPermission() }
            is Intent.LocationPermissionGranted -> refreshLocationStatus()
            is Intent.LocationPermissionDenied -> {
                showLocationRationale = intent.showRationale
                refreshLocationStatus()
            }

            is Intent.LocationServicesToggled -> refreshLocationStatus()
            is Intent.ConnectivityChanged -> refreshLocationStatus()
            is Intent.AppSettingsRequested -> sendEffect(Effect.OpenAppSettings)
            is Intent.LocationSettingsRequested -> sendEffect(Effect.OpenLocationSettings)
            is Intent.InternetSettingsRequested -> sendEffect(Effect.OpenInternetSettings)
            is Intent.LocationPickerRequested -> sendEffect(Effect.NavigateToLocationPicker)

            is Intent.PrimaryActionRequested -> performPrimaryAction()
            is Intent.Skipped -> skipStep()
            is Intent.BackRequested -> goBack()
        }
    }

    private fun sendEffect(effect: Effect) {
        viewModelScope.launch { _effect.send(effect) }
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

    // ---- Permissions --------------------------------------------------------

    private fun refreshPermissions() {
        val permissions = checkOnboardingPermissionsUseCase()
        _state.update {
            it.copy(
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

    // ---- Location step ------------------------------------------------------

    /**
     * Re-reads the three requirements and the saved place. Only the latest check counts, so a slow
     * one can never overwrite a newer result.
     */
    private fun refreshLocationStatus() {
        locationStatusJob?.cancel()
        locationStatusJob = viewModelScope.launch {
            val saved = getSavedLocationUseCase().first()
            val prerequisites = prerequisitesHandler.current(showLocationRationale)
            _state.update {
                it.copy(
                    prerequisites = prerequisites,
                    hasSavedPlace = saved != null,
                    savedPlaceName = saved?.displayName
                )
            }
        }
    }

    /** Remembers the dialog was asked for, so a later silent refusal reads as permanent. */
    private suspend fun requestLocationPermission() {
        prerequisitesHandler.onPermissionRequested()
        _effect.send(Effect.RequestLocationPermission)
    }

    // ---- Navigation ---------------------------------------------------------

    private fun performPrimaryAction() {
        viewModelScope.launch {
            when (_state.value.primaryAction) {
                PrimaryActionUi.CONTINUE -> advance()
                PrimaryActionUi.GET_STARTED -> finish(prepare = true)
                PrimaryActionUi.ALLOW_NOTIFICATIONS,
                PrimaryActionUi.ALLOW_EXACT_ALARMS -> requestMissingAlarmAccess()

                PrimaryActionUi.OPEN_APP_SETTINGS -> _effect.send(Effect.OpenAppSettings)
                PrimaryActionUi.ALLOW_LOCATION -> requestLocationPermission()
                PrimaryActionUi.ENABLE_LOCATION_SERVICES -> _effect.send(Effect.OpenLocationSettings)
                PrimaryActionUi.CONNECT_INTERNET -> _effect.send(Effect.OpenInternetSettings)
            }
        }
    }

    private fun skipStep() {
        if (_state.value.step == StepUi.LOCATION) {
            // Home shows its own blocked screen for whatever is still missing.
            finish(prepare = false)
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
            if (it.isLoading) return@update it
            val previous = StepUi.entries.getOrNull(it.step.ordinal - 1) ?: return@update it
            it.copy(step = previous, isMovingForward = false)
        }
    }

    /** Warms up the first launch when asked, marks onboarding done, then leaves for Home. */
    private fun finish(prepare: Boolean) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, isPreparing = prepare) }
            try {
                if (prepare) prepareFirstLaunchUseCase()
                completeOnboardingUseCase()
                _effect.send(Effect.NavigateToHome)
            } catch (e: CancellationException) {
                _state.update { it.copy(isLoading = false, isPreparing = false) }
                throw e
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, isPreparing = false) }
                _effect.send(Effect.ShowError(MessageUi.SOMETHING_WENT_WRONG))
            }
        }
    }
}