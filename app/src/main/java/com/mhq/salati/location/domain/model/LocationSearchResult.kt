package com.mhq.salati.location.domain.model

import com.mhq.salati.shared.domain.Coordinates

/**
 * One hit from searching a place by name.
 *
 * @property displayName the place's name as the geo-coder formatted it
 * @property coordinates the place's position, ready to save as a location
 */
data class LocationSearchResult(
    val displayName: String,
    val coordinates: Coordinates
)