package com.mhq.salati.location.data.repoimpl

import com.mhq.salati.location.data.datastore.LocationDataStore
import com.mhq.salati.location.domain.model.SavedLocation
import com.mhq.salati.location.domain.repo.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//NOMINATIM…
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

//import com.mhq.salati.location.data.datastore.LocationDataStore
//import com.mhq.salati.location.domain.model.SavedLocation
//import com.mhq.salati.location.domain.repo.GeocoderProvider
//import com.mhq.salati.location.domain.repo.LocationProvider
//import com.mhq.salati.location.domain.repo.LocationRepository
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.withTimeout
//import javax.inject.Inject
//import kotlin.time.Duration.Companion.milliseconds
//
//class LocationRepoImpl @Inject constructor(
//    private val locationDataStore: LocationDataStore,
//    private val locationProvider: LocationProvider,
//    private val geocoderProvider: GeocoderProvider
//) : LocationRepository {
//
//    override val savedLocation: Flow<SavedLocation?> =
//        locationDataStore.savedLocation
//
//    override suspend fun saveManualLocation(
//        latitude: Double,
//        longitude: Double,
//        cityName: String?,
//        countryName: String?
//    ): SavedLocation {
//        locationDataStore.save(
//            latitude,
//            longitude,
//            cityName,
//            countryName
//        )
//
//        return SavedLocation(
//            cityName,
//            countryName,
//            latitude,
//            longitude
//        )
//    }
//}