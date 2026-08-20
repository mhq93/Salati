package com.mhq.salati.location.domain.repo

import com.mhq.salati.location.domain.GeocodeResult

interface GeocoderProvider {
    suspend fun reverseGeocode(
        latitude: Double,
        longitude: Double
    ): GeocodeResult
}