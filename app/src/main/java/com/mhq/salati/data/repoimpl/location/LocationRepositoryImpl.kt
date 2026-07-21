package com.mhq.salati.data.repoimpl.location

import com.mhq.salati.data.local.datastore.LocationDataStore
import com.mhq.salati.domain.model.location.SavedLocation
import com.mhq.salati.data.location.LocationProvider
import com.mhq.salati.domain.repo.location.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationDataStore: LocationDataStore,
    private val locationProvider: LocationProvider
) : LocationRepository {

    override val savedLocation: Flow<SavedLocation?> = locationDataStore.savedLocation

    override suspend fun fetchAndSaveLocation(): SavedLocation {
        val location = locationProvider.getCurrentLocation()
        locationDataStore.save(location.latitude, location.longitude)
        return SavedLocation(location.latitude, location.longitude)
    }
}