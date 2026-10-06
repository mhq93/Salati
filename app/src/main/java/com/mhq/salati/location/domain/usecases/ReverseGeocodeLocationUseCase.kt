package com.mhq.salati.location.domain.usecases

import com.mhq.salati.location.domain.model.GeocodeResult
import com.mhq.salati.location.domain.repo.GeocoderProvider
import com.mhq.salati.shared.domain.Coordinates
import javax.inject.Inject

class ReverseGeocodeLocationUseCase @Inject constructor(
    private val geocoderProvider: GeocoderProvider
) {
    suspend operator fun invoke(
        coordinates: Coordinates,
        acceptLanguage: String? = null
    ): GeocodeResult = geocoderProvider.reverseGeocode(coordinates, acceptLanguage)
}