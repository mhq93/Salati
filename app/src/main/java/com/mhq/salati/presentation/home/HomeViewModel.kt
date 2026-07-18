package com.mhq.salati.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.domain.usecases.GetPrayerTimesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPrayerTimesUseCase: GetPrayerTimesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeContract.State())
    val state: StateFlow<HomeContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<HomeContract.Effect>()
    val effect: SharedFlow<HomeContract.Effect> = _effect.asSharedFlow()

    fun onIntent(intent: HomeContract.Intent) {
        when (intent) {
            is HomeContract.Intent.LoadPrayerTimes -> loadPrayerTimes()
            is HomeContract.Intent.Retry -> loadPrayerTimes()
        }
    }

    private fun loadPrayerTimes() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)

            val today = SimpleDateFormat("dd-MM-yyyy", Locale.US).format(Date())

            // ⚠️ Hardcoded coordinates for now — replace with real location once
            // the location feature is built (Alexandria used as placeholder).
            val result = getPrayerTimesUseCase(
                date = today,
                latitude = 31.2058,
                longitude = 29.9245
            )

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
        }
    }
}