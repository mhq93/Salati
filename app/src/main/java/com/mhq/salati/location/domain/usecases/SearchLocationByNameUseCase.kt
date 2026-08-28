package com.mhq.salati.location.domain.usecases

import com.mhq.salati.location.domain.model.LocationSearchResult
import com.mhq.salati.location.domain.repo.GeocoderProvider
import javax.inject.Inject

class SearchLocationByNameUseCase @Inject constructor(
    private val geocoderProvider: GeocoderProvider
) {
    suspend operator fun invoke(
        query: String,
        acceptLanguage: String? = null
    ): List<LocationSearchResult> {
        if (query.isBlank()) return emptyList()
        return geocoderProvider.searchByName(query, acceptLanguage)
    }
}