package com.mhq.salati.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.home.domain.model.PrayerTimesError
import com.mhq.salati.home.domain.model.PrayerTimesLoad
import com.mhq.salati.home.domain.model.PrayerTimesTick
import com.mhq.salati.home.domain.model.PrayerWindow
import com.mhq.salati.home.domain.service.DayNavigator
import com.mhq.salati.home.presentation.contract.HomeContract
import com.mhq.salati.home.presentation.handlers.HomeAlarmsHandler
import com.mhq.salati.home.presentation.handlers.HomeLocationHandler
import com.mhq.salati.home.presentation.handlers.HomeLocationLoad
import com.mhq.salati.home.presentation.handlers.MutedPrayersHandler
import com.mhq.salati.home.presentation.handlers.PrayerTimesHandler
import com.mhq.salati.home.presentation.mapper.toUi
import com.mhq.salati.location.presentation.handlers.LocationPrerequisitesHandler
import com.mhq.salati.location.presentation.model.PrerequisitesUi
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
    private val prerequisitesHandler: LocationPrerequisitesHandler,
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

    private var loadedTimings: PrayerTimings? = null

    /** Last value the UI reported: the system would still show its dialog for the location permission. */
    private var showLocationRationale = false

    init {
        observeMutedPrayersForCurrentDate()

        locationHandler.localizedNameChanges
            .onEach { name ->
                _state.update { it.copy(locationUiState = it.locationUiState.copy(locationName = name)) }
            }
            .launchIn(viewModelScope)

        observeTimeFormatUseCase()
            .onEach { format -> _state.update { it.copy(timeFormat = format.toUi()) } }
            .launchIn(viewModelScope)
    }

    fun onIntent(intent: HomeContract.Intent) {
        when (intent) {
            is HomeContract.Intent.LoadPrayerTimes -> {
                val current = _state.value
                when {
                    current.prerequisites != null -> reevaluatePrerequisites()
                    current.errorMessage == null -> checkPermissionAndLoad()
                }
            }

            is HomeContract.Intent.RetryRequested -> checkPermissionAndLoad()

            is HomeContract.Intent.PreviousDay -> {
                dayNavigator.previousOrNull(currentDate())?.let { browseDate(it) }
            }

            is HomeContract.Intent.NextDay -> browseDate(dayNavigator.next(currentDate()))
            is HomeContract.Intent.TodayRequested -> {
                if (!dayNavigator.isToday(currentDate())) browseDate(dayNavigator.today())
            }

            is HomeContract.Intent.EndOfMonthRequested ->
                browseDate(dayNavigator.endOfMonth(currentDate()))

            is HomeContract.Intent.LocationPermissionRequested -> {
                viewModelScope.launch {
                    prerequisitesHandler.onPermissionRequested()
                    _effect.send(HomeContract.Effect.RequestLocationPermission)
                }
            }

            is HomeContract.Intent.LocationPermissionGranted -> reevaluatePrerequisites()

            is HomeContract.Intent.LocationPermissionDenied -> {
                showLocationRationale = intent.showRationale
                reevaluatePrerequisites()
            }

            is HomeContract.Intent.AppSettingsRequested ->
                sendEffect(HomeContract.Effect.NavigateToAppSettings)

            is HomeContract.Intent.LocationSettingsRequested ->
                sendEffect(HomeContract.Effect.NavigateToLocationSettings)

            is HomeContract.Intent.InternetSettingsRequested ->
                sendEffect(HomeContract.Effect.NavigateToInternetSettings)

            is HomeContract.Intent.LocationPickerRequested ->
                sendEffect(HomeContract.Effect.NavigateToLocationPicker)

            is HomeContract.Intent.ToggleMute -> {
                viewModelScope.launch {
                    mutedPrayersHandler.toggle(
                        currentDate(),
                        intent.prayerName.toDomain()
                    )
                }
            }

            is HomeContract.Intent.ScreenResumed -> {
                showLocationRationale = intent.showLocationRationale
                val current = _state.value
                viewModelScope.launch {
                    // Exact-alarm access may have been granted in the system settings.
                    alarmsHandler.recheck(loadedTimings, currentDate())

                    when {
                        current.prerequisites != null -> reevaluatePrerequisites()
                        current.prayerTimesUiState.timings == null &&
                                current.errorMessage == null &&
                                !current.isLoading -> checkPermissionAndLoad()
                    }
                }
            }

            is HomeContract.Intent.LocationServicesToggled -> {
                if (_state.value.prerequisites != null) reevaluatePrerequisites()
            }

            is HomeContract.Intent.ConnectivityChanged -> {
                when {
                    _state.value.prerequisites != null -> reevaluatePrerequisites()
                    intent.isConnected && isShowingGenericOfflineError() ->
                        checkPermissionAndLoad(silent = true)
                }
            }
        }
    }

    private fun sendEffect(effect: HomeContract.Effect) {
        viewModelScope.launch { _effect.send(effect) }
    }

    // ---------------------------------------------------------------- date browsing

    private fun currentDate(): LocalDate = _state.value.dateBrowserUiState.currentDate

    private fun browseDate(date: LocalDate) {
        _state.update {
            it.copy(
                dateBrowserUiState = it.dateBrowserUiState.copy(
                    currentDate = date,
                    isBrowsingToday = dayNavigator.isToday(date)
                )
            )
        }
        observeMutedPrayersForCurrentDate()
        loadPrayerTimes()
    }

    // ---------------------------------------------------------------- loading

    // A failed fetch (the blocked screen is handled by [reevaluatePrerequisites]).
    private fun isShowingGenericOfflineError(): Boolean =
        _state.value.errorMessage != null

    private fun checkPermissionAndLoad(silent: Boolean = false) {
        // A silent re-check keeps the current screen until the new result replaces it.
        if (!silent) _state.update { it.copy(errorMessage = null) }
        loadPrayerTimes()
    }

    /**
     * Re-checks what the blocked screen is waiting for. The rows update in place, with no loading
     * state in between; the lookup only restarts once nothing is missing (or a place was picked).
     */
    private fun reevaluatePrerequisites() {
        viewModelScope.launch {
            val blocked = prerequisitesHandler.blocked(showLocationRationale)
            if (blocked == null) checkPermissionAndLoad(silent = true) else showBlocked(blocked)
        }
    }

    private fun showBlocked(prerequisites: PrerequisitesUi) {
        _state.update {
            it.copy(
                isLoading = false,
                errorMessage = null,
                prerequisites = prerequisites
            )
        }
    }

    private fun loadPrayerTimes() {
        loadPrayerTimesJob?.cancel()
        loadPrayerTimesJob = viewModelScope.launch {
            try {
                locationHandler.load().collect { load -> handleLocationLoad(load) }
            } catch (e: SecurityException) {
                reevaluatePrerequisites()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                val message = MessageUi.FAILED_TO_GET_LOCATION
                _state.update { it.copy(isLoading = false, errorMessage = message) }
                _effect.send(HomeContract.Effect.ShowError(message))
            }
        }
    }

    private suspend fun handleLocationLoad(load: HomeLocationLoad) {
        when (load) {
            is HomeLocationLoad.Located -> showLocationAndLoadPrayerTimes(load)

            is HomeLocationLoad.LookingUpCurrentLocation -> {
                // A blocked screen gives way to the spinner; an error screen waits for the result.
                if (_state.value.errorMessage == null) {
                    _state.update { it.copy(isLoading = true, prerequisites = null) }
                }
            }

            is HomeLocationLoad.Blocked ->
                showBlocked(prerequisitesHandler.toUi(load.prerequisites, showLocationRationale))

            is HomeLocationLoad.GeocodingFailed ->
                showError(MessageUi.FAILED_TO_GET_LOCATION_NAME, isOffline = false)

            is HomeLocationLoad.Unavailable ->
                showError(MessageUi.FAILED_TO_GET_LOCATION, isOffline = false)
        }
    }

    private suspend fun showLocationAndLoadPrayerTimes(located: HomeLocationLoad.Located) =
        coroutineScope {
            val location = located.location
            _state.update {
                it.copy(
                    errorMessage = null,
                    prerequisites = null,
                    locationUiState = it.locationUiState.copy(
                        locationName = location.displayName
                    )
                )
            }

            val nameDeferred = async { locationHandler.localizedName(located) }
            val prayerTimesDeferred = async { loadAndApplyPrayerTimes(location.coordinates) }

            val finalName = nameDeferred.await()
            _state.update {
                it.copy(locationUiState = it.locationUiState.copy(locationName = finalName))
            }
            prayerTimesDeferred.await()
        }

    private suspend fun showError(message: MessageUi, isOffline: Boolean) {
        _state.update {
            it.copy(
                isLoading = false,
                prerequisites = null,
                errorMessage = message
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
                    val isScreenCompletelyEmpty =
                        _state.value.locationUiState.locationName == null &&
                                _state.value.prayerTimesUiState.timings == null
                    if (isScreenCompletelyEmpty) {
                        _state.update { it.copy(isLoading = true) }
                    }
                }

                is PrayerTimesLoad.Loaded -> applyLoadedPrayerTimes(load, coordinates)
                is PrayerTimesLoad.Failed -> showPrayerTimesError(load.error)
            }
        }
    }

    private suspend fun applyLoadedPrayerTimes(
        load: PrayerTimesLoad.Loaded,
        coordinates: Coordinates
    ) {
        loadedTimings = load.timings
        _state.update {
            it.copy(
                isLoading = false,
                errorMessage = null,
                prayerTimesUiState = it.prayerTimesUiState.copy(
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
                prerequisites = null,
                errorMessage = message
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
            prayerTimesHandler.keepCountingDown(timings, date, coordinates, window)
                .collect { tick ->
                    when (tick) {
                        is PrayerTimesTick.Countdown -> _state.update {
                            it.copy(
                                prayerTimesUiState = it.prayerTimesUiState.copy(
                                    remaining = tick.countdown.remaining,
                                    windowProgress = tick.countdown.progress
                                )
                            )
                        }

                        is PrayerTimesTick.StatusRefreshed -> _state.update {
                            it.copy(
                                prayerTimesUiState = it.prayerTimesUiState.copy(
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

    // ---------------------------------------------------------------- muted prayers

    private fun observeMutedPrayersForCurrentDate() {
        mutedPrayersJob?.cancel()
        mutedPrayersJob = viewModelScope.launch {
            mutedPrayersHandler.observe(currentDate()).collect { mutedPrayers ->
                _state.update {
                    it.copy(adhanUiState = it.adhanUiState.copy(mutedPrayers = mutedPrayers.toUiSet()))
                }
                rescheduleAlarms()
            }
        }
    }

    override fun onCleared() {
        countdownJob?.cancel()
        super.onCleared()
    }
}