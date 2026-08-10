package com.mhq.salati.locationpicker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.locationpicker.presentation.contract.LocationPickerContract.State
import com.mhq.salati.locationpicker.presentation.contract.LocationPickerContract.Intent
import com.mhq.salati.locationpicker.presentation.contract.LocationPickerContract.Effect
import com.mhq.salati.location.domain.usecases.ReverseGeocodeLocationUseCase
import com.mhq.salati.location.domain.usecases.SaveManualLocationUseCase
import com.mhq.salati.location.domain.usecases.SearchLocationByNameUseCase
import com.mhq.salati.locationpicker.presentation.contract.LocationPickerContract
import com.mhq.salati.shared.presentation.components.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class LocationPickerViewModel @Inject constructor(
    private val searchLocationByName: SearchLocationByNameUseCase,
    private val reverseGeocodeLocation: ReverseGeocodeLocationUseCase,
    private val saveManualLocation: SaveManualLocationUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<Effect>()
    val effect: SharedFlow<Effect> = _effect.asSharedFlow()

    private val searchQueryFlow = MutableStateFlow("")
    private val immediateSearchFlow = MutableSharedFlow<String>()

    private var reverseGeocodeJob: Job? = null

    init {
        observeSearchQuery()
    }

    fun onIntent(intent: Intent) {
        when (intent) {
            is Intent.QueryChanged -> onQueryChanged(intent.query)
            is Intent.SearchClicked -> executeManualSearch(_state.value.query)
            is Intent.SearchResultClicked -> selectResult(intent.result)
            is Intent.MapPointSelected -> selectMapPoint(intent.latitude, intent.longitude)
            is Intent.ConfirmClicked -> confirmSelection()
        }
    }

    private fun onQueryChanged(query: String) {
        _state.update {
            it.copy(
                query = query,
                searchResults = emptyList(),
                isSearching = query.isNotBlank()
            )
        }
        searchQueryFlow.value = query
    }

    private fun observeSearchQuery() {
        merge(
            searchQueryFlow
                .debounce(350.milliseconds)
                .distinctUntilChanged(),
            immediateSearchFlow
        )
            .onEach { query ->
                if (query.isNotBlank()) {
                    _state.update { it.copy(isSearching = true, errorMessage = null) }
                }
            }
            .flatMapLatest { query ->
                flow {
                    if (query.isNotBlank()) {
                        val results = searchLocationByName(query)
                        emit(Result.success(results))
                    } else {
                        emit(Result.success(emptyList()))
                    }
                }
            }
            .onEach { result ->
                result.fold(
                    onSuccess = { results ->
                        _state.update { it.copy(isSearching = false, searchResults = results) }
                    },
                    onFailure = {
                        _state.update {
                            it.copy(
                                isSearching = false,
                                errorMessage = UiText.Raw("Search failed. Check your connection.")
                            )
                        }
                        _effect.emit(Effect.ShowError(UiText.Raw("Search failed. Check your connection.")))
                    }
                )
            }
            .launchIn(viewModelScope)
    }

    private fun executeManualSearch(query: String) {
        if (query.isBlank()) return
        viewModelScope.launch { immediateSearchFlow.emit(query) }
    }

    private fun selectResult(result: LocationPickerContract.LocationSearchResult) {
        // Reset processing stream to prevent dangling emissions
        searchQueryFlow.value = ""
        _state.update {
            it.copy(
                selectedLocation = LocationPickerContract.SelectedLocation(
                    result.displayName,
                    result.latitude,
                    result.longitude
                ),
                searchResults = emptyList(),
                query = result.displayName
            )
        }
    }

    private fun selectMapPoint(latitude: Double, longitude: Double) {
        reverseGeocodeJob?.cancel()
        reverseGeocodeJob = viewModelScope.launch {
            _state.update {
                it.copy(
                    isResolvingSelection = true,
                    selectedLocation = LocationPickerContract.SelectedLocation(
                        null,
                        latitude,
                        longitude
                    )
                )
            }
            try {
                val name = reverseGeocodeLocation(latitude, longitude)
                _state.update {
                    it.copy(
                        isResolvingSelection = false,
                        selectedLocation = LocationPickerContract.SelectedLocation(
                            name,
                            latitude,
                            longitude
                        )
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _state.update { it.copy(isResolvingSelection = false) }
            }
        }
    }

    private fun confirmSelection() {
        val selected = _state.value.selectedLocation ?: return
        viewModelScope.launch {
            try {
                saveManualLocation(
                    latitude = selected.latitude,
                    longitude = selected.longitude,
                    cityName = selected.displayName,
                    countryName = null
                )
                _effect.emit(Effect.LocationSaved)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _effect.emit(Effect.ShowError(UiText.Raw("Couldn't save location. Try again.")))
            }
        }
    }
}