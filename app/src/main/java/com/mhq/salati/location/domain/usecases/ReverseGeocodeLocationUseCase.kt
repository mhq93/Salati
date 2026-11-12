package com.mhq.salati.location.domain.usecases

import com.mhq.salati.location.domain.model.GeocodeResult
import com.mhq.salati.location.domain.repo.GeocoderProvider
import com.mhq.salati.shared.domain.Coordinates
import javax.inject.Inject

/**
 * Names the place at [coordinates].
 *
 * Thin pass-through over [GeocoderProvider.reverseGeocode].
 */
class ReverseGeocodeLocationUseCase @Inject constructor(
    private val geocoderProvider: GeocoderProvider
) {
    /**
     * @param coordinates the position to name
     * @param acceptLanguage BCP-47 language tag for the returned names, or null for the default
     * @return the names, not-found, or the lookup failure
     */
    suspend operator fun invoke(
        coordinates: Coordinates,
        acceptLanguage: String? = null
    ): GeocodeResult = geocoderProvider.reverseGeocode(coordinates, acceptLanguage)
}