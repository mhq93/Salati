package com.mhq.salati.splash.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.splash.domain.usecases.GetStartDestinationUseCase
import com.mhq.salati.splash.presentation.contract.SplashContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getStartDestinationUseCase: GetStartDestinationUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SplashContract.State())
    val state = _state.asStateFlow()

    private val _effect = Channel<SplashContract.Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        onIntent(SplashContract.Intent.CheckStartDestination)
    }

    fun onIntent(intent: SplashContract.Intent) {
        when (intent) {
            is SplashContract.Intent.CheckStartDestination -> checkStartDestination()
        }
    }

    private fun checkStartDestination() {
        viewModelScope.launch {
            val destination = getStartDestinationUseCase()
            _state.update { it.copy(isLoading = false) }
            _effect.send(SplashContract.Effect.NavigateTo(destination))
        }
    }
}