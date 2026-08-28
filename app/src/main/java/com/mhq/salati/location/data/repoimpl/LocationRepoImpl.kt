package com.mhq.salati.location.data.repoimpl

import com.mhq.salati.location.data.datastore.LocationDataStore
import com.mhq.salati.location.domain.model.SavedLocation
import com.mhq.salati.location.domain.repo.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationRepoImpl @Inject constructor(
    private val locationDataStore: LocationDataStore
) : LocationRepository {

    override val savedLocation: Flow<SavedLocation?> =
        locationDataStore.savedLocation

    override suspend fun saveManualLocation(
        latitude: Double,
        longitude: Double,
        cityName: String?,
        countryName: String?
    ): SavedLocation {
        locationDataStore.save(
            latitude,
            longitude,
            cityName,
            countryName
        )

        return SavedLocation(
            cityName,
            countryName,
            latitude,
            longitude
        )
    }
}