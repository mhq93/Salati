package com.mhq.salati.location.data.mapper

import com.mhq.salati.location.datasource.network.dto.NominatimAddressDto
import com.mhq.salati.location.datasource.network.dto.NominatimSearchDto
import com.mhq.salati.location.domain.model.LocationSearchResult
import com.mhq.salati.shared.domain.Coordinates

// Prioritizes the most specific place name Nominatim knows.
internal fun NominatimAddressDto.bestCityName(): String? =
    city
        ?: town
        ?: village
        ?: suburb
        ?: municipality
        ?: stateDistrict
        ?: county
        ?: state

internal fun NominatimSearchDto.toDomainOrNull(): LocationSearchResult? {
    val latitude = lat ?: return null
    val longitude = lon ?: return null

    return LocationSearchResult(
        displayName = constructDisplayName(address, displayName),
        coordinates = Coordinates(latitude, longitude)
    )
}

// Builds a clean "City, State, Country" string.
private fun constructDisplayName(address: NominatimAddressDto?, fallback: String): String {
    if (address == null) return fallback.split(",").firstOrNull()?.trim() ?: fallback

    val city = address.bestCityName()
    val state = address.state ?: address.stateDistrict
    val country = address.country

    // Non-null, non-blank parts without duplicates (for example when city and state are both "Cairo")
    val parts = listOfNotNull(city, state, country)
        .map { it.trim() }
        .filter { it.isNotBlank() }
        .distinct()

    return if (parts.isNotEmpty()) {
        parts.joinToString(", ")
    } else {
        // Falls back to the first part of the original display name if the address is useless
        fallback.split(",").firstOrNull()?.trim() ?: fallback
    }
}