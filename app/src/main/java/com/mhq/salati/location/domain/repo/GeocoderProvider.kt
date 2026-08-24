package com.mhq.salati.location.domain.repo

import com.mhq.salati.location.domain.GeocodeResult

//NOMINATIM…
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

data class LocationSearchResult(
    val displayName: String,
    val latitude: Double,
    val longitude: Double
)

//import com.mhq.salati.location.domain.GeocodeResult
//
//interface GeocoderProvider {
//    suspend fun reverseGeocode(
//        latitude: Double,
//        longitude: Double
//    ): GeocodeResult
//}