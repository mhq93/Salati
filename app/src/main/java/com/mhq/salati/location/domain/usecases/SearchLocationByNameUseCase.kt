package com.mhq.salati.location.domain.usecases

import com.mhq.salati.location.domain.model.LocationSearchResult
import com.mhq.salati.location.domain.repo.GeocoderProvider
import javax.inject.Inject

/**
 * Finds places matching a name, for the manual location picker.
 *
 * Blank queries are short-circuited to an empty list
 * so the geo-coder is never called with nothing to look for.
 */
class SearchLocationByNameUseCase @Inject constructor(
    private val geocoderProvider: GeocoderProvider
) {
    /**
     * @param query the text to search for
     * @param acceptLanguage BCP-47 language tag for the returned names, or null for the default
     * @return the hits, the best one first; empty for a blank query or no matches
     */
    suspend operator fun invoke(
        query: String,
        acceptLanguage: String? = null
    ): List<LocationSearchResult> {
        if (query.isBlank()) return emptyList()
        return geocoderProvider.searchByName(query, acceptLanguage)
    }
}