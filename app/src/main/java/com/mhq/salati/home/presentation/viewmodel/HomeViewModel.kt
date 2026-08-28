package com.mhq.salati.home.presentation.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.R
import com.mhq.salati.adhan.domain.repo.MutedPrayersRepository
import com.mhq.salati.adhan.domain.usecases.ObserveAdhanPlaybackStateUseCase
import com.mhq.salati.adhan.domain.usecases.StopAdhanPlaybackUseCase
import com.mhq.salati.adhan.domain.usecases.ToggleMutePrayerUseCase
import com.mhq.salati.connectivity.domain.repo.ConnectivityChecker
import com.mhq.salati.home.domain.PrayerAlarmCoordinator
import com.mhq.salati.home.domain.usecases.CalculateCurrentPrayerNameUseCase
import com.mhq.salati.home.domain.usecases.CalculateNextPrayerInfoUseCase
import com.mhq.salati.home.domain.usecases.CalculatePastPrayersUseCase
import com.mhq.salati.home.domain.usecases.ResolveLocationUseCase
import com.mhq.salati.home.domain.usecases.ResolveNextPrayerInfoForDisplayUseCase
import com.mhq.salati.home.presentation.contract.HomeContract
import com.mhq.salati.location.domain.model.SavedLocation
import com.mhq.salati.location.domain.usecases.GetLocalizedLocationNameUseCase
import com.mhq.salati.location.domain.usecases.GetSavedLocationUseCase
import com.mhq.salati.location.domain.usecases.SaveManualLocationUseCase
import com.mhq.salati.permissions.domain.PermissionChecker
import com.mhq.salati.permissions.location.LocationPermissionDelegate
import com.mhq.salati.permissions.location.LocationPermissionEffect
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.prayertimes.domain.usecases.GetCachedPrayerTimesUseCase
import com.mhq.salati.prayertimes.domain.usecases.GetPrayerTimesUseCase
import com.mhq.salati.settings.domain.usecases.ObserveSettingsUseCase
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.presentation.components.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

private val LOCATION_NAME_LOOKUP_TIMEOUT = 500L.milliseconds

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val clock: Clock,
    private val permissionChecker: PermissionChecker,
    private val connectivityChecker: ConnectivityChecker,
    private val prayerAlarmCoordinator: PrayerAlarmCoordinator,
    private val mutedPrayersRepository: MutedPrayersRepository,
    private val getPrayerTimesUseCase: GetPrayerTimesUseCase,
    private val getSavedLocationUseCase: GetSavedLocationUseCase,
    private val toggleMutePrayerUseCase: ToggleMutePrayerUseCase,
    private val stopAdhanPlaybackUseCase: StopAdhanPlaybackUseCase,
    private val getCachedPrayerTimesUseCase: GetCachedPrayerTimesUseCase,
    private val calculateNextPrayerInfoUseCase: CalculateNextPrayerInfoUseCase,
    private val observeAdhanPlaybackStateUseCase: ObserveAdhanPlaybackStateUseCase,
    private val locationPermissionDelegate: LocationPermissionDelegate,
    private val calculateCurrentPrayerNameUseCase: CalculateCurrentPrayerNameUseCase,
    private val calculatePastPrayersUseCase: CalculatePastPrayersUseCase,
    private val resolveNextPrayerInfoForDisplayUseCase: ResolveNextPrayerInfoForDisplayUseCase,
    private val resolveLocationUseCase: ResolveLocationUseCase,
    private val getLocalizedLocationName: GetLocalizedLocationNameUseCase,
    private val observeSettings: ObserveSettingsUseCase,
    private val saveManualLocationUseCase: SaveManualLocationUseCase
) : ViewModel() {

    private val dateKeyFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.US)
    private var alarmAndNotificationPermissionsChecked = false

    private val _state = MutableStateFlow(HomeContract.State())
    val state: StateFlow<HomeContract.State> = _state.asStateFlow()

    private val _effect = Channel<HomeContract.Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    private var locationPermissionAutoPromptShown = false
    private var loadPrayerTimesJob: Job? = null
    private var tickCounterJob: Job? = null
    private var mutedPrayersJob: Job? = null

    init {
        viewModelScope.launch {
            locationPermissionDelegate.effect.collect { delegateEffect ->
                val mapped = when (delegateEffect) {
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
            locationPermissionDelegate.state.collect { ps ->
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

        viewModelScope.launch { mutedPrayersRepository.purgePastDates() }
        observeMutedPrayersForCurrentDate()

        observeAdhanPlaybackStateUseCase()
            .onEach { playback -> _state.update { it.copy(adhan = it.adhan.copy(playback = playback)) } }
            .launchIn(viewModelScope)
    }

    fun onIntent(intent: HomeContract.Intent) {
        when (intent) {
            is HomeContract.Intent.LoadPrayerTimes -> checkPermissionAndLoad()
            is HomeContract.Intent.Retry -> checkPermissionAndLoad()
            is HomeContract.Intent.RetryClicked -> {
                locationPermissionAutoPromptShown = false
                checkPermissionAndLoad()
            }
            is HomeContract.Intent.PreviousDay -> {
                if (!isBrowsingToday()) {
                    val newDate = _state.value.dateBrowser.currentDate.minusDays(1)
                    _state.update {
                        it.copy(
                            dateBrowser = it.dateBrowser.copy(
                                currentDate = newDate,
                                isBrowsingToday = newDate == clock.today()
                            )
                        )
                    }
                    observeMutedPrayersForCurrentDate()
                    loadPrayerTimes()
                }
            }
            is HomeContract.Intent.NextDay -> {
                val newDate = _state.value.dateBrowser.currentDate.plusDays(1)
                _state.update {
                    it.copy(
                        dateBrowser = it.dateBrowser.copy(
                            currentDate = newDate,
                            isBrowsingToday = newDate == clock.today()
                        )
                    )
                }
                observeMutedPrayersForCurrentDate()
                loadPrayerTimes()
            }
            is HomeContract.Intent.LocationPermissionGranted -> {
                viewModelScope.launch { locationPermissionDelegate.onPermissionGranted() }
                loadPrayerTimes()
            }
            is HomeContract.Intent.LocationPermissionDenied -> {
                viewModelScope.launch {
                    locationPermissionDelegate.onPermissionDenied(intent.permanentlyDenied)
                }
                _state.update {
                    it.copy(
                        errorMessage = if (intent.permanentlyDenied) {
                            UiText.Res(R.string.location_permission_permanently_denied)
                        } else {
                            UiText.Res(R.string.location_permission_is_required_to_show_prayer_times)
                        }
                    )
                }
            }
            is HomeContract.Intent.AccessAppSettings -> {
                viewModelScope.launch { locationPermissionDelegate.requestAppSettings() }
            }
            is HomeContract.Intent.AccessDeviceLocationSettings -> {
                viewModelScope.launch { locationPermissionDelegate.requestLocationSettings() }
            }
            is HomeContract.Intent.ToggleMute -> {
                viewModelScope.launch {
                    val dateKey = _state.value.dateBrowser.currentDate.format(dateKeyFormatter)
                    val currentlyMuted = intent.prayerName in _state.value.adhan.mutedPrayers
                    toggleMutePrayerUseCase(dateKey, intent.prayerName, !currentlyMuted)
                }
            }
            is HomeContract.Intent.RecheckSystemPermissions -> {
                viewModelScope.launch {
                    checkExactAlarmAndNotificationPermissions(promptIfMissing = false)
                    if (_state.value.systemPermissions.hasExactAlarm) {
                        prayerAlarmCoordinator.scheduleIfToday(
                            timings = _state.value.prayerTimes.timings,
                            browsedDate = _state.value.dateBrowser.currentDate
                        )
                    }
                }
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
            is HomeContract.Intent.ExactAlarmBannerClicked -> {
                viewModelScope.launch {
                    _effect.send(HomeContract.Effect.RequestExactAlarmPermission)
                }
            }
            is HomeContract.Intent.NotificationBannerClicked -> {
                viewModelScope.launch {
                    _effect.send(HomeContract.Effect.RequestNotificationPermission)
                }
            }
            is HomeContract.Intent.StopAdhanClicked -> stopAdhanPlaybackUseCase()
            is HomeContract.Intent.ScreenResumed -> {
                viewModelScope.launch {
                    val currentLocationState = _state.value.location
                    if (currentLocationState.isPermanentlyDenied || currentLocationState.areServicesDisabled) {
                        locationPermissionDelegate.reset()
                        val freshState = locationPermissionDelegate.state.value
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
                    checkExactAlarmAndNotificationPermissions(promptIfMissing = false)
                    if (_state.value.systemPermissions.hasExactAlarm) {
                        prayerAlarmCoordinator.scheduleIfToday(
                            timings = _state.value.prayerTimes.timings,
                            browsedDate = _state.value.dateBrowser.currentDate
                        )
                    }
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

    private fun isShowingGenericOfflineError(): Boolean {
        val s = _state.value
        return s.errorMessage != null &&
                !s.location.isPermissionRequired &&
                !s.location.isPermanentlyDenied &&
                !s.location.areServicesDisabled
    }

    private fun checkPermissionAndLoad() {
        val delegateState = locationPermissionDelegate.state.value
        if (delegateState.permanentlyDenied) {
            _state.update {
                it.copy(
                    isLoading = false,
                    errorMessage = UiText.Res(R.string.location_permission_permanently_denied)
                )
            }
            return
        }
        if (delegateState.servicesDisabled) {
            _state.update {
                it.copy(
                    isLoading = false,
                    errorMessage = UiText.Res(R.string.location_services_disabled)
                )
            }
            return
        }
        // BUG (found during offline-support review): this gate ran before the Room cache
        // was ever consulted. On a cold app start while offline, `prayerTimes.timings` is
        // null on a fresh HomeContract.State() regardless of whether Room already has today
        // cached from a prior session — so this unconditionally showed the offline error and
        // never called loadPrayerTimes() -> fetchAndApplyPrayerTimes() -> the cache check.
        // Removed in favor of letting fetchAndApplyPrayerTimes()'s existing cache-first logic
        // decide, with the offline message now surfacing only from its onFailure branch on a
        // genuine cache miss.
        //
        // if (!connectivityChecker.isConnected() && _state.value.prayerTimes.timings == null) {
        //     _state.update {
        //         it.copy(
        //             isLoading = false,
        //             errorMessage = UiText.Res(R.string.no_internet_connection)
        //         )
        //     }
        //     return
        // }
        _state.update { it.copy(errorMessage = null) }
        loadPrayerTimes()
    }

    private suspend fun checkExactAlarmAndNotificationPermissions(promptIfMissing: Boolean) {
        val hasExactAlarm = permissionChecker.canScheduleExactAlarms()
        val hasNotification = permissionChecker.hasNotificationPermission()
        _state.update {
            it.copy(
                systemPermissions = it.systemPermissions.copy(
                    hasExactAlarm = hasExactAlarm,
                    hasNotification = hasNotification
                )
            )
        }
        if (!promptIfMissing) return
        if (!hasExactAlarm) _effect.send(HomeContract.Effect.RequestExactAlarmPermission)
        if (!hasNotification) _effect.send(HomeContract.Effect.RequestNotificationPermission)
    }

    private fun loadPrayerTimes() {
        loadPrayerTimesJob?.cancel()
        loadPrayerTimesJob = viewModelScope.launch {
            try {
                val savedLocation = getSavedLocationUseCase().first()
                if (savedLocation != null) {
                    handleSavedLocation(savedLocation)
                } else {
                    handleFreshLocation()
                }
            } catch (e: SecurityException) {
                locationPermissionDelegate.requirePermission()
                _state.update { it.copy(isLoading = false, errorMessage = null) }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                val message = UiText.Res(R.string.failed_to_get_location)
                _state.update { it.copy(isLoading = false, errorMessage = message) }
                _effect.send(HomeContract.Effect.ShowError(message))
            }
        }
    }

    private suspend fun handleSavedLocation(savedLocation: SavedLocation) {
        val lang = observeSettings().first().language.code
        val isOnline = connectivityChecker.isConnected()

        val finalName = if (lang == "ar") {
            savedLocation.toDisplayName()
        } else if (isOnline) {
            withTimeoutOrNull(LOCATION_NAME_LOOKUP_TIMEOUT) {
                getLocalizedLocationName(savedLocation, lang)
            } ?: savedLocation.toDisplayName()
        } else {
            savedLocation.toDisplayName()
        }

        _state.update {
            it.copy(
                location = it.location.copy(
                    latitude = savedLocation.latitude,
                    longitude = savedLocation.longitude,
                    locationName = finalName
                )
            )
        }

        fetchAndApplyPrayerTimes(savedLocation.latitude, savedLocation.longitude)
    }

    private suspend fun handleFreshLocation() {
        if (locationPermissionDelegate.state.value.permanentlyDenied) {
            _state.update {
                it.copy(
                    isLoading = false,
                    errorMessage = UiText.Res(R.string.location_permission_permanently_denied)
                )
            }
            return
        }

        locationPermissionDelegate.reset()
        _state.update { it.copy(isLoading = true, errorMessage = null) }

        when (val result = resolveLocationUseCase(locationPermissionAutoPromptShown)) {
            is ResolveLocationUseCase.Result.Success -> {
                locationPermissionAutoPromptShown = false

                val lang = observeSettings().first().language.code
                val isOnline = connectivityChecker.isConnected()

                // Localize the location name
                val localizedName = if (lang != "ar" && isOnline) {
                    withTimeoutOrNull(LOCATION_NAME_LOOKUP_TIMEOUT) {
                        getLocalizedLocationName(result.savedLocation, lang)
                    }
                } else {
                    null
                }

                // Use localized name if available, otherwise fall back to saved location name
                val finalName = localizedName ?: result.savedLocation.toDisplayName()

                // If we successfully localized and have a different name, save it
                if (localizedName != null && localizedName != result.savedLocation.toDisplayName()) {
                    val (city, country) = parseDisplayName(localizedName)
                    saveManualLocationUseCase(
                        result.savedLocation.latitude,
                        result.savedLocation.longitude,
                        city,
                        country
                    )
                }

                _state.update {
                    it.copy(
                        location = it.location.copy(
                            latitude = result.savedLocation.latitude,
                            longitude = result.savedLocation.longitude,
                            locationName = finalName
                        )
                    )
                }

                fetchAndApplyPrayerTimes(
                    result.savedLocation.latitude,
                    result.savedLocation.longitude
                )
            }
            is ResolveLocationUseCase.Result.PromptPermission -> {
                locationPermissionAutoPromptShown = true
                locationPermissionDelegate.requirePermission()
                _state.update { it.copy(isLoading = false) }
            }
            is ResolveLocationUseCase.Result.PermissionRequired -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = UiText.Res(R.string.location_permission_is_required_to_show_prayer_times)
                    )
                }
            }
            is ResolveLocationUseCase.Result.PermanentlyDenied -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = UiText.Res(R.string.location_permission_permanently_denied)
                    )
                }
            }
            is ResolveLocationUseCase.Result.ServicesDisabled -> {
                locationPermissionDelegate.markServicesDisabled()
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = UiText.Res(R.string.location_services_disabled),
                        location = it.location.copy(areServicesDisabled = true)
                    )
                }
            }
            is ResolveLocationUseCase.Result.Error -> {
                val isCurrentlyConnected = connectivityChecker.isConnected()
                val isLocationServicesError = result.message == UiText.Res(R.string.location_services_disabled)
                val shouldClearFlags = isCurrentlyConnected && !isLocationServicesError

                _state.update { current ->
                    current.copy(
                        isLoading = false,
                        errorMessage = result.message,
                        location = if (shouldClearFlags) {
                            current.location.copy(
                                isPermissionRequired = false,
                                isPermanentlyDenied = false,
                                areServicesDisabled = false
                            )
                        } else {
                            current.location.copy(
                                areServicesDisabled = current.location.areServicesDisabled || isLocationServicesError
                            )
                        }
                    )
                }
                if (shouldClearFlags) {
                    _effect.send(HomeContract.Effect.ShowError(result.message))
                }
            }
        }
    }

    private fun parseDisplayName(displayName: String?): Pair<String?, String?> {
        if (displayName == null) return null to null
        val parts = displayName.split(", ", limit = 2)
        return when (parts.size) {
            2 -> parts[0] to parts[1]
            1 -> parts[0] to null
            else -> null to null
        }
    }

    private suspend fun fetchAndApplyPrayerTimes(latitude: Double, longitude: Double) {
        val today = _state.value.dateBrowser.currentDate.format(dateKeyFormatter)
        val cached = getCachedPrayerTimesUseCase(today, latitude, longitude)

        if (cached != null) {
            applyLoadedPrayerTimes(cached.timings, cached.date, today, latitude, longitude)
            return
        }

        val isScreenCompletelyEmpty = _state.value.location.locationName == null &&
                _state.value.prayerTimes.timings == null

        if (isScreenCompletelyEmpty) {
            _state.update { it.copy(isLoading = true) }
        }

        val result = withTimeoutOrNull(5_000L.milliseconds) {
            getPrayerTimesUseCase(date = today, latitude = latitude, longitude = longitude)
        } ?: Result.failure(Exception("Request timed out. Check your connection."))

        result.fold(
            onSuccess = { prayerTimesResult ->
                applyLoadedPrayerTimes(
                    prayerTimesResult.timings,
                    prayerTimesResult.date,
                    today,
                    latitude,
                    longitude
                )
            },
            onFailure = { throwable ->
                val isOffline = !connectivityChecker.isConnected()
                val message = if (isOffline) {
                    UiText.Res(R.string.no_internet_connection)
                } else {
                    throwable.message?.let { UiText.Raw(it) }
                        ?: UiText.Res(R.string.something_went_wrong)
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
        )
    }

    private suspend fun applyLoadedPrayerTimes(
        timings: PrayerTimings,
        date: com.mhq.salati.prayertimes.domain.model.PrayerDate,
        today: String,
        latitude: Double,
        longitude: Double
    ) {
        val info = calculateNextPrayerInfoUseCase(timings, today, latitude, longitude)
        val browsingToday = isBrowsingToday()
        _state.update {
            it.copy(
                isLoading = false,
                prayerTimes = it.prayerTimes.copy(
                    timings = timings,
                    date = date,
                    nextPrayerInfo = info,
                    currentPrayerName = if (browsingToday) calculateCurrentPrayerNameUseCase(timings) else null,
                    pastPrayers = if (browsingToday) calculatePastPrayersUseCase(timings) else emptySet()
                )
            )
        }
        startCountdownTicker(info.spanEndMillis)
        prayerAlarmCoordinator.scheduleIfToday(
            timings = timings,
            browsedDate = _state.value.dateBrowser.currentDate
        )
        if (!alarmAndNotificationPermissionsChecked) {
            alarmAndNotificationPermissionsChecked = true
            checkExactAlarmAndNotificationPermissions(promptIfMissing = true)
        }
    }

    private fun SavedLocation.toDisplayName(): String? = when {
        cityName != null && countryName != null -> "$cityName, $countryName"
        cityName != null -> cityName
        countryName != null -> countryName
        else -> null
    }

    private fun startCountdownTicker(spanEndMillis: Long) {
        tickCounterJob?.cancel()
        tickCounterJob = viewModelScope.launch {
            while (isActive) {
                val remaining = (spanEndMillis - System.currentTimeMillis()).coerceAtLeast(0)
                _state.update {
                    it.copy(
                        prayerTimes = it.prayerTimes.copy(remainingMillis = remaining)
                    )
                }
                if (remaining <= 0) {
                    onNextPrayerWindowElapsed()
                    break
                }
                delay(1_000.milliseconds)
            }
        }
    }

    private fun observeMutedPrayersForCurrentDate() {
        mutedPrayersJob?.cancel()
        val dateKey = _state.value.dateBrowser.currentDate.format(dateKeyFormatter)
        mutedPrayersJob = viewModelScope.launch {
            mutedPrayersRepository.observeMutedPrayers(dateKey).collect { mutedPrayers ->
                _state.update {
                    it.copy(
                        adhan = it.adhan.copy(mutedPrayers = mutedPrayers)
                    )
                }
                prayerAlarmCoordinator.scheduleIfToday(
                    timings = _state.value.prayerTimes.timings,
                    browsedDate = _state.value.dateBrowser.currentDate
                )
            }
        }
    }

    private fun onNextPrayerWindowElapsed() {
        if (_state.value.prayerTimes.nextPrayerInfo?.crossesIntoNextDay == true) {
            val newDate = _state.value.dateBrowser.currentDate.plusDays(1)
            _state.update {
                it.copy(
                    dateBrowser = it.dateBrowser.copy(
                        currentDate = newDate,
                        isBrowsingToday = newDate == clock.today()
                    )
                )
            }
            loadPrayerTimes()
        } else {
            viewModelScope.launch { recomputeNextPrayerInfo() }
        }
    }

    private fun isBrowsingToday(): Boolean = _state.value.dateBrowser.currentDate == clock.today()

    private suspend fun recomputeNextPrayerInfo() {
        val timings = _state.value.prayerTimes.timings ?: return
        val latitude = _state.value.location.latitude ?: return
        val longitude = _state.value.location.longitude ?: return

        val info = resolveNextPrayerInfoForDisplayUseCase(
            browsedTimings = timings,
            browsedDate = _state.value.dateBrowser.currentDate,
            latitude = latitude,
            longitude = longitude
        )
        val browsingToday = isBrowsingToday()
        _state.update {
            it.copy(
                prayerTimes = it.prayerTimes.copy(
                    nextPrayerInfo = info,
                    currentPrayerName = if (browsingToday) calculateCurrentPrayerNameUseCase(timings) else null,
                    pastPrayers = if (browsingToday) calculatePastPrayersUseCase(timings) else emptySet()
                )
            )
        }
        startCountdownTicker(info.spanEndMillis)
    }

    @SuppressLint("EmptySuperCall")
    override fun onCleared() {
        tickCounterJob?.cancel()
        super.onCleared()
    }
}

//package com.mhq.salati.home.presentation.viewmodel
//
//import android.annotation.SuppressLint
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.mhq.salati.R
//import com.mhq.salati.adhan.domain.repo.MutedPrayersRepository
//import com.mhq.salati.adhan.domain.usecases.ObserveAdhanPlaybackStateUseCase
//import com.mhq.salati.adhan.domain.usecases.StopAdhanPlaybackUseCase
//import com.mhq.salati.adhan.domain.usecases.ToggleMutePrayerUseCase
//import com.mhq.salati.connectivity.domain.repo.ConnectivityChecker
//import com.mhq.salati.home.domain.PrayerAlarmCoordinator
//import com.mhq.salati.home.domain.usecases.CalculateCurrentPrayerNameUseCase
//import com.mhq.salati.home.domain.usecases.CalculateNextPrayerInfoUseCase
//import com.mhq.salati.home.domain.usecases.CalculatePastPrayersUseCase
//import com.mhq.salati.home.domain.usecases.ResolveLocationUseCase
//import com.mhq.salati.home.domain.usecases.ResolveNextPrayerInfoForDisplayUseCase
//import com.mhq.salati.home.presentation.contract.HomeContract
//import com.mhq.salati.location.domain.model.SavedLocation
//import com.mhq.salati.location.domain.usecases.GetLocalizedLocationNameUseCase
//import com.mhq.salati.location.domain.usecases.GetSavedLocationUseCase
//import com.mhq.salati.location.domain.usecases.SaveManualLocationUseCase
//import com.mhq.salati.permissions.domain.PermissionChecker
//import com.mhq.salati.permissions.location.LocationPermissionDelegate
//import com.mhq.salati.permissions.location.LocationPermissionEffect
//import com.mhq.salati.prayertimes.domain.model.PrayerTimings
//import com.mhq.salati.prayertimes.domain.usecases.GetCachedPrayerTimesUseCase
//import com.mhq.salati.prayertimes.domain.usecases.GetPrayerTimesUseCase
//import com.mhq.salati.settings.domain.usecases.ObserveSettingsUseCase
//import com.mhq.salati.shared.domain.Clock
//import com.mhq.salati.shared.presentation.components.UiText
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.CancellationException
//import kotlinx.coroutines.Job
//import kotlinx.coroutines.channels.Channel
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.flow.launchIn
//import kotlinx.coroutines.flow.onEach
//import kotlinx.coroutines.flow.receiveAsFlow
//import kotlinx.coroutines.flow.update
//import kotlinx.coroutines.isActive
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withTimeoutOrNull
//import java.time.format.DateTimeFormatter
//import java.util.Locale
//import javax.inject.Inject
//import kotlin.time.Duration.Companion.milliseconds
//
//private val LOCATION_NAME_LOOKUP_TIMEOUT = 500L.milliseconds
//
//@HiltViewModel
//class HomeViewModel @Inject constructor(
//    private val clock: Clock,
//    private val permissionChecker: PermissionChecker,
//    private val connectivityChecker: ConnectivityChecker,
//    private val prayerAlarmCoordinator: PrayerAlarmCoordinator,
//    private val mutedPrayersRepository: MutedPrayersRepository,
//    private val getPrayerTimesUseCase: GetPrayerTimesUseCase,
//    private val getSavedLocationUseCase: GetSavedLocationUseCase,
//    private val toggleMutePrayerUseCase: ToggleMutePrayerUseCase,
//    private val stopAdhanPlaybackUseCase: StopAdhanPlaybackUseCase,
//    private val getCachedPrayerTimesUseCase: GetCachedPrayerTimesUseCase,
//    private val calculateNextPrayerInfoUseCase: CalculateNextPrayerInfoUseCase,
//    private val observeAdhanPlaybackStateUseCase: ObserveAdhanPlaybackStateUseCase,
//    private val locationPermissionDelegate: LocationPermissionDelegate,
//    private val calculateCurrentPrayerNameUseCase: CalculateCurrentPrayerNameUseCase,
//    private val calculatePastPrayersUseCase: CalculatePastPrayersUseCase,
//    private val resolveNextPrayerInfoForDisplayUseCase: ResolveNextPrayerInfoForDisplayUseCase,
//    private val resolveLocationUseCase: ResolveLocationUseCase,
//    private val getLocalizedLocationName: GetLocalizedLocationNameUseCase,
//    private val observeSettings: ObserveSettingsUseCase,
//    private val saveManualLocationUseCase: SaveManualLocationUseCase
//) : ViewModel() {
//
//    private val dateKeyFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.US)
//    private var alarmAndNotificationPermissionsChecked = false
//
//    private val _state = MutableStateFlow(HomeContract.State())
//    val state: StateFlow<HomeContract.State> = _state.asStateFlow()
//
//    private val _effect = Channel<HomeContract.Effect>(Channel.BUFFERED)
//    val effect = _effect.receiveAsFlow()
//
//    private var locationPermissionAutoPromptShown = false
//    private var loadPrayerTimesJob: Job? = null
//    private var tickCounterJob: Job? = null
//    private var mutedPrayersJob: Job? = null
//
//    init {
//        viewModelScope.launch {
//            locationPermissionDelegate.effect.collect { delegateEffect ->
//                val mapped = when (delegateEffect) {
//                    is LocationPermissionEffect.RequestPermission ->
//                        HomeContract.Effect.RequestLocationPermission
//                    is LocationPermissionEffect.NavigateToAppSettings ->
//                        HomeContract.Effect.NavigateToAppSettings
//                    is LocationPermissionEffect.NavigateToLocationSettings ->
//                        HomeContract.Effect.NavigateToLocationSettings
//                    is LocationPermissionEffect.PermissionResolved -> null
//                }
//                mapped?.let { _effect.send(it) }
//            }
//        }
//
//        viewModelScope.launch {
//            locationPermissionDelegate.state.collect { ps ->
//                _state.update {
//                    it.copy(
//                        location = it.location.copy(
//                            isPermissionGranted = ps.granted,
//                            isPermanentlyDenied = ps.permanentlyDenied,
//                            areServicesDisabled = ps.servicesDisabled,
//                            isPermissionRequired = ps.required
//                        )
//                    )
//                }
//            }
//        }
//
//        viewModelScope.launch { mutedPrayersRepository.purgePastDates() }
//        observeMutedPrayersForCurrentDate()
//
//        observeAdhanPlaybackStateUseCase()
//            .onEach { playback -> _state.update { it.copy(adhan = it.adhan.copy(playback = playback)) } }
//            .launchIn(viewModelScope)
//    }
//
//    fun onIntent(intent: HomeContract.Intent) {
//        when (intent) {
//            is HomeContract.Intent.LoadPrayerTimes -> checkPermissionAndLoad()
//            is HomeContract.Intent.Retry -> checkPermissionAndLoad()
//            is HomeContract.Intent.RetryClicked -> {
//                locationPermissionAutoPromptShown = false
//                checkPermissionAndLoad()
//            }
//            is HomeContract.Intent.PreviousDay -> {
//                if (!isBrowsingToday()) {
//                    val newDate = _state.value.dateBrowser.currentDate.minusDays(1)
//                    _state.update {
//                        it.copy(
//                            dateBrowser = it.dateBrowser.copy(
//                                currentDate = newDate,
//                                isBrowsingToday = newDate == clock.today()
//                            )
//                        )
//                    }
//                    observeMutedPrayersForCurrentDate()
//                    loadPrayerTimes()
//                }
//            }
//            is HomeContract.Intent.NextDay -> {
//                val newDate = _state.value.dateBrowser.currentDate.plusDays(1)
//                _state.update {
//                    it.copy(
//                        dateBrowser = it.dateBrowser.copy(
//                            currentDate = newDate,
//                            isBrowsingToday = newDate == clock.today()
//                        )
//                    )
//                }
//                observeMutedPrayersForCurrentDate()
//                loadPrayerTimes()
//            }
//            is HomeContract.Intent.LocationPermissionGranted -> {
//                viewModelScope.launch { locationPermissionDelegate.onPermissionGranted() }
//                loadPrayerTimes()
//            }
//            is HomeContract.Intent.LocationPermissionDenied -> {
//                viewModelScope.launch {
//                    locationPermissionDelegate.onPermissionDenied(intent.permanentlyDenied)
//                }
//                _state.update {
//                    it.copy(
//                        errorMessage = if (intent.permanentlyDenied) {
//                            UiText.Res(R.string.location_permission_permanently_denied)
//                        } else {
//                            UiText.Res(R.string.location_permission_is_required_to_show_prayer_times)
//                        }
//                    )
//                }
//            }
//            is HomeContract.Intent.AccessAppSettings -> {
//                viewModelScope.launch { locationPermissionDelegate.requestAppSettings() }
//            }
//            is HomeContract.Intent.AccessDeviceLocationSettings -> {
//                viewModelScope.launch { locationPermissionDelegate.requestLocationSettings() }
//            }
//            is HomeContract.Intent.ToggleMute -> {
//                viewModelScope.launch {
//                    val dateKey = _state.value.dateBrowser.currentDate.format(dateKeyFormatter)
//                    val currentlyMuted = intent.prayerName in _state.value.adhan.mutedPrayers
//                    toggleMutePrayerUseCase(dateKey, intent.prayerName, !currentlyMuted)
//                }
//            }
//            is HomeContract.Intent.RecheckSystemPermissions -> {
//                viewModelScope.launch {
//                    checkExactAlarmAndNotificationPermissions(promptIfMissing = false)
//                    if (_state.value.systemPermissions.hasExactAlarm) {
//                        prayerAlarmCoordinator.scheduleIfToday(
//                            timings = _state.value.prayerTimes.timings,
//                            browsedDate = _state.value.dateBrowser.currentDate
//                        )
//                    }
//                }
//            }
//            is HomeContract.Intent.NotificationPermissionResult -> {
//                _state.update {
//                    it.copy(
//                        systemPermissions = it.systemPermissions.copy(
//                            hasNotification = intent.granted
//                        )
//                    )
//                }
//            }
//            is HomeContract.Intent.ExactAlarmBannerClicked -> {
//                viewModelScope.launch {
//                    _effect.send(HomeContract.Effect.RequestExactAlarmPermission)
//                }
//            }
//            is HomeContract.Intent.NotificationBannerClicked -> {
//                viewModelScope.launch {
//                    _effect.send(HomeContract.Effect.RequestNotificationPermission)
//                }
//            }
//            is HomeContract.Intent.StopAdhanClicked -> stopAdhanPlaybackUseCase()
//            is HomeContract.Intent.ScreenResumed -> {
//                viewModelScope.launch {
//                    val currentLocationState = _state.value.location
//                    if (currentLocationState.isPermanentlyDenied || currentLocationState.areServicesDisabled) {
//                        locationPermissionDelegate.reset()
//                        val freshState = locationPermissionDelegate.state.value
//                        if (freshState.permanentlyDenied || freshState.servicesDisabled) {
//                            _state.update {
//                                it.copy(
//                                    isLoading = false,
//                                    location = it.location.copy(
//                                        isPermanentlyDenied = freshState.permanentlyDenied,
//                                        areServicesDisabled = freshState.servicesDisabled,
//                                        isPermissionRequired = freshState.required
//                                    )
//                                )
//                            }
//                            return@launch
//                        }
//                    }
//                    checkExactAlarmAndNotificationPermissions(promptIfMissing = false)
//                    if (_state.value.systemPermissions.hasExactAlarm) {
//                        prayerAlarmCoordinator.scheduleIfToday(
//                            timings = _state.value.prayerTimes.timings,
//                            browsedDate = _state.value.dateBrowser.currentDate
//                        )
//                    }
//                    if (_state.value.prayerTimes.timings == null) {
//                        checkPermissionAndLoad()
//                    }
//                }
//            }
//            is HomeContract.Intent.LocationServicesToggled -> {
//                if (intent.enabled && _state.value.location.areServicesDisabled) {
//                    checkPermissionAndLoad()
//                }
//            }
//            is HomeContract.Intent.ConnectivityChanged -> {
//                if (intent.isConnected && isShowingGenericOfflineError()) {
//                    checkPermissionAndLoad()
//                }
//            }
//        }
//    }
//
//    private fun isShowingGenericOfflineError(): Boolean {
//        val s = _state.value
//        return s.errorMessage != null &&
//                !s.location.isPermissionRequired &&
//                !s.location.isPermanentlyDenied &&
//                !s.location.areServicesDisabled
//    }
//
//    private fun checkPermissionAndLoad() {
//        val delegateState = locationPermissionDelegate.state.value
//        if (delegateState.permanentlyDenied) {
//            _state.update {
//                it.copy(
//                    isLoading = false,
//                    errorMessage = UiText.Res(R.string.location_permission_permanently_denied)
//                )
//            }
//            return
//        }
//        if (delegateState.servicesDisabled) {
//            _state.update {
//                it.copy(
//                    isLoading = false,
//                    errorMessage = UiText.Res(R.string.location_services_disabled)
//                )
//            }
//            return
//        }
//        if (!connectivityChecker.isConnected() && _state.value.prayerTimes.timings == null) {
//            _state.update {
//                it.copy(
//                    isLoading = false,
//                    errorMessage = UiText.Res(R.string.no_internet_connection)
//                )
//            }
//            return
//        }
//        _state.update { it.copy(errorMessage = null) }
//        loadPrayerTimes()
//    }
//
//    private suspend fun checkExactAlarmAndNotificationPermissions(promptIfMissing: Boolean) {
//        val hasExactAlarm = permissionChecker.canScheduleExactAlarms()
//        val hasNotification = permissionChecker.hasNotificationPermission()
//        _state.update {
//            it.copy(
//                systemPermissions = it.systemPermissions.copy(
//                    hasExactAlarm = hasExactAlarm,
//                    hasNotification = hasNotification
//                )
//            )
//        }
//        if (!promptIfMissing) return
//        if (!hasExactAlarm) _effect.send(HomeContract.Effect.RequestExactAlarmPermission)
//        if (!hasNotification) _effect.send(HomeContract.Effect.RequestNotificationPermission)
//    }
//
//    private fun loadPrayerTimes() {
//        loadPrayerTimesJob?.cancel()
//        loadPrayerTimesJob = viewModelScope.launch {
//            try {
//                val savedLocation = getSavedLocationUseCase().first()
//                if (savedLocation != null) {
//                    handleSavedLocation(savedLocation)
//                } else {
//                    handleFreshLocation()
//                }
//            } catch (e: SecurityException) {
//                locationPermissionDelegate.requirePermission()
//                _state.update { it.copy(isLoading = false, errorMessage = null) }
//            } catch (e: CancellationException) {
//                throw e
//            } catch (e: Exception) {
//                val message = UiText.Res(R.string.failed_to_get_location)
//                _state.update { it.copy(isLoading = false, errorMessage = message) }
//                _effect.send(HomeContract.Effect.ShowError(message))
//            }
//        }
//    }
//
//    private suspend fun handleSavedLocation(savedLocation: SavedLocation) {
//        val lang = observeSettings().first().language.code
//        val isOnline = connectivityChecker.isConnected()
//
//        val finalName = if (lang == "ar") {
//            savedLocation.toDisplayName()
//        } else if (isOnline) {
//            withTimeoutOrNull(LOCATION_NAME_LOOKUP_TIMEOUT) {
//                getLocalizedLocationName(savedLocation, lang)
//            } ?: savedLocation.toDisplayName()
//        } else {
//            savedLocation.toDisplayName()
//        }
//
//        _state.update {
//            it.copy(
//                location = it.location.copy(
//                    latitude = savedLocation.latitude,
//                    longitude = savedLocation.longitude,
//                    locationName = finalName
//                )
//            )
//        }
//
//        fetchAndApplyPrayerTimes(savedLocation.latitude, savedLocation.longitude)
//    }
//
//    private suspend fun handleFreshLocation() {
//        if (locationPermissionDelegate.state.value.permanentlyDenied) {
//            _state.update {
//                it.copy(
//                    isLoading = false,
//                    errorMessage = UiText.Res(R.string.location_permission_permanently_denied)
//                )
//            }
//            return
//        }
//
//        locationPermissionDelegate.reset()
//        _state.update { it.copy(isLoading = true, errorMessage = null) }
//
//        when (val result = resolveLocationUseCase(locationPermissionAutoPromptShown)) {
//            is ResolveLocationUseCase.Result.Success -> {
//                locationPermissionAutoPromptShown = false
//
//                val lang = observeSettings().first().language.code
//                val isOnline = connectivityChecker.isConnected()
//
//                // Localize the location name
//                val localizedName = if (lang != "ar" && isOnline) {
//                    withTimeoutOrNull(LOCATION_NAME_LOOKUP_TIMEOUT) {
//                        getLocalizedLocationName(result.savedLocation, lang)
//                    }
//                } else {
//                    null
//                }
//
//                // Use localized name if available, otherwise fall back to saved location name
//                val finalName = localizedName ?: result.savedLocation.toDisplayName()
//
//                // If we successfully localized and have a different name, save it
//                if (localizedName != null && localizedName != result.savedLocation.toDisplayName()) {
//                    val (city, country) = parseDisplayName(localizedName)
//                    saveManualLocationUseCase(
//                        result.savedLocation.latitude,
//                        result.savedLocation.longitude,
//                        city,
//                        country
//                    )
//                }
//
//                _state.update {
//                    it.copy(
//                        location = it.location.copy(
//                            latitude = result.savedLocation.latitude,
//                            longitude = result.savedLocation.longitude,
//                            locationName = finalName
//                        )
//                    )
//                }
//
//                fetchAndApplyPrayerTimes(
//                    result.savedLocation.latitude,
//                    result.savedLocation.longitude
//                )
//            }
//            is ResolveLocationUseCase.Result.PromptPermission -> {
//                locationPermissionAutoPromptShown = true
//                locationPermissionDelegate.requirePermission()
//                _state.update { it.copy(isLoading = false) }
//            }
//            is ResolveLocationUseCase.Result.PermissionRequired -> {
//                _state.update {
//                    it.copy(
//                        isLoading = false,
//                        errorMessage = UiText.Res(R.string.location_permission_is_required_to_show_prayer_times)
//                    )
//                }
//            }
//            is ResolveLocationUseCase.Result.PermanentlyDenied -> {
//                _state.update {
//                    it.copy(
//                        isLoading = false,
//                        errorMessage = UiText.Res(R.string.location_permission_permanently_denied)
//                    )
//                }
//            }
//            is ResolveLocationUseCase.Result.ServicesDisabled -> {
//                locationPermissionDelegate.markServicesDisabled()
//                _state.update {
//                    it.copy(
//                        isLoading = false,
//                        errorMessage = UiText.Res(R.string.location_services_disabled),
//                        location = it.location.copy(areServicesDisabled = true)
//                    )
//                }
//            }
//            is ResolveLocationUseCase.Result.Error -> {
//                val isCurrentlyConnected = connectivityChecker.isConnected()
//                val isLocationServicesError = result.message == UiText.Res(R.string.location_services_disabled)
//                val shouldClearFlags = isCurrentlyConnected && !isLocationServicesError
//
//                _state.update { current ->
//                    current.copy(
//                        isLoading = false,
//                        errorMessage = result.message,
//                        location = if (shouldClearFlags) {
//                            current.location.copy(
//                                isPermissionRequired = false,
//                                isPermanentlyDenied = false,
//                                areServicesDisabled = false
//                            )
//                        } else {
//                            current.location.copy(
//                                areServicesDisabled = current.location.areServicesDisabled || isLocationServicesError
//                            )
//                        }
//                    )
//                }
//                if (shouldClearFlags) {
//                    _effect.send(HomeContract.Effect.ShowError(result.message))
//                }
//            }
//        }
//    }
//
//    private fun parseDisplayName(displayName: String?): Pair<String?, String?> {
//        if (displayName == null) return null to null
//        val parts = displayName.split(", ", limit = 2)
//        return when (parts.size) {
//            2 -> parts[0] to parts[1]
//            1 -> parts[0] to null
//            else -> null to null
//        }
//    }
//
//    private suspend fun fetchAndApplyPrayerTimes(latitude: Double, longitude: Double) {
//        val today = _state.value.dateBrowser.currentDate.format(dateKeyFormatter)
//        val cached = getCachedPrayerTimesUseCase(today, latitude, longitude)
//
//        if (cached != null) {
//            applyLoadedPrayerTimes(cached.timings, cached.date, today, latitude, longitude)
//            return
//        }
//
//        val isScreenCompletelyEmpty = _state.value.location.locationName == null &&
//                _state.value.prayerTimes.timings == null
//
//        if (isScreenCompletelyEmpty) {
//            _state.update { it.copy(isLoading = true) }
//        }
//
//        val result = withTimeoutOrNull(5_000L.milliseconds) {
//            getPrayerTimesUseCase(date = today, latitude = latitude, longitude = longitude)
//        } ?: Result.failure(Exception("Request timed out. Check your connection."))
//
//        result.fold(
//            onSuccess = { prayerTimesResult ->
//                applyLoadedPrayerTimes(
//                    prayerTimesResult.timings,
//                    prayerTimesResult.date,
//                    today,
//                    latitude,
//                    longitude
//                )
//            },
//            onFailure = { throwable ->
//                val isOffline = !connectivityChecker.isConnected()
//                val message = if (isOffline) {
//                    UiText.Res(R.string.no_internet_connection)
//                } else {
//                    throwable.message?.let { UiText.Raw(it) }
//                        ?: UiText.Res(R.string.something_went_wrong)
//                }
//                _state.update {
//                    it.copy(
//                        isLoading = false,
//                        errorMessage = message,
//                        location = it.location.copy(
//                            isPermissionRequired = false,
//                            isPermanentlyDenied = false,
//                            areServicesDisabled = false
//                        )
//                    )
//                }
//                if (!isOffline) {
//                    _effect.send(HomeContract.Effect.ShowError(message))
//                }
//            }
//        )
//    }
//
//    private suspend fun applyLoadedPrayerTimes(
//        timings: PrayerTimings,
//        date: com.mhq.salati.prayertimes.domain.model.PrayerDate,
//        today: String,
//        latitude: Double,
//        longitude: Double
//    ) {
//        val info = calculateNextPrayerInfoUseCase(timings, today, latitude, longitude)
//        val browsingToday = isBrowsingToday()
//        _state.update {
//            it.copy(
//                isLoading = false,
//                prayerTimes = it.prayerTimes.copy(
//                    timings = timings,
//                    date = date,
//                    nextPrayerInfo = info,
//                    currentPrayerName = if (browsingToday) calculateCurrentPrayerNameUseCase(timings) else null,
//                    pastPrayers = if (browsingToday) calculatePastPrayersUseCase(timings) else emptySet()
//                )
//            )
//        }
//        startCountdownTicker(info.spanEndMillis)
//        prayerAlarmCoordinator.scheduleIfToday(
//            timings = timings,
//            browsedDate = _state.value.dateBrowser.currentDate
//        )
//        if (!alarmAndNotificationPermissionsChecked) {
//            alarmAndNotificationPermissionsChecked = true
//            checkExactAlarmAndNotificationPermissions(promptIfMissing = true)
//        }
//    }
//
//    private fun SavedLocation.toDisplayName(): String? = when {
//        cityName != null && countryName != null -> "$cityName, $countryName"
//        cityName != null -> cityName
//        countryName != null -> countryName
//        else -> null
//    }
//
//    private fun startCountdownTicker(spanEndMillis: Long) {
//        tickCounterJob?.cancel()
//        tickCounterJob = viewModelScope.launch {
//            while (isActive) {
//                val remaining = (spanEndMillis - System.currentTimeMillis()).coerceAtLeast(0)
//                _state.update {
//                    it.copy(
//                        prayerTimes = it.prayerTimes.copy(remainingMillis = remaining)
//                    )
//                }
//                if (remaining <= 0) {
//                    onNextPrayerWindowElapsed()
//                    break
//                }
//                delay(1_000.milliseconds)
//            }
//        }
//    }
//
//    private fun observeMutedPrayersForCurrentDate() {
//        mutedPrayersJob?.cancel()
//        val dateKey = _state.value.dateBrowser.currentDate.format(dateKeyFormatter)
//        mutedPrayersJob = viewModelScope.launch {
//            mutedPrayersRepository.observeMutedPrayers(dateKey).collect { mutedPrayers ->
//                _state.update {
//                    it.copy(
//                        adhan = it.adhan.copy(mutedPrayers = mutedPrayers)
//                    )
//                }
//                prayerAlarmCoordinator.scheduleIfToday(
//                    timings = _state.value.prayerTimes.timings,
//                    browsedDate = _state.value.dateBrowser.currentDate
//                )
//            }
//        }
//    }
//
//    private fun onNextPrayerWindowElapsed() {
//        if (_state.value.prayerTimes.nextPrayerInfo?.crossesIntoNextDay == true) {
//            val newDate = _state.value.dateBrowser.currentDate.plusDays(1)
//            _state.update {
//                it.copy(
//                    dateBrowser = it.dateBrowser.copy(
//                        currentDate = newDate,
//                        isBrowsingToday = newDate == clock.today()
//                    )
//                )
//            }
//            loadPrayerTimes()
//        } else {
//            viewModelScope.launch { recomputeNextPrayerInfo() }
//        }
//    }
//
//    private fun isBrowsingToday(): Boolean = _state.value.dateBrowser.currentDate == clock.today()
//
//    private suspend fun recomputeNextPrayerInfo() {
//        val timings = _state.value.prayerTimes.timings ?: return
//        val latitude = _state.value.location.latitude ?: return
//        val longitude = _state.value.location.longitude ?: return
//
//        val info = resolveNextPrayerInfoForDisplayUseCase(
//            browsedTimings = timings,
//            browsedDate = _state.value.dateBrowser.currentDate,
//            latitude = latitude,
//            longitude = longitude
//        )
//        val browsingToday = isBrowsingToday()
//        _state.update {
//            it.copy(
//                prayerTimes = it.prayerTimes.copy(
//                    nextPrayerInfo = info,
//                    currentPrayerName = if (browsingToday) calculateCurrentPrayerNameUseCase(timings) else null,
//                    pastPrayers = if (browsingToday) calculatePastPrayersUseCase(timings) else emptySet()
//                )
//            )
//        }
//        startCountdownTicker(info.spanEndMillis)
//    }
//
//    @SuppressLint("EmptySuperCall")
//    override fun onCleared() {
//        tickCounterJob?.cancel()
//        super.onCleared()
//    }
//}