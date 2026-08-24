//package com.mhq.salati.locationpicker.domain.usecases
//
//import com.mhq.salati.location.domain.repo.LocationRepository
//import com.mhq.salati.locationpicker.domain.model.Location
//import javax.inject.Inject
//
////NEW OSMDroid REPLACE...
//class SaveLocationUseCase @Inject constructor(
//    private val locationRepository: LocationRepository
//) {
//    suspend operator fun invoke(location: Location): Result<Unit> {
//        return try {
//            locationRepository.saveLocation(location)
//            Result.success(Unit)
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }
//}