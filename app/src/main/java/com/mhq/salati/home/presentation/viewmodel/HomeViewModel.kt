package com.mhq.salati.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.R
import com.mhq.salati.adhan.domain.repo.MutedPrayersRepository
import com.mhq.salati.adhan.domain.usecases.ObserveAdhanPlaybackStateUseCase
import com.mhq.salati.adhan.domain.usecases.ScheduleDailyPrayerAlarmsUseCase
import com.mhq.salati.adhan.domain.usecases.StopAdhanPlaybackUseCase
import com.mhq.salati.adhan.domain.usecases.ToggleMutePrayerUseCase
import com.mhq.salati.home.domain.usecases.CalculateNextPrayerInfoUseCase
import com.mhq.salati.home.presentation.contract.HomeContract
import com.mhq.salati.location.domain.repo.LocationProvider
import com.mhq.salati.location.domain.usecases.FetchAndSaveLocationUseCase
import com.mhq.salati.location.domain.usecases.GetSavedLocationUseCase
import com.mhq.salati.permissions.domain.PermissionChecker
import com.mhq.salati.permissions.location.LocationPermissionDelegate
import com.mhq.salati.permissions.location.LocationPermissionEffect
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.prayertimes.domain.usecases.GetCachedPrayerTimesUseCase
import com.mhq.salati.prayertimes.domain.usecases.GetPrayerTimesUseCase
import com.mhq.salati.shared.domain.usecases.ParseTimeToMinutesUseCase
import com.mhq.salati.shared.presentation.components.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val locationProvider: LocationProvider,
    private val permissionChecker: PermissionChecker,
    private val mutedPrayersRepository: MutedPrayersRepository,
    private val getPrayerTimesUseCase: GetPrayerTimesUseCase,
    private val getSavedLocationUseCase: GetSavedLocationUseCase,
    private val fetchAndSaveLocationUseCase: FetchAndSaveLocationUseCase,
    private val toggleMutePrayerUseCase: ToggleMutePrayerUseCase,
    private val stopAdhanPlaybackUseCase: StopAdhanPlaybackUseCase,
    private val parseTimeToMinutesUseCase: ParseTimeToMinutesUseCase,
    private val getCachedPrayerTimesUseCase: GetCachedPrayerTimesUseCase,
    private val calculateNextPrayerInfoUseCase: CalculateNextPrayerInfoUseCase,
    private val observeAdhanPlaybackStateUseCase: ObserveAdhanPlaybackStateUseCase,
    private val scheduleDailyPrayerAlarmsUseCase: ScheduleDailyPrayerAlarmsUseCase
) : ViewModel() {

    private val permissionDelegate = LocationPermissionDelegate()
    val permissionEffect: SharedFlow<LocationPermissionEffect> = permissionDelegate.effect
    private var alarmAndNotificationPermissionsChecked = false

    private val _state = MutableStateFlow(HomeContract.State())
    val state: StateFlow<HomeContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<HomeContract.Effect>()
    val effect: SharedFlow<HomeContract.Effect> = _effect.asSharedFlow()

    private var loadPrayerTimesJob: Job? = null
    private var tickCounterJob: Job? = null
    private var mutedPrayersJob: Job? = null

    init {
        viewModelScope.launch {
            permissionDelegate.state.collect { permissionState ->
                _state.update { it.copy(locationPermission = permissionState) }
            }
        }

        viewModelScope.launch { mutedPrayersRepository.purgePastDates() }
        observeMutedPrayersForCurrentDate()

        viewModelScope.launch {
            permissionDelegate.effect.collect { effect ->
                if (effect is LocationPermissionEffect.PermissionResolved &&
                    !alarmAndNotificationPermissionsChecked
                ) {
                    alarmAndNotificationPermissionsChecked = true
                    checkExactAlarmAndNotificationPermissions()
                }
            }
        }

        observeAdhanPlaybackStateUseCase()
            .onEach { playback -> _state.update { it.copy(adhanPlayback = playback) } }
            .launchIn(viewModelScope)
    }

    fun onIntent(intent: HomeContract.Intent) {
        when (intent) {
            is HomeContract.Intent.LoadPrayerTimes -> {
                checkPermissionAndLoad()
            }

            is HomeContract.Intent.Retry -> {
                checkPermissionAndLoad()
            }

            is HomeContract.Intent.PreviousDay -> {
                if (!isBrowsingToday()) {
                    _state.update { state ->
                        state.currentDate.apply { add(Calendar.DAY_OF_YEAR, -1) }
                        state.copy(currentDate = state.currentDate)
                    }
                    observeMutedPrayersForCurrentDate()
                    loadPrayerTimes()
                }
            }

            is HomeContract.Intent.NextDay -> {
                _state.update { state ->
                    state.currentDate.apply { add(Calendar.DAY_OF_YEAR, 1) }
                    state.copy(currentDate = state.currentDate)
                }
                observeMutedPrayersForCurrentDate()
                loadPrayerTimes()
            }

            is HomeContract.Intent.LocationPermissionGranted -> {
                viewModelScope.launch {
                    permissionDelegate.onPermissionGranted()
                }
                loadPrayerTimes()
            }

            is HomeContract.Intent.LocationPermissionDenied -> {
                viewModelScope.launch {
                    permissionDelegate.onPermissionDenied(intent.permanentlyDenied)
                }
                _state.update {
                    it.copy(
                        errorMessage = if (intent.permanentlyDenied) {
                            UiText.Res(R.string.location_permission_permanently_denied_please_enable_it_via_settings)
                        } else {
                            UiText.Res(R.string.location_permission_is_required_to_show_prayer_times)
                        }
                    )
                }
            }

            is HomeContract.Intent.AccessAppSettings -> {
                viewModelScope.launch {
                    permissionDelegate.requestAppSettings()
                }
            }

            is HomeContract.Intent.AccessDeviceLocationSettings -> {
                viewModelScope.launch {
                    permissionDelegate.requestLocationSettings()
                }
            }

            is HomeContract.Intent.ToggleMute -> {
                viewModelScope.launch {
                    val dateKey = SimpleDateFormat("dd-MM-yyyy", Locale.US)
                        .format(_state.value.currentDate.time)
                    val currentlyMuted = intent.prayerName in _state.value.mutedPrayers
                    toggleMutePrayerUseCase(dateKey, intent.prayerName, !currentlyMuted)
                }
            }

            is HomeContract.Intent.RecheckSystemPermissions -> {
                viewModelScope.launch {
                    val hasExactAlarm = permissionChecker.canScheduleExactAlarms()
                    _state.update {
                        it.copy(
                            hasExactAlarmPermission = hasExactAlarm,
                            hasNotificationPermission = permissionChecker.hasNotificationPermission()
                        )
                    }
                    if (hasExactAlarm) rescheduleAlarmsIfLoaded()
                }
            }

            is HomeContract.Intent.NotificationPermissionResult -> {
                _state.update { it.copy(hasNotificationPermission = intent.granted) }
            }

            is HomeContract.Intent.ExactAlarmBannerClicked -> {
                viewModelScope.launch {
                    _effect.emit(HomeContract.Effect.RequestExactAlarmPermission)
                }
            }

            is HomeContract.Intent.NotificationBannerClicked -> {
                viewModelScope.launch {
                    _effect.emit(HomeContract.Effect.RequestNotificationPermission)
                }
            }

            is HomeContract.Intent.StopAdhanClicked -> {
                stopAdhanPlaybackUseCase()
            }
        }
    }

    private fun checkPermissionAndLoad() {
        _state.update { it.copy(errorMessage = null) }
        viewModelScope.launch {
            if (permissionChecker.hasLocationPermission()) {
                permissionDelegate.onPermissionGranted()
                loadPrayerTimes()
            } else {
                permissionDelegate.requirePermission()
            }
        }
    }

    private suspend fun checkExactAlarmAndNotificationPermissions() {
        val hasExactAlarm = permissionChecker.canScheduleExactAlarms()
        val hasNotification = permissionChecker.hasNotificationPermission()
        _state.update {
            it.copy(
                hasExactAlarmPermission = hasExactAlarm,
                hasNotificationPermission = hasNotification
            )
        }
        if (!hasExactAlarm)
            _effect.emit(HomeContract.Effect.RequestExactAlarmPermission)

        if (!hasNotification)
            _effect.emit(HomeContract.Effect.RequestNotificationPermission)
    }

    private fun loadPrayerTimes() {
        loadPrayerTimesJob?.cancel()
        loadPrayerTimesJob = viewModelScope.launch {
            permissionDelegate.reset()
            _state.update { it.copy(errorMessage = null) }

            val today = SimpleDateFormat("dd-MM-yyyy", Locale.US)
                .format(_state.value.currentDate.time)

            try {
                val savedLocation = getSavedLocationUseCase().first()
                val location = if (savedLocation != null) {
                    savedLocation
                } else {
                    _state.update { it.copy(isLoading = true) }

                    if (!locationProvider.isLocationEnabled()) {
                        permissionDelegate.markServicesDisabled()
                        _state.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = UiText.Res(R.string.location_services_disabled)
                            )
                        }
                        return@launch
                    }

                    if (!permissionChecker.hasLocationPermission()) {
                        permissionDelegate.requirePermission()
                        _state.update { it.copy(isLoading = false) }
                        return@launch
                    }
                    fetchAndSaveLocationUseCase()
                }

                val latitude = location.latitude
                val longitude = location.longitude
                _state.update { it.copy(latitude = latitude, longitude = longitude) }
                val cached = getCachedPrayerTimesUseCase(today, latitude, longitude)

                if (cached != null) {
                    val info = calculateNextPrayerInfoUseCase(
                        cached.timings,
                        today,
                        latitude,
                        longitude
                    )

                    _state.update {
                        it.copy(
                            isLoading = false,
                            timings = cached.timings,
                            date = cached.date,
                            nextPrayerInfo = info,
                            currentPrayerName = if (isBrowsingToday()) calculateCurrentPrayerName(
                                cached.timings
                            ) else null,
                            pastPrayers = if (isBrowsingToday()) calculatePastPrayers(cached.timings) else emptySet()
                        )
                    }
                    startCountdownTicker(info.spanEndMillis)
                    rescheduleAlarmsIfLoaded()
                    return@launch
                }

                _state.update { it.copy(isLoading = true) }

                val result = withTimeoutOrNull(5_000L.milliseconds) {
                    getPrayerTimesUseCase(
                        date = today,
                        latitude = latitude,
                        longitude = longitude
                    )
                } ?: Result.failure(Exception("Request timed out. Check your connection."))

                result.fold(
                    onSuccess = { prayerTimesResult ->
                        val info = calculateNextPrayerInfoUseCase(
                            prayerTimesResult.timings, today, latitude, longitude
                        )
                        _state.update {
                            it.copy(
                                isLoading = false,
                                timings = prayerTimesResult.timings,
                                date = prayerTimesResult.date,
                                nextPrayerInfo = info,
                                currentPrayerName = if (isBrowsingToday()) calculateCurrentPrayerName(
                                    prayerTimesResult.timings
                                ) else null,
                                pastPrayers = if (isBrowsingToday()) calculatePastPrayers(
                                    prayerTimesResult.timings
                                ) else emptySet()
                            )
                        }
                        startCountdownTicker(info.spanEndMillis)
                        rescheduleAlarmsIfLoaded()
                    },
                    onFailure = { throwable ->
                        val message = throwable.message?.let { UiText.Raw(it) }
                            ?: UiText.Res(R.string.something_went_wrong)
                        _state.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = message
                            )
                        }
                        _effect.emit(HomeContract.Effect.ShowError(message))
                    }
                )
            } catch (e: SecurityException) {
                permissionDelegate.requirePermission()
                _state.update { it.copy(isLoading = false, errorMessage = null) }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                val message = UiText.Res(R.string.failed_to_get_location)
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = message
                    )
                }
                _effect.emit(HomeContract.Effect.ShowError(message))
            }
        }
    }

    private fun startCountdownTicker(spanEndMillis: Long) {
        tickCounterJob?.cancel()
        tickCounterJob = viewModelScope.launch {
            while (isActive) {
                val remaining = (spanEndMillis - System.currentTimeMillis()).coerceAtLeast(0)
                _state.update { it.copy(remainingMillis = remaining) }
                if (remaining <= 0) {
                    onNextPrayerWindowElapsed()
                    break
                }
                delay(1000.milliseconds)
            }
        }
    }

    private fun observeMutedPrayersForCurrentDate() {
        mutedPrayersJob?.cancel()
        val dateKey =
            SimpleDateFormat("dd-MM-yyyy", Locale.US).format(_state.value.currentDate.time)
        mutedPrayersJob = viewModelScope.launch {
            mutedPrayersRepository.observeMutedPrayers(dateKey).collect { mutedPrayers ->
                _state.update { it.copy(mutedPrayers = mutedPrayers) }
                rescheduleAlarmsIfLoaded()
            }
        }
    }

    private fun onNextPrayerWindowElapsed() {
        if (_state.value.nextPrayerInfo?.crossesIntoNextDay == true) {
            _state.update { state ->
                state.currentDate.apply { add(Calendar.DAY_OF_YEAR, 1) }
                state.copy(currentDate = state.currentDate)
            }
            loadPrayerTimes()
        } else {
            viewModelScope.launch { recomputeNextPrayerInfo() }
        }
    }

    private fun isBrowsingToday(): Boolean {
        val browsedDateKey =
            SimpleDateFormat("dd-MM-yyyy", Locale.US).format(_state.value.currentDate.time)
        val todayKey = SimpleDateFormat("dd-MM-yyyy", Locale.US).format(Calendar.getInstance().time)
        return browsedDateKey == todayKey
    }

    private fun calculatePastPrayers(timings: PrayerTimings): Set<String> {
        val allTimings = listOf(
            "Imsak" to timings.imsak,
            "Fajr" to timings.fajr,
            "Shorouq" to timings.sunrise,
            "Dhuhr" to timings.dhuhr,
            "Asr" to timings.asr,
            "Maghrib" to timings.maghrib,
            "Isha" to timings.isha,
            "First Third" to timings.firstThird,
            "Midnight" to timings.midnight,
            "Last Third" to timings.lastThird
        )

        val imsakMinutes = parseTimeToMinutesUseCase(timings.imsak)

        fun normalize(minutes: Int) = if (minutes < imsakMinutes) minutes + 24 * 60 else minutes

        val rawNowMinutes = parseTimeToMinutesUseCase(
            SimpleDateFormat("HH:mm", Locale.US).format(Date())
        )
        val nowMinutes = normalize(rawNowMinutes)

        return allTimings
            .filter { (_, time) -> normalize(parseTimeToMinutesUseCase(time)) <= nowMinutes }
            .map { (name, _) -> name }
            .toSet()
    }

    private fun calculateCurrentPrayerName(timings: PrayerTimings): String? {
        val nowMinutes = parseTimeToMinutesUseCase(
            SimpleDateFormat("HH:mm", Locale.US).format(Date())
        )
        val prayers = listOf(
            "Fajr" to timings.fajr,
            "Dhuhr" to timings.dhuhr,
            "Asr" to timings.asr,
            "Maghrib" to timings.maghrib,
            "Isha" to timings.isha
        )
        return prayers
            .map { it.first to parseTimeToMinutesUseCase(it.second) }
            .filter { it.second <= nowMinutes }
            .maxByOrNull { it.second }
            ?.first
    }

    private suspend fun recomputeNextPrayerInfo() {
        val timings = _state.value.timings ?: return
        val latitude = _state.value.latitude ?: return
        val longitude = _state.value.longitude ?: return
        val date = SimpleDateFormat("dd-MM-yyyy", Locale.US)
            .format(_state.value.currentDate.time)
        val info = calculateNextPrayerInfoUseCase(timings, date, latitude, longitude)
        _state.update {
            it.copy(
                nextPrayerInfo = info,
                currentPrayerName = if (isBrowsingToday()) calculateCurrentPrayerName(timings) else null,
                pastPrayers = if (isBrowsingToday()) calculatePastPrayers(timings) else emptySet()
            )
        }
        startCountdownTicker(info.spanEndMillis)
    }

    private suspend fun rescheduleAlarmsIfLoaded() {
        val timings = _state.value.timings ?: return
        val date = _state.value.date ?: return

        val browsedDateKey =
            SimpleDateFormat("dd-MM-yyyy", Locale.US).format(_state.value.currentDate.time)
        val todayKey = SimpleDateFormat("dd-MM-yyyy", Locale.US).format(Calendar.getInstance().time)

        if (browsedDateKey != todayKey) return

        val todaysMutedPrayers = mutedPrayersRepository.getMutedPrayers(todayKey)
        scheduleDailyPrayerAlarmsUseCase(timings, todayKey, todaysMutedPrayers)
    }

    override fun onCleared() {
        tickCounterJob?.cancel()
        super.onCleared()
    }
}