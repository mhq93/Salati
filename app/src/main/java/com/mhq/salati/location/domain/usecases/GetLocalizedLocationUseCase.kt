package com.mhq.salati.location.domain.usecases

import com.mhq.salati.location.domain.model.GeocodeResult
import com.mhq.salati.location.domain.model.SavedLocation
import javax.inject.Inject

/**
 * The same place with its city and country names in [languageCode],
 * or null when the names can't be looked up.
 *
 * A lookup that succeeds but finds no names at all counts as a failure,
 * so callers never store an empty-looking "localization" over existing names.
 */
class GetLocalizedLocationUseCase @Inject constructor(
    private val reverseGeocodeLocation: ReverseGeocodeLocationUseCase
) {
    /**
     * @param savedLocation the place to name
     * @param languageCode the BCP-47 language to request names in
     * @return the place with new names, or null when the geocode failed,
     *   found nothing, or returned no names
     */
    suspend operator fun invoke(
        savedLocation: SavedLocation,
        languageCode: String
    ): SavedLocation? {
        val found = reverseGeocodeLocation(
            savedLocation.coordinates,
            languageCode
        ) as? GeocodeResult.Found ?: return null

        if (found.cityName == null && found.countryName == null)
            return null

        return savedLocation.copy(
            cityName = found.cityName,
            countryName = found.countryName
        )
    }
}