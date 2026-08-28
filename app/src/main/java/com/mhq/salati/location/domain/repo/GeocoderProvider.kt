package com.mhq.salati.location.domain.repo

import com.mhq.salati.location.domain.GeocodeResult
import com.mhq.salati.location.domain.model.LocationSearchResult

interface GeocoderProvider {
    suspend fun reverseGeocode(
        latitude: Double,
        longitude: Double,
        acceptLanguage: String? = null
    ): GeocodeResult

    suspend fun searchByName(
        query: String,
        acceptLanguage: String? = null
    ): List<LocationSearchResult>
}