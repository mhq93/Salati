package com.mhq.salati.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.data.location.LocationProvider
import com.mhq.salati.domain.usecases.GetPrayerTimesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPrayerTimesUseCase: GetPrayerTimesUseCase,
    private val locationProvider: LocationProvider
) : ViewModel() {

    private val _state = MutableStateFlow(HomeContract.State())
    val state: StateFlow<HomeContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<HomeContract.Effect>()
    val effect: SharedFlow<HomeContract.Effect> = _effect.asSharedFlow()

    fun onIntent(intent: HomeContract.Intent) {
        when (intent) {
            is HomeContract.Intent.Retry -> checkPermissionAndLoad()
            is HomeContract.Intent.LoadPrayerTimes -> checkPermissionAndLoad()
            is HomeContract.Intent.AccessAppSettings -> Unit
            is HomeContract.Intent.AccessDeviceLocationSettings -> Unit
            is HomeContract.Intent.LocationPermissionGranted -> loadPrayerTimes()
            is HomeContract.Intent.LocationPermissionDenied -> {
                _state.value = _state.value.copy(
                    locationPermissionRequired = false,
                    locationPermissionPermanentlyDenied = intent.permanentlyDenied,
                    errorMessage = if (intent.permanentlyDenied) {
                        "Location permission permanently denied. Please enable it in Settings."
                    } else {
                        "Location permission is required to show prayer times."
                    }
                )
            }
        }
    }

    private fun checkPermissionAndLoad() {
        _state.value = _state.value.copy(
            locationPermissionRequired = true,
            locationPermissionPermanentlyDenied = false,
            locationServicesDisabled = false,
            errorMessage = null
        )
    }

    private fun loadPrayerTimes() {
        viewModelScope.launch {
            val today = SimpleDateFormat("dd-MM-yyyy", Locale.US).format(Date())

            _state.value = _state.value.copy(
                isLoading = true,
                errorMessage = null,
                locationPermissionRequired = false,
                locationServicesDisabled = false,
                locationPermissionPermanentlyDenied = false
            )

            if (!locationProvider.isLocationEnabled()) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    locationServicesDisabled = true,
                    errorMessage = "Location services are turned off. Please enable them."
                )
                return@launch
            }

            try {
                val (latitude, longitude) = locationProvider.getCurrentLocation()
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