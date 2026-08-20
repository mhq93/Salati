package com.mhq.salati.location.data

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import com.mhq.salati.location.domain.GeocodeResult
import com.mhq.salati.location.domain.repo.GeocoderProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlinx.io.IOException
import java.util.Locale
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.time.Duration.Companion.milliseconds

class AndroidGeocoderProvider @Inject constructor(
    @ApplicationContext private val context: Context
) : GeocoderProvider {

    private val geocoder by lazy { Geocoder(context, Locale.getDefault()) }

    override suspend fun reverseGeocode(latitude: Double, longitude: Double): GeocodeResult {
        if (!Geocoder.isPresent()) return GeocodeResult.NotFound

        return try {
            withTimeout(5_000L.milliseconds) {
                val address = fetchAddress(latitude, longitude)
                if (address == null) {
                    GeocodeResult.NotFound
                } else {
                    GeocodeResult.Found(
                        cityName = address.locality ?: address.subAdminArea,
                        countryName = address.countryName
                    )
                }
            }
        } catch (e: TimeoutCancellationException) {
            GeocodeResult.Failed(e)
        } catch (e: IOException) {
            GeocodeResult.Failed(e)
        }
    }

    private suspend fun fetchAddress(latitude: Double, longitude: Double): Address? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            suspendCancellableCoroutine { cont ->
                geocoder.getFromLocation(latitude, longitude, 1) { addresses ->
                    cont.resume(addresses.firstOrNull())
                }
            }
        } else {
            withContext(Dispatchers.IO) {
                @Suppress("DEPRECATION")
                geocoder.getFromLocation(latitude, longitude, 1)?.firstOrNull()
            }
        }
}