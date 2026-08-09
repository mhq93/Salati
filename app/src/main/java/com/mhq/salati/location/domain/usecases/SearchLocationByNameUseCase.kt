package com.mhq.salati.location.domain.usecases

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.annotation.RequiresApi
import com.mhq.salati.locationpicker.presentation.contract.LocationPickerContract.LocationSearchResult
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.util.Locale
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.time.Duration.Companion.milliseconds

class SearchLocationByNameUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend operator fun invoke(query: String): List<LocationSearchResult> {
        if (query.isBlank()) return emptyList()
        val geocoder = Geocoder(context, Locale.getDefault())

        val addresses = withTimeout(8_000L.milliseconds) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocodeAsync(geocoder, query)
            } else {
                geocodeLegacy(geocoder, query)
            }
        }
        return addresses.toSearchResults()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private suspend fun geocodeAsync(geocoder: Geocoder, query: String): List<Address> =
        suspendCancellableCoroutine { continuation ->
            geocoder.getFromLocationName(query, 5) { addresses ->
                continuation.resume(addresses)
            }
        }

    private suspend fun geocodeLegacy(geocoder: Geocoder, query: String): List<Address> =
        withContext(Dispatchers.IO) {
            @Suppress("DEPRECATION")
            geocoder.getFromLocationName(query, 5) ?: emptyList()
        }

    private fun List<Address>.toSearchResults(): List<LocationSearchResult> =
        filter { it.hasLatitude() && it.hasLongitude() }
            .map { address ->
                LocationSearchResult(
                    displayName = address.toDisplayName(),
                    latitude = address.latitude,
                    longitude = address.longitude
                )
            }

    private fun Address.toDisplayName(): String =
        listOfNotNull(locality ?: subAdminArea ?: adminArea, countryName)
            .ifEmpty { listOfNotNull(featureName) }
            .joinToString(", ")
}