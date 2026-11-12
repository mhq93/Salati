package com.mhq.salati.shared.domain

/**
 * A geographic position in decimal degrees.
 *
 * Validated on construction: latitude must lie in [-90, 90] and longitude in [-180, 180].
 * This guarantees every [Coordinates] in the app is a usable location,
 * so consumers never re-check the ranges.
 *
 * @property latitude north-south position in degrees, from -90 (South Pole) to 90 (North Pole)
 * @property longitude east-west position in degrees, from -180 to 180
 * @throws IllegalArgumentException if either value is outside its valid range
 */
data class Coordinates(
    val latitude: Double,
    val longitude: Double
) {
    init {
        require(latitude in -90.0..90.0) {
            "Latitude must be between -90 and 90 but was $latitude"
        }

        require(longitude in -180.0..180.0) {
            "Longitude must be between -180 and 180 but was $longitude"
        }
    }
}