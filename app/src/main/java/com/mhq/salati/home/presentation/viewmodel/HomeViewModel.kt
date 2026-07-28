package com.mhq.salati.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.adhan.domain.repo.MutedPrayersRepository
import com.mhq.salati.adhan.domain.usecases.ObserveAdhanPlaybackStateUseCase
import com.mhq.salati.adhan.domain.usecases.ScheduleDailyPrayerAlarmsUseCase
import com.mhq.salati.adhan.domain.usecases.StopAdhanPlaybackUseCase
import com.mhq.salati.adhan.domain.usecases.ToggleMutePrayerUseCase
import com.mhq.salati.home.domain.usecases.CalculateNextPrayerInfoUseCase
import com.mhq.salati.home.presentation.contract.HomeContract
import com.mhq.salati.location.data.LocationProvider
import com.mhq.salati.location.domain.usecases.FetchAndSaveLocationUseCase
import com.mhq.salati.location.domain.usecases.GetSavedLocationUseCase
import com.mhq.salati.permissions.domain.PermissionChecker
import com.mhq.salati.permissions.location.LocationPermissionDelegate
import com.mhq.salati.permissions.location.LocationPermissionEffect
import com.mhq.salati.prayertimes.domain.model.PrayerTimings
import com.mhq.salati.prayertimes.domain.usecases.GetCachedPrayerTimesUseCase
import com.mhq.salati.prayertimes.domain.usecases.GetPrayerTimesUseCase
import com.mhq.salati.shared.domain.usecases.ParseTimeToMinutesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mutedPrayersRepository: MutedPrayersRepository,
    private val getPrayerTimesUseCase: GetPrayerTimesUseCase,
    private val calculateNextPrayerInfoUseCase: CalculateNextPrayerInfoUseCase,
    private val parseTimeToMinutesUseCase: ParseTimeToMinutesUseCase,
    private val getCachedPrayerTimesUseCase: GetCachedPrayerTimesUseCase,
    private val toggleMutePrayerUseCase: ToggleMutePrayerUseCase,
    private val observeAdhanPlaybackStateUseCase: ObserveAdhanPlaybackStateUseCase,
    private val stopAdhanPlaybackUseCase: StopAdhanPlaybackUseCase,
    private val scheduleDailyPrayerAlarmsUseCase: ScheduleDailyPrayerAlarmsUseCase,
    private val getSavedLocationUseCase: GetSavedLocationUseCase,
    private val fetchAndSaveLocationUseCase: FetchAndSaveLocationUseCase,
    private val locationProvider: LocationProvider,
    private val permissionChecker: PermissionChecker
) : ViewModel() {

    private val permissionDelegate = LocationPermissionDelegate()
    val permissionEffect: SharedFlow<LocationPermissionEffect> = permissionDelegate.effect
    private var alarmAndNotificationPermissionsChecked = false

    private val _state = MutableStateFlow(HomeContract.State())
    val state: StateFlow<HomeContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<HomeContract.Effect>()
    val effect: SharedFlow<HomeContract.Effect> = _effect.asSharedFlow()

    private var currentDate: Calendar = Calendar.getInstance()

    private var loadJob: Job? = null

    init {
        viewModelScope.launch {
            permissionDelegate.state.collect { permissionState ->
                _state.value = _state.value.copy(
                    locationPermission = permissionState
                )
            }
        }

        viewModelScope.launch {
            mutedPrayersRepository.observeMutedPrayers().collect { mutedPrayers ->
                _state.value = _state.value.copy(
                    mutedPrayers = mutedPrayers
                )
                rescheduleAlarmsIfLoaded()
            }
        }

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
            is HomeContract.Intent.LoadPrayerTimes ->
                checkPermissionAndLoad()

            is HomeContract.Intent.Retry ->
                checkPermissionAndLoad()

            is HomeContract.Intent.PreviousDay -> {
                currentDate.add(Calendar.DAY_OF_YEAR, -1)
                loadPrayerTimes()
            }

            is HomeContract.Intent.NextDay -> {
                currentDate.add(Calendar.DAY_OF_YEAR, 1)
                loadPrayerTimes()
            }

            is HomeContract.Intent.NextPrayerWindowElapsed -> onNextPrayerWindowElapsed()

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
                _state.value = _state.value.copy(
                    errorMessage = if (intent.permanentlyDenied) {
                        "Location permission permanently denied. Please, enable it via Settings."
                    } else {
                        "Location permission is required to show prayer times."
                    }
                )
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
                    val currentlyMuted = intent.prayerName in _state.value.mutedPrayers
                    toggleMutePrayerUseCase(
                        intent.prayerName,
                        !currentlyMuted
                    )
                }
            }

            HomeContract.Intent.StopAdhanClicked -> stopAdhanPlaybackUseCase()
        }
    }

    private fun checkPermissionAndLoad() {
        _state.value = _state.value.copy(errorMessage = null)
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
        if (!permissionChecker.canScheduleExactAlarms()) {
            _effect.emit(HomeContract.Effect.RequestExactAlarmPermission)
        }
        if (!permissionChecker.hasNotificationPermission()) {
            _effect.emit(HomeContract.Effect.RequestNotificationPermission)
        }
    }

    private fun loadPrayerTimes() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            permissionDelegate.reset()
            _state.value = _state.value.copy(errorMessage = null)

            val today =
                SimpleDateFormat("dd-MM-yyyy", Locale.US)
                    .format(currentDate.time)

            try {
                val savedLocation = getSavedLocationUseCase().first()

                val location = if (savedLocation != null) {
                    savedLocation
                } else {
                    _state.value = _state.value.copy(isLoading = true)

                    if (!locationProvider.isLocationEnabled()) {
                        permissionDelegate.markServicesDisabled()
                        _state.value = _state.value.copy(
                            isLoading = false,
                            errorMessage = "Location services are turned off. Please, enable them."
                        )
                        return@launch
                    }

                    if (!permissionChecker.hasLocationPermission()) {
                        permissionDelegate.requirePermission()
                        _state.value = _state.value.copy(isLoading = false)
                        return@launch
                    }
                    fetchAndSaveLocationUseCase()
                }

                val latitude = location.latitude
                val longitude = location.longitude
                val cached = getCachedPrayerTimesUseCase(today, latitude, longitude)

                if (cached != null) {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        timings = cached.timings,
                        date = cached.date,
                        nextPrayerInfo = calculateNextPrayerInfoUseCase(cached.timings, today)

                    )
                    rescheduleAlarmsIfLoaded()
                    return@launch
                }

                _state.value = _state.value.copy(isLoading = true)

                val result = withTimeoutOrNull(5_000L.milliseconds) {
                    getPrayerTimesUseCase(
                        date = today,
                        latitude = latitude,
                        longitude = longitude
                    )
                } ?: Result.failure(
                    Exception("Request timed out. Check your connection.")
                )

                result.fold(
                    onSuccess = { prayerTimesResult ->
                        _state.value = _state.value.copy(
                            isLoading = false,
                            timings = prayerTimesResult.timings,
                            date = prayerTimesResult.date,
                            nextPrayerInfo = calculateNextPrayerInfoUseCase(prayerTimesResult.timings, today)
                        )
                        rescheduleAlarmsIfLoaded()
                    },
                    onFailure = { throwable ->
                        val message = throwable.message ?: "Something went wrong."
                        _state.value = _state.value.copy(
                            isLoading = false,
                            errorMessage = message
                        )
                        _effect.emit(
                            HomeContract.Effect.ShowError(message)
                        )
                    }
                )
            } catch (e: SecurityException) {
                permissionDelegate.requirePermission()
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = null
                )
            } catch (e: Exception) {
                val message = "Failed to get location."
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = message
                )
                _effect.emit(
                    HomeContract.Effect.ShowError(message)
                )
            }
        }
    }

    private fun onNextPrayerWindowElapsed() {
        if (_state.value.nextPrayerInfo?.crossesIntoNextDay == true) {
            currentDate.add(Calendar.DAY_OF_YEAR, 1)
            loadPrayerTimes()
        } else {
            recomputeNextPrayerInfo()
        }
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

    private fun recomputeNextPrayerInfo() {
        val timings = _state.value.timings ?: return
        val date = SimpleDateFormat("dd-MM-yyyy", Locale.US).format(currentDate.time)
        _state.value = _state.value.copy(
            nextPrayerInfo = calculateNextPrayerInfoUseCase(timings, date),
            currentPrayerName = calculateCurrentPrayerName(timings)
        )
    }

    private suspend fun rescheduleAlarmsIfLoaded() {
        val timings = _state.value.timings ?: return
        val date = _state.value.date ?: return
        val dateKey =
            SimpleDateFormat("dd-MM-yyyy", Locale.US)
                .format(currentDate.time)

        scheduleDailyPrayerAlarmsUseCase(timings, dateKey, _state.value.mutedPrayers)
    }
}