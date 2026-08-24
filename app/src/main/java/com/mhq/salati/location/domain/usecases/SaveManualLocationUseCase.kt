package com.mhq.salati.location.domain.usecases

import com.mhq.salati.location.domain.model.SavedLocation
import com.mhq.salati.location.domain.repo.LocationRepository
import javax.inject.Inject

//NOMINATIM…
class SaveManualLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(
        latitude: Double,
        longitude: Double,
        cityName: String?,
        countryName: String?
    ): SavedLocation = locationRepository.saveManualLocation(
        latitude = latitude,
        longitude = longitude,
        cityName = cityName,
        countryName = countryName
    )
}

//import com.mhq.salati.location.domain.model.SavedLocation
//import com.mhq.salati.location.domain.repo.LocationRepository
//import javax.inject.Inject
//
//class SaveManualLocationUseCase @Inject constructor(
//    private val locationRepository: LocationRepository
//) {
//    suspend operator fun invoke(
//        latitude: Double,
//        longitude: Double,
//        cityName: String?,
//        countryName: String?
//    ): SavedLocation =
//        locationRepository.saveManualLocation(
//            latitude,
//            longitude,
//            cityName,
//            countryName
//        )
//}