package com.mhq.salati.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.data.location.LocationProvider
import com.mhq.salati.domain.usecases.GetCachedPrayerTimesUseCase
import com.mhq.salati.domain.usecases.GetPrayerTimesUseCase
import com.mhq.salati.presentation.common.LocationPermissionDelegate
import com.mhq.salati.presentation.common.LocationPermissionEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPrayerTimesUseCase: GetPrayerTimesUseCase,
    private val getCachedPrayerTimesUseCase: GetCachedPrayerTimesUseCase,
    private val locationProvider: LocationProvider
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
            is HomeContract.Intent.LocationPermissionGranted -> loadPrayerTimes()
            is HomeContract.Intent.LocationPermissionDenied -> {
                permissionDelegate.onPermissionDenied(intent.permanentlyDenied)
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
        }
    }

    private fun checkPermissionAndLoad() {
        permissionDelegate.requirePermission()
        _state.value = _state.value.copy(errorMessage = null)
    }

    private fun loadPrayerTimes() {
        viewModelScope.launch {
            permissionDelegate.reset()
            _state.value = _state.value.copy(errorMessage = null)

            val today = SimpleDateFormat("dd-MM-yyyy", Locale.US).format(currentDate.time)

            if (!locationProvider.isLocationEnabled()) {
                permissionDelegate.markServicesDisabled()
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = "Location services are turned off. Please enable them."
                )
                return@launch
            }

            try {
                val (latitude, longitude) = locationProvider.getCurrentLocation()

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

                val result = getPrayerTimesUseCase(date = today, latitude = latitude, longitude = longitude)

                result.fold(
                    onSuccess = { prayerTimesResult ->
                        _state.value = _state.value.copy(
                            isLoading = false,
                            timings = prayerTimesResult.timings,
                            date = prayerTimesResult.date
                        )
                    },
                    onFailure = { throwable ->
                        val message = throwable.message ?: "Something went wrong"
                        _state.value = _state.value.copy(isLoading = false, errorMessage = message)
                        _effect.emit(HomeContract.Effect.ShowError(message))
                    }
                )
            } catch (e: Exception) {
                val message = "Failed to get location"
                _state.value = _state.value.copy(isLoading = false, errorMessage = message)
                _effect.emit(HomeContract.Effect.ShowError(message))
            }
        }
    }
}