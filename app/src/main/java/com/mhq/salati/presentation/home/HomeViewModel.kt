package com.mhq.salati.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.data.location.LocationProvider
import com.mhq.salati.domain.permissions.PermissionChecker
import com.mhq.salati.domain.repo.alarms.MutedPrayersRepository
import com.mhq.salati.domain.usecases.alarms.ScheduleDailyPrayerAlarmsUseCase
import com.mhq.salati.domain.usecases.alarms.ToggleMutePrayerUseCase
import com.mhq.salati.domain.usecases.location.FetchAndSaveLocationUseCase
import com.mhq.salati.domain.usecases.location.GetSavedLocationUseCase
import com.mhq.salati.domain.usecases.prayers.GetCachedPrayerTimesUseCase
import com.mhq.salati.domain.usecases.prayers.GetPrayerTimesUseCase
import com.mhq.salati.presentation.common.location.LocationPermissionDelegate
import com.mhq.salati.presentation.common.location.LocationPermissionEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mutedPrayersRepository: MutedPrayersRepository,
    private val getPrayerTimesUseCase: GetPrayerTimesUseCase,
    private val getCachedPrayerTimesUseCase: GetCachedPrayerTimesUseCase,
    private val toggleMutePrayerUseCase: ToggleMutePrayerUseCase,
    private val scheduleDailyPrayerAlarmsUseCase: ScheduleDailyPrayerAlarmsUseCase,
    private val getSavedLocationUseCase: GetSavedLocationUseCase,
    private val fetchAndSaveLocationUseCase: FetchAndSaveLocationUseCase,
    private val locationProvider: LocationProvider,
    private val permissionChecker: PermissionChecker
) : ViewModel() {

    private val permissionDelegate = LocationPermissionDelegate()
    val permissionEffect: SharedFlow<LocationPermissionEffect> = permissionDelegate.effect

    private val _state = MutableStateFlow(HomeContract.State())
    val state: StateFlow<HomeContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<HomeContract.Effect>()
    val effect: SharedFlow<HomeContract.Effect> = _effect.asSharedFlow()

    private var currentDate: Calendar = Calendar.getInstance()

    init {
        viewModelScope.launch {
            permissionDelegate.state.collect { permissionState ->
                _state.value = _state.value.copy(locationPermission = permissionState)
            }
        }

        viewModelScope.launch {
            mutedPrayersRepository.observeMutedPrayers().collect { muted ->
                _state.value = _state.value.copy(mutedPrayers = muted)
                rescheduleAlarmsIfLoaded()
            }
        }
    }

    fun onIntent(intent: HomeContract.Intent) {
        when (intent) {
            is HomeContract.Intent.LoadPrayerTimes -> checkPermissionAndLoad()
            is HomeContract.Intent.Retry -> checkPermissionAndLoad()
            is HomeContract.Intent.PreviousDay -> {
                currentDate.add(Calendar.DAY_OF_YEAR, -1)
                loadPrayerTimes()
            }

            is HomeContract.Intent.NextDay -> {
                currentDate.add(Calendar.DAY_OF_YEAR, 1)
                loadPrayerTimes()
            }

            is HomeContract.Intent.LocationPermissionGranted -> {
                viewModelScope.launch { permissionDelegate.onPermissionGranted() }
                loadPrayerTimes()
            }

            is HomeContract.Intent.LocationPermissionDenied -> {
                viewModelScope.launch { permissionDelegate.onPermissionDenied(intent.permanentlyDenied) }
                _state.value = _state.value.copy(
                    errorMessage = if (intent.permanentlyDenied) {
                        "Location permission permanently denied. Please enable it in Settings."
                    } else {
                        "Location permission is required to show prayer times."
                    }
                )
            }

            is HomeContract.Intent.AccessAppSettings -> {
                viewModelScope.launch { permissionDelegate.requestAppSettings() }
            }

            is HomeContract.Intent.AccessDeviceLocationSettings -> {
                viewModelScope.launch { permissionDelegate.requestLocationSettings() }
            }

            is HomeContract.Intent.ToggleMute -> {
                viewModelScope.launch {
                    val currentlyMuted = intent.prayerName in _state.value.mutedPrayers
                    toggleMutePrayerUseCase(intent.prayerName, !currentlyMuted)
                }
            }
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

    private fun loadPrayerTimes() {
        viewModelScope.launch {
            permissionDelegate.reset()
            _state.value = _state.value.copy(errorMessage = null)

            val today = SimpleDateFormat("dd-MM-yyyy", Locale.US).format(currentDate.time)

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
                        date = cached.date
                    )
                    return@launch
                }

                _state.value = _state.value.copy(isLoading = true)

                val result = withTimeoutOrNull(5_000L.milliseconds) {
                    getPrayerTimesUseCase(
                        date = today,
                        latitude = latitude,
                        longitude = longitude
                    )
                } ?: Result.failure(Exception("Request timed out. Check your connection."))

                result.fold(
                    onSuccess = { prayerTimesResult ->
                        _state.value = _state.value.copy(
                            isLoading = false,
                            timings = prayerTimesResult.timings,
                            date = prayerTimesResult.date
                        )
                    },
                    onFailure = { throwable ->
                        val message = throwable.message ?: "Something went wrong."
                        _state.value = _state.value.copy(
                            isLoading = false,
                            errorMessage = message
                        )
                        _effect.emit(HomeContract.Effect.ShowError(message))
                    }
                )
            } catch (e: SecurityException) {
                permissionDelegate.requirePermission()
                _state.value = _state.value.copy(isLoading = false, errorMessage = null)
            } catch (e: Exception) {
                val message = "Failed to get location."
                _state.value = _state.value.copy(isLoading = false, errorMessage = message)
                _effect.emit(HomeContract.Effect.ShowError(message))
            }
        }
    }

    private suspend fun rescheduleAlarmsIfLoaded() {
        val timings = _state.value.timings ?: return
        val date = _state.value.date ?: return
        val dateKey = SimpleDateFormat("dd-MM-yyyy", Locale.US).format(currentDate.time)
        scheduleDailyPrayerAlarmsUseCase(timings, dateKey, _state.value.mutedPrayers)
    }
}