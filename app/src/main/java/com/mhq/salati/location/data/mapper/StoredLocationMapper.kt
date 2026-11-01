package com.mhq.salati.location.data.mapper

import com.mhq.salati.location.datasource.preferences.StoredLocation
import com.mhq.salati.location.domain.model.SavedLocation
import com.mhq.salati.shared.domain.Coordinates

/** A stored pair that isn't a valid coordinate counts as "no saved location" instead of crashing. */
fun StoredLocation.toDomain(): SavedLocation? =
    runCatching { Coordinates(latitude, longitude) }.getOrNull()?.let { coordinates ->
        SavedLocation(
            coordinates = coordinates,
            cityName = cityName,
            countryName = countryName
        )
    }

fun SavedLocation.toStored(): StoredLocation = StoredLocation(
    latitude = coordinates.latitude,
    longitude = coordinates.longitude,
    cityName = cityName,
    countryName = countryName
)