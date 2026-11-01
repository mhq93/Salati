package com.mhq.salati.home.presentation.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.home.domain.service.DayNavigator
import com.mhq.salati.adhan.domain.model.AlarmPermissions
import com.mhq.salati.home.domain.model.PrayerTimesError
import com.mhq.salati.home.domain.model.PrayerTimesLoad
import com.mhq.salati.home.domain.model.PrayerTimesTick
import com.mhq.salati.home.domain.model.PrayerWindow
import com.mhq.salati.home.presentation.contract.HomeContract
import com.mhq.salati.home.presentation.handlers.HomeAlarmsHandler
import com.mhq.salati.home.presentation.handlers.HomeLocationHandler
import com.mhq.salati.home.presentation.handlers.HomeLocationLoadingHandler
import com.mhq.salati.home.presentation.handlers.MutedPrayersHandler
import com.mhq.salati.home.presentation.handlers.PrayerTimesHandler
import com.mhq.salati.home.presentation.mapper.toUi
import com.mhq.salati.permissions.presentation.LocationPermissionEffect
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.settings.domain.usecases.ObserveTimeFormatUseCase
import com.mhq.salati.settings.presentation.mapper.toUi
import com.mhq.salati.shared.domain.Coordinates
import com.mhq.salati.shared.presentation.mapper.toDomain
import com.mhq.salati.shared.presentation.mapper.toUi
import com.mhq.salati.shared.presentation.mapper.toUiSet
import com.mhq.salati.shared.presentation.model.MessageUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dayNavigator: DayNavigator,
    private val locationHandler: HomeLocationHandler,
    private val prayerTimesHandler: PrayerTimesHandler,
    private val mutedPrayersHandler: MutedPrayersHandler,
    private val alarmsHandler: HomeAlarmsHandler,
    private val observeTimeFormatUseCase: ObserveTimeFormatUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeContract.State())
    val state: StateFlow<HomeContract.State> = _state.asStateFlow()

    private val _effect = Channel<HomeContract.Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    private var loadPrayerTimesJob: Job? = null
    private var countdownJob: Job? = null
    private var mutedPrayersJob: Job? = null

    // The domain timings the alarm handler needs. The state only holds the screen's copy (PrayerTimingsUi).
    private var loadedTimings: PrayerTimings? = null

    init {
        viewModelScope.launch {
            locationHandler.permissionEffects.collect { permissionEffect ->
                val mapped = when (permissionEffect) {
                    is LocationPermissionEffect.RequestPermission ->
                        HomeContract.Effect.RequestLocationPermission
                    is LocationPermissionEffect.NavigateToAppSettings ->
                        HomeContract.Effect.NavigateToAppSettings
                    is LocationPermissionEffect.NavigateToLocationSettings ->
                        HomeContract.Effect.NavigateToLocationSettings
                    is LocationPermissionEffect.PermissionResolved -> null
                }
                mapped?.let { _effect.send(it) }
            }
        }

        viewModelScope.launch {
            locationHandler.permissionState.collect { ps ->
                _state.update {
                    it.copy(
                        location = it.location.copy(
                            isPermissionGranted = ps.granted,
                            isPermanentlyDenied = ps.permanentlyDenied,
                            areServicesDisabled = ps.servicesDisabled,
                            isPermissionRequired = ps.required
                        )
                    )
                }
            }
        }

        observeMutedPrayersForCurrentDate()

        locationHandler.localizedNameChanges
            .onEach { name ->
                _state.update { it.copy(location = it.location.copy(locationName = name)) }
            }
            .launchIn(viewModelScope)

        observeTimeFormatUseCase()
            .onEach { format -> _state.update { it.copy(timeFormat = format.toUi()) } }
            .launchIn(viewModelScope)
    }

    fun onIntent(intent: HomeContract.Intent) {
        when (intent) {
            is HomeContract.Intent.LoadPrayerTimes -> checkPermissionAndLoad()
            is HomeContract.Intent.RetryRequested -> {
                locationHandler.allowPermissionPromptAgain()
                checkPermissionAndLoad()
            }
            is HomeContract.Intent.PreviousDay -> {
                dayNavigator.previousOrNull(currentDate())?.let { browseDate(it) }
            }
            is HomeContract.Intent.NextDay -> browseDate(dayNavigator.next(currentDate()))
            is HomeContract.Intent.LocationPermissionGranted -> {
                viewModelScope.launch { locationHandler.onPermissionGranted() }
                loadPrayerTimes()
            }
            is HomeContract.Intent.LocationPermissionDenied -> {
                viewModelScope.launch {
                    locationHandler.onPermissionDenied(intent.permanentlyDenied)
                }
                _state.update {
                    it.copy(
                        errorMessage = if (intent.permanentlyDenied) {
                            MessageUi.LOCATION_PERMISSION_PERMANENTLY_DENIED
                        } else {
                            MessageUi.LOCATION_PERMISSION_IS_REQUIRED_TO_SHOW_PRAYER_TIMES
                        }
                    )
                }
            }
            is HomeContract.Intent.AccessAppSettings -> {
                viewModelScope.launch { locationHandler.openAppSettings() }
            }
            is HomeContract.Intent.AccessDeviceLocationSettings -> {
                viewModelScope.launch { locationHandler.openLocationSettings() }
            }
            is HomeContract.Intent.ToggleMute -> {
                viewModelScope.launch { mutedPrayersHandler.toggle(currentDate(), intent.prayerName.toDomain()) }
            }
            is HomeContract.Intent.RecheckSystemPermissions -> {
                viewModelScope.launch { recheckAlarmPermissions() }
            }
            is HomeContract.Intent.NotificationPermissionResult -> {
                _state.update {
                    it.copy(
                        systemPermissions = it.systemPermissions.copy(
                            hasNotification = intent.granted
                        )
                    )
                }
            }
            is HomeContract.Intent.ExactAlarmPermissionRequested -> {
                viewModelScope.launch {
                    _effect.send(HomeContract.Effect.RequestExactAlarmPermission)
                }
            }
            is HomeContract.Intent.NotificationPermissionRequested -> {
                viewModelScope.launch {
                    _effect.send(HomeContract.Effect.RequestNotificationPermission)
                }
            }
            is HomeContract.Intent.ScreenResumed -> {
                viewModelScope.launch {
                    val currentLocationState = _state.value.location
                    if (currentLocationState.isPermanentlyDenied || currentLocationState.areServicesDisabled) {
                        locationHandler.recheckPermissions()
                        val freshState = locationHandler.permissionState.value
                        if (freshState.permanentlyDenied || freshState.servicesDisabled) {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    location = it.location.copy(
                                        isPermanentlyDenied = freshState.permanentlyDenied,
                                        areServicesDisabled = freshState.servicesDisabled,
                                        isPermissionRequired = freshState.required
                                    )
                                )
                            }
                            return@launch
                        }
                    }
                    recheckAlarmPermissions()
                    if (_state.value.prayerTimes.timings == null) {
                        checkPermissionAndLoad()
                    }
                }
            }
            is HomeContract.Intent.LocationServicesToggled -> {
                if (intent.enabled && _state.value.location.areServicesDisabled) {
                    checkPermissionAndLoad()
                }
            }
            is HomeContract.Intent.ConnectivityChanged -> {
                if (intent.isConnected && isShowingGenericOfflineError()) {
                    checkPermissionAndLoad()
                }
            }
        }
    }

    // ---------------------------------------------------------------- date browsing

    private fun currentDate(): LocalDate = _state.value.dateBrowser.currentDate

    private fun browseDate(date: LocalDate) {
        _state.update {
            it.copy(
                dateBrowser = it.dateBrowser.copy(
                    currentDate = date,
                    isBrowsingToday = dayNavigator.isToday(date)
                )
            )
        }
        observeMutedPrayersForCurrentDate()
        loadPrayerTimes()
    }

    // ---------------------------------------------------------------- loading

    private fun isShowingGenericOfflineError(): Boolean {
        val s = _state.value
        return s.errorMessage != null &&
                !s.location.isPermissionRequired &&
                !s.location.isPermanentlyDenied &&
                !s.location.areServicesDisabled
    }

    private fun checkPermissionAndLoad() {
        val permissionState = locationHandler.permissionState.value
        if (permissionState.permanentlyDenied) {
            _state.update {
                it.copy(
                    isLoading = false,
                    errorMessage = MessageUi.LOCATION_PERMISSION_PERMANENTLY_DENIED
                )
            }
            return
        }
        if (permissionState.servicesDisabled) {
            _state.update {
                it.copy(
                    isLoading = false,
                    errorMessage = MessageUi.LOCATION_SERVICES_DISABLED
                )
            }
            return
        }

        _state.update { it.copy(errorMessage = null) }
        loadPrayerTimes()
    }

    private fun loadPrayerTimes() {
        loadPrayerTimesJob?.cancel()
        loadPrayerTimesJob = viewModelScope.launch {
            try {
                locationHandler.load().collect { load -> handleLocationLoad(load) }
            } catch (e: SecurityException) {
                locationHandler.requirePermission()
                _state.update { it.copy(isLoading = false, errorMessage = null) }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                val message = MessageUi.FAILED_TO_GET_LOCATION
                _state.update { it.copy(isLoading = false, errorMessage = message) }
                _effect.send(HomeContract.Effect.ShowError(message))
            }
        }
    }

    private suspend fun handleLocationLoad(load: HomeLocationLoadingHandler) {
        when (load) {
            is HomeLocationLoadingHandler.Located -> showLocationAndLoadPrayerTimes(load)
            is HomeLocationLoadingHandler.LookingUpCurrentLocation ->
                _state.update { it.copy(isLoading = true, errorMessage = null) }
            is HomeLocationLoadingHandler.PermissionPermanentlyDenied ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = MessageUi.LOCATION_PERMISSION_PERMANENTLY_DENIED
                    )
                }
            is HomeLocationLoadingHandler.PermissionPrompted ->
                _state.update { it.copy(isLoading = false) }
            is HomeLocationLoadingHandler.PermissionRequired ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = MessageUi.LOCATION_PERMISSION_IS_REQUIRED_TO_SHOW_PRAYER_TIMES
                    )
                }
            is HomeLocationLoadingHandler.ServicesDisabled ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = MessageUi.LOCATION_SERVICES_DISABLED,
                        location = it.location.copy(areServicesDisabled = true)
                    )
                }
            is HomeLocationLoadingHandler.NoInternet ->
                showError(MessageUi.NO_INTERNET_CONNECTION, isOffline = true)
            is HomeLocationLoadingHandler.GeocodingFailed ->
                showError(MessageUi.FAILED_TO_GET_LOCATION_NAME, isOffline = false)
            is HomeLocationLoadingHandler.Unavailable ->
                showError(MessageUi.FAILED_TO_GET_LOCATION, isOffline = false)
        }
    }

    // Shows the place right away with the names it already has, then localizes them while the times load.
    private suspend fun showLocationAndLoadPrayerTimes(located: HomeLocationLoadingHandler.Located) = coroutineScope {
        val location = located.location
        _state.update {
            it.copy(
                location = it.location.copy(
                    locationName = location.displayName // instant fallback, closes the race window
                )
            )
        }

        val nameDeferred = async { locationHandler.localizedName(located) }
        val prayerTimesDeferred = async { loadAndApplyPrayerTimes(location.coordinates) }

        val finalName = nameDeferred.await()
        _state.update {
            it.copy(location = it.location.copy(locationName = finalName))
        }
        prayerTimesDeferred.await()
    }

    // While offline the error is only shown inline: no snackbar, and the location flags stay as they were.
    private suspend fun showError(message: MessageUi, isOffline: Boolean) {
        _state.update { current ->
            current.copy(
                isLoading = false,
                errorMessage = message,
                location = if (isOffline) {
                    current.location
                } else {
                    current.location.copy(
                        isPermissionRequired = false,
                        isPermanentlyDenied = false,
                        areServicesDisabled = false
                    )
                }
            )
        }
        if (!isOffline) {
            _effect.send(HomeContract.Effect.ShowError(message))
        }
    }

    // ---------------------------------------------------------------- prayer times

    private suspend fun loadAndApplyPrayerTimes(coordinates: Coordinates) {
        prayerTimesHandler.load(currentDate(), coordinates).collect { load ->
            when (load) {
                is PrayerTimesLoad.FetchingFromNetwork -> {
                    val isScreenCompletelyEmpty = _state.value.location.locationName == null &&
                            _state.value.prayerTimes.timings == null
                    if (isScreenCompletelyEmpty) {
                        _state.update { it.copy(isLoading = true) }
                    }
                }
                is PrayerTimesLoad.Loaded -> applyLoadedPrayerTimes(load, coordinates)
                is PrayerTimesLoad.Failed -> showPrayerTimesError(load.error)
            }
        }
    }

    private suspend fun applyLoadedPrayerTimes(load: PrayerTimesLoad.Loaded, coordinates: Coordinates) {
        loadedTimings = load.timings
        _state.update {
            it.copy(
                isLoading = false,
                prayerTimes = it.prayerTimes.copy(
                    timings = load.timings.toUi(),
                    date = load.prayerDate.toUi(),
                    nextPrayerName = load.status.window.nextPrayer.toUi(),
                    currentPrayerName = load.status.currentPrayer?.toUi(),
                    pastPrayers = load.status.pastPrayers.toUiSet()
                )
            )
        }
        startCountdown(load.timings, currentDate(), coordinates, load.status.window)
        rescheduleAlarms()
        alarmsHandler.checkPermissionsOnFirstLoad()?.let { permissions ->
            applyAlarmPermissions(permissions, promptIfMissing = true)
        }
    }

    private suspend fun showPrayerTimesError(error: PrayerTimesError) {
        val isOffline = error is PrayerTimesError.Offline
        val message = when (error) {
            is PrayerTimesError.Offline -> MessageUi.NO_INTERNET_CONNECTION
            is PrayerTimesError.TimedOut -> MessageUi.REQUEST_TIMED_OUT_CHECK_CONNECTION
            is PrayerTimesError.Other -> MessageUi.SOMETHING_WENT_WRONG
        }
        _state.update {
            it.copy(
                isLoading = false,
                errorMessage = message,
                location = it.location.copy(
                    isPermissionRequired = false,
                    isPermanentlyDenied = false,
                    areServicesDisabled = false
                )
            )
        }
        if (!isOffline) {
            _effect.send(HomeContract.Effect.ShowError(message))
        }
    }

    private fun startCountdown(
        timings: PrayerTimings,
        date: LocalDate,
        coordinates: Coordinates,
        window: PrayerWindow
    ) {
        countdownJob?.cancel()
        countdownJob = viewModelScope.launch {
            prayerTimesHandler.keepCountingDown(timings, date, coordinates, window).collect { tick ->
                when (tick) {
                    is PrayerTimesTick.Countdown -> _state.update {
                        it.copy(
                            prayerTimes = it.prayerTimes.copy(
                                remaining = tick.countdown.remaining,
                                windowProgress = tick.countdown.progress
                            )
                        )
                    }
                    is PrayerTimesTick.StatusRefreshed -> _state.update {
                        it.copy(
                            prayerTimes = it.prayerTimes.copy(
                                nextPrayerName = tick.status.window.nextPrayer.toUi(),
                                currentPrayerName = tick.status.currentPrayer?.toUi(),
                                pastPrayers = tick.status.pastPrayers.toUiSet()
                            )
                        )
                    }
                    is PrayerTimesTick.DayRolledOver -> browseDate(tick.date)
                }
            }
        }
    }

    // ---------------------------------------------------------------- alarms

    private suspend fun rescheduleAlarms() {
        alarmsHandler.schedule(loadedTimings, currentDate())
    }

    private suspend fun recheckAlarmPermissions() {
        val permissions = alarmsHandler.recheck(loadedTimings, currentDate())
        applyAlarmPermissions(permissions, promptIfMissing = false)
    }

    private suspend fun applyAlarmPermissions(permissions: AlarmPermissions, promptIfMissing: Boolean) {
        _state.update {
            it.copy(
                systemPermissions = it.systemPermissions.copy(
                    hasExactAlarm = permissions.hasExactAlarm,
                    hasNotification = permissions.hasNotification
                )
            )
        }
        if (!promptIfMissing) return
        if (!permissions.hasExactAlarm) _effect.send(HomeContract.Effect.RequestExactAlarmPermission)
        if (!permissions.hasNotification) _effect.send(HomeContract.Effect.RequestNotificationPermission)
    }

    // ---------------------------------------------------------------- muted prayers

    private fun observeMutedPrayersForCurrentDate() {
        mutedPrayersJob?.cancel()
        mutedPrayersJob = viewModelScope.launch {
            mutedPrayersHandler.observe(currentDate()).collect { mutedPrayers ->
                _state.update {
                    it.copy(adhan = it.adhan.copy(mutedPrayers = mutedPrayers.toUiSet()))
                }
                rescheduleAlarms()
            }
        }
    }

    @SuppressLint("EmptySuperCall")
    override fun onCleared() {
        countdownJob?.cancel()
        super.onCleared()
    }
}