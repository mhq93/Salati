package com.mhq.salati.location.domain.repo

import com.mhq.salati.location.domain.model.GeocodeResult
import com.mhq.salati.location.domain.model.LocationSearchResult
import com.mhq.salati.shared.domain.Coordinates

/**
 * Place-name lookups, implemented in Data over the platform geo-coder or a web service.
 *
 * [acceptLanguage] is a BCP-47 language tag forwarded to the geo-coder
 * so names come back in the app's language; null means the geo-coder's default.
 */
interface GeocoderProvider {
    /**
     * Names the place at [coordinates].
     *
     * @return the names, [GeocodeResult.NotFound] when the place is unknown,
     *   or [GeocodeResult.Failed] when the lookup itself errors
     */
    suspend fun reverseGeocode(
        coordinates: Coordinates,
        acceptLanguage: String? = null
    ): GeocodeResult

    /**
     * Finds places matching [query].
     *
     * @return the hits, the best one first; empty when nothing matches
     */
    suspend fun searchByName(
        query: String,
        acceptLanguage: String? = null
    ): List<LocationSearchResult>
}