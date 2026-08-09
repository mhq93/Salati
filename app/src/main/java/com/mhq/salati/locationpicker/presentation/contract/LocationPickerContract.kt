package com.mhq.salati.locationpicker.presentation.contract

import com.mhq.salati.shared.presentation.components.UiText

object LocationPickerContract {

    data class State(
        val query: String = "",
        val isSearching: Boolean = false,
        val searchResults: List<LocationSearchResult> = emptyList(),
        val selectedLocation: SelectedLocation? = null,
        val isResolvingSelection: Boolean = false,
        val errorMessage: UiText? = null
    )

    data class LocationSearchResult(
        val displayName: String,
        val latitude: Double,
        val longitude: Double
    )

    data class SelectedLocation(
        val displayName: String?,
        val latitude: Double,
        val longitude: Double
    )

    sealed interface Intent {
        data class QueryChanged(val query: String) : Intent
        data object SearchClicked : Intent
        data class SearchResultClicked(val result: LocationSearchResult) : Intent
        data class MapPointSelected(val latitude: Double, val longitude: Double) : Intent
        data object ConfirmClicked : Intent
    }

    sealed interface Effect {
        data class ShowError(val message: UiText) : Effect
        data object LocationSaved : Effect
    }
}