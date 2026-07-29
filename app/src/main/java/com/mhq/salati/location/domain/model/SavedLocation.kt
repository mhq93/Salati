package com.mhq.salati.location.domain.model

data class SavedLocation(
    val cityName: String? = null,
    val countryName: String? = null,
    val latitude: Double,
    val longitude: Double
)