package com.mhq.salati.location.domain.repo

import com.mhq.salati.shared.domain.Coordinates

interface LocationProvider {
    fun isLocationEnabled(): Boolean
    suspend fun getCurrentLocation(): Coordinates
}