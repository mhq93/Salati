package com.mhq.salati.location.data.repoimpl

import com.mhq.salati.location.datasource.device.GpsDataSource
import com.mhq.salati.location.domain.repo.LocationProvider
import com.mhq.salati.shared.domain.Coordinates
import javax.inject.Inject

class LocationProviderImpl @Inject constructor(
    private val gpsDataSource: GpsDataSource
) : LocationProvider {

    override fun isLocationEnabled(): Boolean = gpsDataSource.isLocationEnabled()

    override suspend fun getCurrentLocation(): Coordinates {
        val location = gpsDataSource.getCurrentLocation()
        return Coordinates(location.latitude, location.longitude)
    }
}