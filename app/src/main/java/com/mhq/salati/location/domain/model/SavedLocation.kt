package com.mhq.salati.location.domain.model

import com.mhq.salati.shared.domain.Coordinates

data class SavedLocation(
    val coordinates: Coordinates,
    val cityName: String? = null,
    val countryName: String? = null
) {
    /** "City, Country",
     * or whichever part exists;
     * null when the place has no name yet. */
    val displayName: String?
        get() = when {
            cityName != null && countryName != null -> "$cityName, $countryName"
            cityName != null -> cityName
            countryName != null -> countryName
            else -> null
        }
}