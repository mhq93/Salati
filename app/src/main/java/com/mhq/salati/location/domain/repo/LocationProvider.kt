package com.mhq.salati.location.domain.repo

import android.location.Location

interface LocationProvider {
    fun isLocationEnabled(): Boolean
    suspend fun getCurrentLocation(): Location
}