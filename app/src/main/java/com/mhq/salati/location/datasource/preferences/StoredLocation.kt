package com.mhq.salati.location.datasource.preferences

/** The saved place exactly as the DataStore keeps it. */
data class StoredLocation(
    val latitude: Double,
    val longitude: Double,
    val cityName: String?,
    val countryName: String?
)