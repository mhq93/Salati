package com.mhq.salati.splash.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.settings.domain.usecases.ObserveSettingsUseCase
import com.mhq.salati.settings.presentation.mapper.toUi
import com.mhq.salati.splash.domain.usecases.GetStartDestinationUseCase
import com.mhq.salati.splash.presentation.contract.SplashContract
import com.mhq.salati.splash.presentation.mapper.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getStartDestinationUseCase: GetStartDestinationUseCase,
    observeSettingsUseCase: ObserveSettingsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SplashContract.State())
    val state = _state.asStateFlow()

    init {
        onIntent(SplashContract.Intent.CheckStartDestination)

        // The theme is observed here so MainActivity can theme the very first frame.
        // First-run language selection moved to onboarding's Welcome page,
        // so this ViewModel no longer carries any language flow.
        observeSettingsUseCase()
            .onEach { settings ->
                _state.update {
                    it.copy(
                        themeMode = settings.themeMode.toUi(),
                        isSettingsReady = true,
                        isLoading = !it.isDestinationReady
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun onIntent(intent: SplashContract.Intent) {
        when (intent) {
            is SplashContract.Intent.CheckStartDestination -> checkStartDestination()
        }
    }

    private fun checkStartDestination() {
        viewModelScope.launch {
            val destination = getStartDestinationUseCase()
            _state.update {
                it.copy(
                    startDestination = destination.toUi(),
                    isDestinationReady = true,
                    isLoading = !it.isSettingsReady
                )
            }
        }
    }
}