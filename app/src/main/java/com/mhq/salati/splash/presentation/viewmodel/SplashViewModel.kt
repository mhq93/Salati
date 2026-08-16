package com.mhq.salati.splash.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.settings.domain.usecases.ObserveSettingsUseCase
import com.mhq.salati.splash.domain.usecases.GetStartDestinationUseCase
import com.mhq.salati.splash.presentation.contract.SplashContract
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

    //private val _effect = Channel<SplashContract.Effect>(Channel.BUFFERED)
    //val effect = _effect.receiveAsFlow()

    init {
        onIntent(SplashContract.Intent.CheckStartDestination)

        observeSettingsUseCase()   // NEW
            .onEach { settings -> _state.update { it.copy(themeMode = settings.themeMode) } }
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
            _state.update { it.copy(isLoading = false, startDestination = destination) }
            //_effect.send(SplashContract.Effect.NavigateTo(destination))
        }
    }
}