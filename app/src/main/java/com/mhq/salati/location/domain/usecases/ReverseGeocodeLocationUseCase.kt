package com.mhq.salati.location.domain.usecases

import com.mhq.salati.location.domain.GeocodeResult
import com.mhq.salati.location.domain.repo.GeocoderProvider
import javax.inject.Inject

class ReverseGeocodeLocationUseCase @Inject constructor(
    private val geocoderProvider: GeocoderProvider
) {
    suspend operator fun invoke(latitude: Double, longitude: Double): GeocodeResult =
        geocoderProvider.reverseGeocode(latitude, longitude)
}