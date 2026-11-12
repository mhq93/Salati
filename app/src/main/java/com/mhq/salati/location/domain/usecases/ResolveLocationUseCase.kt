package com.mhq.salati.location.domain.usecases

import com.mhq.salati.location.domain.model.LocationResolution
import com.mhq.salati.location.domain.model.GeocodeResult
import com.mhq.salati.location.domain.model.CoordinatesLookup
import com.mhq.salati.location.domain.model.SavedLocation
import com.mhq.salati.settings.domain.usecases.ObserveSettingsUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import javax.inject.Inject

/**
 * The full automatic "find where the user is" flow, as a one-shot [LocationResolution].
 *
 * Steps, in order:
 * check prerequisites (returning [LocationResolution.Blocked] with every missing one if any fail),
 * take the last GPS lookup state (re-mapping blockers onto the prerequisites so UI shows the fix),
 * then geocode the coordinates in the app language and save the place.
 * Coordinates are saved even when the geo-coder recognizes no place;
 * only a geo-coder error stops the save. Cancellation always propagates.
 */
class ResolveLocationUseCase @Inject constructor(
    private val checkLocationPrerequisitesUseCase: CheckLocationPrerequisitesUseCase,
    private val getCurrentCoordinatesUseCase: GetCurrentCoordinatesUseCase,
    private val reverseGeocodeLocationUseCase: ReverseGeocodeLocationUseCase,
    private val saveManualLocationUseCase: SaveManualLocationUseCase,
    private val observeSettingsUseCase: ObserveSettingsUseCase
) {
    /** @return what happened; a [LocationResolution.Resolved] place is already saved */
    suspend operator fun invoke(): LocationResolution {
        val prerequisites = checkLocationPrerequisitesUseCase()
        if (!prerequisites.areMet) return LocationResolution.Blocked(prerequisites)

        val coordinates = when (val lookup = getCurrentCoordinatesUseCase().last()) {
            is CoordinatesLookup.Found -> lookup.coordinates
            is CoordinatesLookup.ServicesDisabled ->
                return LocationResolution.Blocked(prerequisites.copy(servicesEnabled = false))
            is CoordinatesLookup.PermissionDenied ->
                return LocationResolution.Blocked(prerequisites.copy(permissionGranted = false))
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