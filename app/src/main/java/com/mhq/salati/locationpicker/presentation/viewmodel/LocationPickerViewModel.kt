package com.mhq.salati.locationpicker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.R
import com.mhq.salati.location.domain.model.GeocodeResult
import com.mhq.salati.location.domain.model.LocationSearchResult
import com.mhq.salati.location.domain.model.SavedLocation
import com.mhq.salati.location.domain.usecases.ReverseGeocodeLocationUseCase
import com.mhq.salati.location.domain.usecases.SaveManualLocationUseCase
import com.mhq.salati.location.domain.usecases.SearchLocationByNameUseCase
import com.mhq.salati.locationpicker.presentation.contract.LocationPickerContract
import com.mhq.salati.settings.domain.usecases.ObserveSettingsUseCase
import com.mhq.salati.shared.domain.Coordinates
import com.mhq.salati.shared.ui.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
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
    private val saveManualLocation: SaveManualLocationUseCase,
    private val observeSettings: ObserveSettingsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LocationPickerContract.State())
    val state: StateFlow<LocationPickerContract.State> = _state.asStateFlow()

    private val _effect = Channel<LocationPickerContract.Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    private val searchQueryFlow = MutableStateFlow("")
    private val immediateSearchFlow = MutableSharedFlow<String>()
    private var lastManualSearchTimeMs = 0L
    private val SEARCH_COOLDOWN_MS = 1000L
    private var reverseGeocodeJob: Job? = null

    init {
        observeSearchQuery()
    }

    fun onIntent(intent: LocationPickerContract.Intent) {
        when (intent) {
            is LocationPickerContract.Intent.QueryChanged -> onQueryChanged(intent.query)
            is LocationPickerContract.Intent.SearchClicked -> executeManualSearch(_state.value.query)
            is LocationPickerContract.Intent.SearchResultClicked -> selectResult(intent.result)
            is LocationPickerContract.Intent.MapPointSelected -> selectMapPoint(
                intent.latitude,
                intent.longitude
            )

            is LocationPickerContract.Intent.ConfirmClicked -> confirmSelection()
        }
    }

    private fun onQueryChanged(query: String) {
        // Typing never triggers a request; the user searches explicitly.
        _state.update {
            it.copy(
                query = query,
                searchResults = emptyList(),
                isSearching = false,
                errorMessage = null
            )
        }
    }

    private fun observeSearchQuery() {
        immediateSearchFlow
            .flatMapLatest { query ->
                flow {
                    val lang = currentLanguageCode()
                    emit(Result.success(searchLocationByName(query, lang)))
                }.catch { e ->
                    if (e is CancellationException) throw e
                    emit(Result.failure(e))
                }
            }
            .onEach { result ->
                result.fold(
                    onSuccess = { results ->
                        _state.update {
                            it.copy(
                                isSearching = false,
                                searchResults = results.toContractResults()
                            )
                        }
                    },
                    onFailure = {
                        val message = UiText.Res(R.string.search_failed_check_connection)
                        _state.update {
                            it.copy(
                                isSearching = false,
                                errorMessage = message
                            )
                        }
                        _effect.send(LocationPickerContract.Effect.ShowError(message))
                    }
                )
            }
            .launchIn(viewModelScope)
    }

    private fun executeManualSearch(query: String) {
        if (query.isBlank()) return

        val currentTime = System.currentTimeMillis()

        // Nominatim's usage policy allows at most one request per second.
        if (currentTime - lastManualSearchTimeMs < SEARCH_COOLDOWN_MS) {
            return
        }

        lastManualSearchTimeMs = currentTime
        _state.update { it.copy(isSearching = true, errorMessage = null) }
        viewModelScope.launch {
            immediateSearchFlow.emit(query)
        }
    }

    private fun selectResult(result: LocationPickerContract.LocationSearchResult) {
        _state.update {
            it.copy(
                selectedLocation = LocationPickerContract.SelectedLocation(
                    displayName = result.displayName,
                    latitude = result.latitude,
                    longitude = result.longitude
                ),
                searchResults = emptyList(),
                query = result.displayName,
                mapCenterLat = result.latitude,
                mapCenterLng = result.longitude,
                mapZoom = 12
            )
        }
    }

    // Nominatim: Autocomplete search stopped;
    // I may stretch the search duration to avoid 1 request per minute limit
    //    private fun onQueryChanged(query: String) {
    //        _state.update {
    //            it.copy(
    //                query = query,
    //                searchResults = emptyList(),
    //                isSearching = query.isNotBlank(),
    //                errorMessage = null
    //            )
    //        }
    //        searchQueryFlow.value = query
    //    }
    //
    //    private fun observeSearchQuery() {
    //        merge(
    //            searchQueryFlow
    //                .debounce(350.milliseconds)
    //                .distinctUntilChanged(),
    //            immediateSearchFlow
    //        )
    //            .flatMapLatest { query ->
    //                flow {
    //                    if (query.isNotBlank()) {
    //                        val lang = currentLanguageCode()
    //                        emit(Result.success(searchLocationByName(query, lang)))
    //                    } else {
    //                        emit(Result.success(emptyList()))
    //                    }
    //                }.catch { e ->
    //                    if (e is CancellationException) throw e
    //                    emit(Result.failure(e))
    //                }
    //            }
    //            .onEach { result ->
    //                result.fold(
    //                    onSuccess = { results ->
    //                        _state.update {
    //                            it.copy(
    //                                isSearching = false,
    //                                searchResults = results.toContractResults()
    //                            )
    //                        }
    //                    },
    //                    onFailure = {
    //                        val message = UiText.Res(R.string.search_failed_check_connection)
    //                        _state.update {
    //                            it.copy(
    //                                isSearching = false,
    //                                errorMessage = message
    //                            )
    //                        }
    //                        _effect.send(LocationPickerContract.Effect.ShowError(message))
    //                    }
    //                )
    //            }
    //            .launchIn(viewModelScope)
    //    }
    //
    //    private fun executeManualSearch(query: String) {
    //        if (query.isBlank()) return
    //
    //        val currentTime = System.currentTimeMillis()
    //
    //        if (currentTime - lastManualSearchTimeMs < SEARCH_COOLDOWN_MS) {
    //            return
    //        }
    //
    //        lastManualSearchTimeMs = currentTime
    //        viewModelScope.launch {
    //            immediateSearchFlow.emit(query)
    //        }
    //    }
    //
    //    private fun selectResult(result: LocationPickerContract.LocationSearchResult) {
    //        searchQueryFlow.value = ""
    //        _state.update {
    //            it.copy(
    //                selectedLocation = LocationPickerContract.SelectedLocation(
    //                    displayName = result.displayName,
    //                    latitude = result.latitude,
    //                    longitude = result.longitude
    //                ),
    //                searchResults = emptyList(),
    //                query = result.displayName,
    //                mapCenterLat = result.latitude,
    //                mapCenterLng = result.longitude,
    //                mapZoom = 12
    //            )
    //        }
    //    }

    private fun selectMapPoint(latitude: Double, longitude: Double) {
        reverseGeocodeJob?.cancel()
        reverseGeocodeJob = viewModelScope.launch {
            _state.update {
                it.copy(
                    isResolvingSelection = true,
                    selectedLocation = LocationPickerContract.SelectedLocation(
                        displayName = null,
                        latitude = latitude,
                        longitude = longitude
                    ),
                    mapCenterLat = latitude,
                    mapCenterLng = longitude,
                    mapZoom = 12
                )
            }
            val lang = currentLanguageCode()
            when (val result = reverseGeocodeLocation(Coordinates(latitude, normalizeLongitude(longitude)), lang)) {
                is GeocodeResult.Found -> {
                    val name = listOfNotNull(result.cityName, result.countryName)
                        .joinToString(", ")
                        .ifBlank { null }
                    _state.update {
                        it.copy(
                            isResolvingSelection = false,
                            selectedLocation = LocationPickerContract.SelectedLocation(
                                displayName = name,
                                latitude = latitude,
                                longitude = longitude
                            )
                        )
                    }
                }

                is GeocodeResult.NotFound -> {
                    _state.update {
                        it.copy(
                            isResolvingSelection = false,
                            selectedLocation = LocationPickerContract.SelectedLocation(
                                displayName = null,
                                latitude = latitude,
                                longitude = longitude
                            )
                        )
                    }
                }

                is GeocodeResult.Failed -> {
                    _state.update { it.copy(isResolvingSelection = false) }
                    _effect.send(
                        LocationPickerContract.Effect.ShowError(
                            UiText.Res(R.string.couldn_t_determine_location_name)
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
                val (city, country) = parseDisplayName(selected.displayName)
                saveManualLocation(
                    SavedLocation(
                        coordinates = Coordinates(selected.latitude, normalizeLongitude(selected.longitude)),
                        cityName = city,
                        countryName = country
                    )
                )
                _effect.send(LocationPickerContract.Effect.LocationSaved)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _effect.send(
                    LocationPickerContract.Effect.ShowError(
                        UiText.Res(R.string.couldn_t_save_location)
                    )
                )
            }
        }
    }

    // Panning the map around the world can push longitude past ±180°; Coordinates only accepts values inside that range.
    private fun normalizeLongitude(longitude: Double): Double = ((longitude + 540.0) % 360.0) - 180.0

    private fun parseDisplayName(displayName: String?): Pair<String?, String?> {
        if (displayName == null) return null to null
        val parts = displayName.split(", ", limit = 2)
        return when (parts.size) {
            2 -> parts[0] to parts[1]
            1 -> parts[0] to null
            else -> null to null
        }
    }

    private suspend fun currentLanguageCode(): String {
        return observeSettings().first().language.code
    }

    private fun List<LocationSearchResult>.toContractResults(): List<LocationPickerContract.LocationSearchResult> =
        map { result ->
            LocationPickerContract.LocationSearchResult(
                displayName = result.displayName,
                latitude = result.coordinates.latitude,
                longitude = result.coordinates.longitude
            )
        }
}