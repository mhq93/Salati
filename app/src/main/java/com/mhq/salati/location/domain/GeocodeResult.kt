package com.mhq.salati.location.domain

sealed interface GeocodeResult {
    data class Found(
        val cityName: String?,
        val countryName: String?
    ) : GeocodeResult

    data object NotFound : GeocodeResult

    data class Failed(val throwable: Throwable) : GeocodeResult
}