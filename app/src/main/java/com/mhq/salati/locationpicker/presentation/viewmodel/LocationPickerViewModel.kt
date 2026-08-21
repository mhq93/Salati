package com.mhq.salati.locationpicker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.location.domain.GeocodeResult
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
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
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

    private val _state = MutableStateFlow(LocationPickerContract.State())
    val state: StateFlow<LocationPickerContract.State> = _state.asStateFlow()

    // FIX: Channel instead of SharedFlow
    private val _effect = Channel<LocationPickerContract.Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    private val searchQueryFlow = MutableStateFlow("")
    private val immediateSearchFlow = kotlinx.coroutines.flow.MutableSharedFlow<String>()

    private var reverseGeocodeJob: Job? = null

    init {
        observeSearchQuery()
    }

    fun onIntent(intent: LocationPickerContract.Intent) {
        when (intent) {
            is LocationPickerContract.Intent.QueryChanged -> onQueryChanged(intent.query)
            is LocationPickerContract.Intent.SearchClicked -> executeManualSearch(_state.value.query)
            is LocationPickerContract.Intent.SearchResultClicked -> selectResult(intent.result)
            is LocationPickerContract.Intent.MapPointSelected -> selectMapPoint(intent.latitude, intent.longitude)
            is LocationPickerContract.Intent.ConfirmClicked -> confirmSelection()
        }
    }

    private fun onQueryChanged(query: String) {
        _state.update {
            it.copy(
                query = query,
                searchResults = emptyList(),
                isSearching = query.isNotBlank(),
                errorMessage = null
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
            .flatMapLatest { query ->
                flow {
                    if (query.isNotBlank()) {
                        emit(Result.success(searchLocationByName(query)))
                    } else {
                        emit(Result.success(emptyList()))
                    }
                }.catch { e ->
                    if (e is CancellationException) throw e
                    emit(Result.failure(e))
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
                        _effect.send(
                            LocationPickerContract.Effect.ShowError(
                                UiText.Raw("Search failed. Check your connection.")
                            )
                        )
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
                    selectedLocation = LocationPickerContract.SelectedLocation(null, latitude, longitude)
                )
            }
            when (val result = reverseGeocodeLocation(latitude, longitude)) {
                is GeocodeResult.Found -> {
                    val name = listOfNotNull(result.cityName, result.countryName)
                        .joinToString(", ")
                        .ifBlank { null }
                    _state.update {
                        it.copy(
                            isResolvingSelection = false,
                            selectedLocation = LocationPickerContract.SelectedLocation(name, latitude, longitude)
                        )
                    }
                }
                is GeocodeResult.NotFound -> {
                    _state.update {
                        it.copy(
                            isResolvingSelection = false,
                            selectedLocation = LocationPickerContract.SelectedLocation(null, latitude, longitude)
                        )
                    }
                }
                is GeocodeResult.Failed -> {
                    _state.update { it.copy(isResolvingSelection = false) }
                    _effect.send(
                        LocationPickerContract.Effect.ShowError(
                            UiText.Raw("Couldn't determine location name. Check your connection.")
                        )
                    )
                }
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
                _effect.send(LocationPickerContract.Effect.LocationSaved)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _effect.send(
                    LocationPickerContract.Effect.ShowError(
                        UiText.Raw("Couldn't save location. Try again.")
                    )
                )
            }
        }
    }
}