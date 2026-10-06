package com.mhq.salati.location.domain.model

import com.mhq.salati.shared.domain.Coordinates

data class LocationSearchResult(
    val displayName: String,
    val coordinates: Coordinates
)