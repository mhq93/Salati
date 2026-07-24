package com.mhq.salati.data.repoimpl.location

import com.mhq.salati.data.local.location.LocationDataStore
import com.mhq.salati.domain.model.location.SavedLocation
import com.mhq.salati.data.location.LocationProvider
import com.mhq.salati.domain.repo.location.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withTimeout
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

class LocationRepositoryImpl @Inject constructor(
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