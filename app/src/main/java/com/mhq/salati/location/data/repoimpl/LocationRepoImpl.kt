package com.mhq.salati.location.data.repoimpl

import com.mhq.salati.location.data.GeocoderProvider
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
    private val locationProvider: LocationProvider,
    private val geocoderProvider: GeocoderProvider
) : LocationRepository {

    override val savedLocation: Flow<SavedLocation?> = locationDataStore.savedLocation

    override suspend fun fetchAndSaveLocation(): SavedLocation {
        val location = withTimeout(5_000L.milliseconds) {
            locationProvider.getCurrentLocation()
        }
        val (cityName, countryName) = geocoderProvider.reverseGeocode(location.latitude, location.longitude)

        locationDataStore.save(location.latitude, location.longitude, cityName, countryName)
        return SavedLocation(cityName, countryName, location.latitude, location.longitude)
    }
}