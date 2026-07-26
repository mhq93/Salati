package com.mhq.salati.location.data.repoimpl

import com.mhq.salati.location.data.local.LocationDataStore
import com.mhq.salati.location.data.LocationProvider
import com.mhq.salati.location.domain.model.SavedLocation
import com.mhq.salati.location.domain.repo.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withTimeout
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

class LocationRepoImpl @Inject constructor(
    private val locationDataStore: LocationDataStore,
    private val locationProvider: LocationProvider
) : LocationRepository {

    override val savedLocation: Flow<SavedLocation?> = locationDataStore.savedLocation

    override suspend fun fetchAndSaveLocation(): SavedLocation {
        val location = withTimeout(5_000L.milliseconds) {
            locationProvider.getCurrentLocation()
        }
        locationDataStore.save(location.latitude, location.longitude)
        return SavedLocation(location.latitude, location.longitude)
    }
}