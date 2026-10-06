package com.mhq.salati.home.domain.usecases

import com.mhq.salati.connectivity.domain.repo.ConnectivityChecker
import com.mhq.salati.home.domain.model.LocationResolution
import com.mhq.salati.location.domain.model.GeocodeResult
import com.mhq.salati.location.domain.model.CoordinatesLookup
import com.mhq.salati.location.domain.model.SavedLocation
import com.mhq.salati.location.domain.usecases.GetCurrentCoordinatesUseCase
import com.mhq.salati.location.domain.usecases.ReverseGeocodeLocationUseCase
import com.mhq.salati.location.domain.usecases.SaveManualLocationUseCase
import com.mhq.salati.settings.domain.usecases.ObserveSettingsUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import javax.inject.Inject

class ResolveLocationUseCase @Inject constructor(
    private val connectivityChecker: ConnectivityChecker,
    private val getCurrentCoordinatesUseCase: GetCurrentCoordinatesUseCase,
    private val reverseGeocodeLocationUseCase: ReverseGeocodeLocationUseCase,
    private val saveManualLocationUseCase: SaveManualLocationUseCase,
    private val observeSettingsUseCase: ObserveSettingsUseCase
) {

    suspend operator fun invoke(): LocationResolution {
        if (!connectivityChecker.isConnected()) return LocationResolution.NoInternet

        val coordinates = when (val lookup = getCurrentCoordinatesUseCase().last()) {
            is CoordinatesLookup.Found -> lookup.coordinates
            is CoordinatesLookup.ServicesDisabled -> return LocationResolution.ServicesDisabled
            is CoordinatesLookup.PermissionDenied -> return LocationResolution.PermissionDenied
            is CoordinatesLookup.TimedOut,
            is CoordinatesLookup.Failed,
            is CoordinatesLookup.Locating -> return LocationResolution.LocationUnavailable
        }

        return try {
            val lang = observeSettingsUseCase().first().language.code
            when (val geocode = reverseGeocodeLocationUseCase(coordinates, lang)) {
                is GeocodeResult.Found -> {
                    val location = SavedLocation(coordinates, geocode.cityName, geocode.countryName)
                    saveManualLocationUseCase(location)
                    LocationResolution.Resolved(location)
                }

                is GeocodeResult.NotFound -> {
                    val location = SavedLocation(coordinates)
                    saveManualLocationUseCase(location)
                    LocationResolution.Resolved(location)
                }

                is GeocodeResult.Failed -> LocationResolution.GeocodingFailed
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            LocationResolution.LocationUnavailable
        }
    }
}

//import com.mhq.salati.R
//import com.mhq.salati.connectivity.domain.repo.ConnectivityChecker
//import com.mhq.salati.location.domain.model.GeocodeResult
//import com.mhq.salati.location.domain.model.SavedLocation
//import com.mhq.salati.location.domain.repo.LocationProvider
//import com.mhq.salati.location.domain.usecases.ReverseGeocodeLocationUseCase
//import com.mhq.salati.location.domain.usecases.SaveManualLocationUseCase
//import com.mhq.salati.permissions.domain.repo.PermissionChecker
//import com.mhq.salati.permissions.presentation.LocationPermissionDelegate
//import com.mhq.salati.settings.domain.usecases.ObserveSettingsUseCase
//import com.mhq.salati.shared.ui.UiText
//import kotlinx.coroutines.CancellationException
//import kotlinx.coroutines.TimeoutCancellationException
//import kotlinx.coroutines.withTimeout
//import javax.inject.Inject
//import kotlin.time.Duration.Companion.milliseconds
//import kotlinx.coroutines.flow.first
//
//class ResolveLocationUseCase @Inject constructor(
//    private val locationProvider: LocationProvider,
//    private val permissionChecker: PermissionChecker,
//    private val connectivityChecker: ConnectivityChecker,
//    private val locationPermissionDelegate: LocationPermissionDelegate,
//    private val reverseGeocodeLocationUseCase: ReverseGeocodeLocationUseCase,
//    private val saveManualLocationUseCase: SaveManualLocationUseCase,
//    private val observeSettingsUseCase: ObserveSettingsUseCase
//) {
//
//    sealed interface Result {
//        data class Success(val savedLocation: SavedLocation) : Result
//        data class Error(val message: UiText) : Result
//        data object PromptPermission : Result
//        data object PermissionRequired : Result
//        data object PermanentlyDenied : Result
//        data object ServicesDisabled : Result
//    }
//
//    suspend operator fun invoke(autoPromptShown: Boolean): Result {
//        if (!connectivityChecker.isConnected()) {
//            return Result.Error(UiText.Res(R.string.no_internet_connection))
//        }
//        if (!locationProvider.isLocationEnabled()) {
//            locationPermissionDelegate.markServicesDisabled()
//            return Result.ServicesDisabled
//        }
//        if (!permissionChecker.hasLocationPermission()) {
//            if (locationPermissionDelegate.state.value.permanentlyDenied) {
//                return Result.PermanentlyDenied
//            }
//            return if (autoPromptShown) {
//                Result.PermissionRequired
//            } else {
//                Result.PromptPermission
//            }
//        }
//
//        return try {
//            val gpsLocation = withTimeout(5_000L.milliseconds) {
//                locationProvider.getCurrentLocation()
//            }
//            val latitude = gpsLocation.latitude
//            val longitude = gpsLocation.longitude
//
//            val lang = observeSettingsUseCase().first().language.code
//            when (val geocode = reverseGeocodeLocationUseCase(latitude, longitude, lang)) {
//                is GeocodeResult.Found -> {
//                    saveManualLocationUseCase(
//                        cityName = geocode.cityName,
//                        countryName = geocode.countryName,
//                        latitude = latitude,
//                        longitude = longitude
//                    )
//                    Result.Success(
//                        SavedLocation(
//                            cityName = geocode.cityName,
//                            countryName = geocode.countryName,
//                            latitude = latitude,
//                            longitude = longitude
//                        )
//                    )
//                }
//                is GeocodeResult.NotFound -> {
//                    saveManualLocationUseCase(latitude, longitude, null, null)
//                    Result.Success(
//                        SavedLocation(
//                            cityName = null,
//                            countryName = null,
//                            latitude = latitude,
//                            longitude = longitude
//                        )
//                    )
//                }
//                is GeocodeResult.Failed -> {
//                    Result.Error(UiText.Res(R.string.failed_to_get_location_name))
//                }
//            }
//        } catch (e: TimeoutCancellationException) {
//            Result.Error(UiText.Res(R.string.failed_to_get_location))
//        } catch (e: SecurityException) {
//            Result.PromptPermission
//        } catch (e: CancellationException) {
//            throw e
//        } catch (e: Exception) {
//            Result.Error(UiText.Res(R.string.failed_to_get_location))
//        }
//    }
//}