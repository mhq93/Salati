package com.mhq.salati.splash.presentation.viewmodel

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.language.domain.usecases.SetLanguageSelectedUseCase
import com.mhq.salati.settings.domain.model.AppLanguage
import com.mhq.salati.settings.domain.usecases.ObserveSettingsUseCase
import com.mhq.salati.settings.domain.usecases.UpdateLanguageUseCase
import com.mhq.salati.shared.presentation.components.UiText
import com.mhq.salati.splash.domain.usecases.GetStartDestinationUseCase
import com.mhq.salati.splash.presentation.contract.SplashContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getStartDestinationUseCase: GetStartDestinationUseCase,
    private val updateLanguageUseCase: UpdateLanguageUseCase,
    private val setLanguageSelectedUseCase: SetLanguageSelectedUseCase,
    observeSettingsUseCase: ObserveSettingsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SplashContract.State())
    val state = _state.asStateFlow()

    private val _effect = Channel<SplashContract.Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        onIntent(SplashContract.Intent.CheckStartDestination)

        observeSettingsUseCase()
            .onEach { settings ->
                _state.update {
                    it.copy(
                        themeMode = settings.themeMode,
                        isLanguageSelected = settings.isLanguageSelected,
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
            is SplashContract.Intent.SelectInitialLanguage -> selectInitialLanguage(intent.language)
        }
    }

    private fun selectInitialLanguage(language: AppLanguage) = viewModelScope.launch {
        try {
            updateLanguageUseCase(language)
            // Apply the locale BEFORE flipping isLanguageSelected, so the app
            // recreates into the right locale instead of briefly showing
            // main content in the old one, and so we only recreate once.
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(language.code)
            )
            setLanguageSelectedUseCase(true)
        } catch (e: Exception) {
            _effect.send(
                SplashContract.Effect.ShowError(
                    UiText.Raw("Something went wrong.")
                )
            )
        }
    }

    private fun checkStartDestination() {
        viewModelScope.launch {
            val destination = getStartDestinationUseCase()
            _state.update {
                it.copy(
                    startDestination = destination,
                    isDestinationReady = true,
                    isLoading = !it.isSettingsReady
                )
            }
        }
    }
}

/*
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
 */