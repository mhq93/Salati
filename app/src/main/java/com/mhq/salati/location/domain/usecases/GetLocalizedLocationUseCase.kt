package com.mhq.salati.location.domain.usecases

import com.mhq.salati.location.domain.model.GeocodeResult
import com.mhq.salati.location.domain.model.SavedLocation
import javax.inject.Inject

class GetLocalizedLocationUseCase @Inject constructor(
    private val reverseGeocodeLocation: ReverseGeocodeLocationUseCase
) {
    /**
     * The same place with its city and country names in [languageCode].
     * Returns [savedLocation] unchanged when the names can't be looked up.
     */
    suspend operator fun invoke(
        savedLocation: SavedLocation,
        languageCode: String
    ): SavedLocation {
        val found = reverseGeocodeLocation(savedLocation.coordinates, languageCode) as? GeocodeResult.Found
            ?: return savedLocation

        if (found.cityName == null && found.countryName == null) return savedLocation

        return savedLocation.copy(cityName = found.cityName, countryName = found.countryName)
    }
}