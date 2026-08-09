package com.mhq.salati.location.domain.usecases

import com.mhq.salati.location.domain.repo.GeocoderProvider
import javax.inject.Inject

class ReverseGeocodeLocationUseCase @Inject constructor(
    private val geocoderProvider: GeocoderProvider
) {
    suspend operator fun invoke(latitude: Double, longitude: Double): String? {
        val (cityName, countryName) = geocoderProvider.reverseGeocode(latitude, longitude)
        return listOfNotNull(cityName, countryName).joinToString(", ").ifBlank { null }
    }
}