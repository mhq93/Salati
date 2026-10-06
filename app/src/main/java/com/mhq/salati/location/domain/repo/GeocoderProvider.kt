package com.mhq.salati.location.domain.repo

import com.mhq.salati.location.domain.model.GeocodeResult
import com.mhq.salati.location.domain.model.LocationSearchResult
import com.mhq.salati.shared.domain.Coordinates

interface GeocoderProvider {
    suspend fun reverseGeocode(
        coordinates: Coordinates,
        acceptLanguage: String? = null
    ): GeocodeResult

    suspend fun searchByName(
        query: String,
        acceptLanguage: String? = null
    ): List<LocationSearchResult>
}