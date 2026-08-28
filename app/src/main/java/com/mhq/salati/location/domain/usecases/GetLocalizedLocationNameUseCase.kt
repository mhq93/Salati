package com.mhq.salati.location.domain.usecases

import com.mhq.salati.location.domain.GeocodeResult
import com.mhq.salati.location.domain.model.SavedLocation
import javax.inject.Inject

class GetLocalizedLocationNameUseCase @Inject constructor(
    private val reverseGeocodeLocation: ReverseGeocodeLocationUseCase
) {
    suspend operator fun invoke(
        savedLocation: SavedLocation,
        languageCode: String
    ): String? {
        val result = reverseGeocodeLocation(
            latitude = savedLocation.latitude,
            longitude = savedLocation.longitude,
            acceptLanguage = languageCode
        )
        return when (result) {
            is GeocodeResult.Found -> result.toDisplayName()
            else -> savedLocation.toDisplayName()
        }
    }

    private fun GeocodeResult.Found.toDisplayName(): String? = when {
        cityName != null && countryName != null -> "$cityName, $countryName"
        cityName != null -> cityName
        countryName != null -> countryName
        else -> null
    }

    private fun SavedLocation.toDisplayName(): String? = when {
        cityName != null && countryName != null -> "$cityName, $countryName"
        cityName != null -> cityName
        countryName != null -> countryName
        else -> null
    }
}