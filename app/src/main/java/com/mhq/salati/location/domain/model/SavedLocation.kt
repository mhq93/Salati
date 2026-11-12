package com.mhq.salati.location.domain.model

import com.mhq.salati.shared.domain.Coordinates

/**
 * The location the app prays by: coordinates plus optional place names.
 *
 * Names may be absent right after a GPS-only resolve,
 * before geocoding or localization has filled them in;
 * in that case only [coordinates] are set.
 *
 * @property coordinates the position prayer times are calculated for
 * @property cityName the city part of the display name, when known
 * @property countryName the country part of the display name, when known
 */
data class SavedLocation(
    val coordinates: Coordinates,
    val cityName: String? = null,
    val countryName: String? = null
) {
    /** "City, Country" or whichever part exists;
     * null when the place has no name yet. */
    val displayName: String?
        get() = when {
            cityName != null && countryName != null -> "$cityName, $countryName"
            cityName != null -> cityName
            countryName != null -> countryName
            else -> null
        }
}