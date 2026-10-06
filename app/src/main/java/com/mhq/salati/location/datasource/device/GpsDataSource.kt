package com.mhq.salati.location.datasource.device

interface GpsDataSource {
    fun isLocationEnabled(): Boolean
    suspend fun getCurrentLocation(): DeviceLocation
}