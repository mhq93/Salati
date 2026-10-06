package com.mhq.salati.location.domain.usecases

import com.mhq.salati.connectivity.domain.repo.ConnectivityChecker
import com.mhq.salati.location.domain.model.GeocodeResult
import com.mhq.salati.location.domain.model.SavedLocation
import com.mhq.salati.settings.domain.usecases.ObserveSettingsUseCase
import com.mhq.salati.shared.domain.Coordinates
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetNamedLocationUseCase @Inject constructor(
    private val connectivityChecker: ConnectivityChecker,
    private val observeSettings: ObserveSettingsUseCase,
    private val reverseGeocodeLocationUseCase: ReverseGeocodeLocationUseCase
) {
    /** [coordinates] with city and country names when they can be looked up, otherwise coordinates only. */
    suspend operator fun invoke(coordinates: Coordinates): SavedLocation {
        if (!connectivityChecker.isConnected()) return SavedLocation(coordinates)

        val languageCode = observeSettings().first().language.code
        return when (val geocode = reverseGeocodeLocationUseCase(coordinates, languageCode)) {
            is GeocodeResult.Found -> SavedLocation(coordinates, geocode.cityName, geocode.countryName)
            else -> SavedLocation(coordinates)
        }
    }
}