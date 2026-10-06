package com.mhq.salati.location.datasource.network.service

import com.mhq.salati.location.datasource.network.dto.NominatimReverseDto
import com.mhq.salati.location.datasource.network.dto.NominatimSearchDto

/** The Nominatim web service.
 * Both calls throw when the request fails,
 * times out or the server says no. */
interface NominatimApi {
    suspend fun reverse(
        latitude: Double,
        longitude: Double,
        acceptLanguage: String?
    ): NominatimReverseDto

    suspend fun search(
        query: String,
        acceptLanguage: String?
    ): List<NominatimSearchDto>
}

class NominatimHttpException(val statusCode: Int) : Exception("HTTP $statusCode")