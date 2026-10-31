package com.mhq.salati.splash.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.language.domain.usecases.SetLanguageSelectedUseCase
import com.mhq.salati.settings.domain.usecases.ObserveSettingsUseCase
import com.mhq.salati.settings.domain.usecases.UpdateLanguageUseCase
import com.mhq.salati.settings.presentation.mapper.toDomain
import com.mhq.salati.settings.presentation.mapper.toUi
import com.mhq.salati.settings.presentation.model.AppLanguageUi
import com.mhq.salati.shared.presentation.model.MessageUi
import com.mhq.salati.splash.domain.usecases.GetStartDestinationUseCase
import com.mhq.salati.splash.presentation.contract.SplashContract
import com.mhq.salati.splash.presentation.mapper.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
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
                        themeMode = settings.themeMode.toUi(),
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
            is SplashContract.Intent.LanguageApplied -> completeLanguageSelection()
        }
    }

    // Step 1: save the choice, then ask the UI to apply the locale.
    // The UI answers with LanguageApplied, so the locale is always applied BEFORE
    // isLanguageSelected flips, and the app only recreates once.
    private fun selectInitialLanguage(languageUi: AppLanguageUi) = viewModelScope.launch {
        try {
            val language = languageUi.toDomain()
            updateLanguageUseCase(language)
            _effect.send(SplashContract.Effect.ApplyLanguage(language.code))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            showGenericError()
        }
    }

    // Step 2: the locale is applied, so the language counts as selected.
    private fun completeLanguageSelection() = viewModelScope.launch {
        try {
            setLanguageSelectedUseCase(true)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            showGenericError()
        }
    }

    private suspend fun showGenericError() {
        _effect.send(SplashContract.Effect.ShowError(MessageUi.SOMETHING_WENT_WRONG))
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