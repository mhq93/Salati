package com.mhq.salati.home.domain.usecases

import com.mhq.salati.R
import com.mhq.salati.connectivity.domain.repo.ConnectivityChecker
import com.mhq.salati.location.domain.GeocodeResult
import com.mhq.salati.location.domain.model.SavedLocation
import com.mhq.salati.location.domain.repo.LocationProvider
import com.mhq.salati.location.domain.usecases.ReverseGeocodeLocationUseCase
import com.mhq.salati.location.domain.usecases.SaveManualLocationUseCase
import com.mhq.salati.permissions.domain.PermissionChecker
import com.mhq.salati.permissions.location.LocationPermissionDelegate
import com.mhq.salati.shared.presentation.components.UiText
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

//NOMINATIM…
class ResolveLocationUseCase @Inject constructor(
    private val connectivityChecker: ConnectivityChecker,
    private val locationProvider: LocationProvider,
    private val permissionChecker: PermissionChecker,
    private val reverseGeocodeLocationUseCase: ReverseGeocodeLocationUseCase,
    private val saveManualLocationUseCase: SaveManualLocationUseCase,
    private val locationPermissionDelegate: LocationPermissionDelegate
) {

    sealed interface Result {
        data class Success(val savedLocation: SavedLocation) : Result
        data class Error(val message: UiText) : Result
        data object PromptPermission : Result
        data object PermissionRequired : Result
        data object PermanentlyDenied : Result
        data object ServicesDisabled : Result
    }

    suspend operator fun invoke(autoPromptShown: Boolean): Result {
        if (!connectivityChecker.isConnected()) {
            return Result.Error(UiText.Res(R.string.no_internet_connection))
        }
        if (!locationProvider.isLocationEnabled()) {
            locationPermissionDelegate.markServicesDisabled()
            return Result.ServicesDisabled
        }
        if (!permissionChecker.hasLocationPermission()) {
            if (locationPermissionDelegate.state.value.permanentlyDenied) {
                return Result.PermanentlyDenied
            }
            return if (autoPromptShown) {
                Result.PermissionRequired
            } else {
                Result.PromptPermission
            }
        }

        return try {
            val gpsLocation = withTimeout(5_000L.milliseconds) {
                locationProvider.getCurrentLocation()
            }
            val latitude = gpsLocation.latitude
            val longitude = gpsLocation.longitude

            when (val geocode = reverseGeocodeLocationUseCase(latitude, longitude)) {
                is GeocodeResult.Found -> {
                    saveManualLocationUseCase(
                        cityName = geocode.cityName,
                        countryName = geocode.countryName,
                        latitude = latitude,
                        longitude = longitude
                    )
                    Result.Success(
                        SavedLocation(
                            cityName = geocode.cityName,
                            countryName = geocode.countryName,
                            latitude = latitude,
                            longitude = longitude
                        )
                    )
                }
                is GeocodeResult.NotFound -> {
                    saveManualLocationUseCase(latitude, longitude, null, null)
                    Result.Success(
                        SavedLocation(
                            cityName = null,
                            countryName = null,
                            latitude = latitude,
                            longitude = longitude
                        )
                    )
                }
                is GeocodeResult.Failed -> {
                    Result.Error(UiText.Res(R.string.failed_to_get_location_name))
                }
            }
        } catch (e: TimeoutCancellationException) {
            Result.Error(UiText.Res(R.string.failed_to_get_location))
        } catch (e: SecurityException) {
            Result.PromptPermission
        }
    }
}