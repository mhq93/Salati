package com.mhq.salati.location.data

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AndroidLocationProvider @Inject constructor(
    @ApplicationContext private val context: Context
) : LocationProvider {
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    override fun isLocationEnabled(): Boolean {
        val locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Location = suspendCancellableCoroutine { continuation ->
        val cancellationTokenSource = CancellationTokenSource()
        var activeCallback: LocationCallback? = null

        continuation.invokeOnCancellation {
            cancellationTokenSource.cancel()
            activeCallback?.let {
                fusedLocationClient.removeLocationUpdates(it)
            }
        }

        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource.token
        )
            .addOnSuccessListener { location ->
                if (location != null) {
                    continuation.resume(location)
                } else {
                    activeCallback = requestSingleLocationUpdate(continuation)
                }
            }
            .addOnFailureListener { continuation.resumeWithException(it) }
    }

    @RequiresPermission(allOf = [
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ])
    private fun requestSingleLocationUpdate(
        cont: CancellableContinuation<Location>
    ): LocationCallback {
        val request = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            5000L
        )
            .setMaxUpdates(1)
            .setDurationMillis(15000L)
            .build()

        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                fusedLocationClient.removeLocationUpdates(this)
                if (!cont.isActive) return
                val location = result.lastLocation
                if (location != null) cont.resume(location)
                else cont.resumeWithException(IllegalStateException("LOCATION_UNAVAILABLE"))
            }

            override fun onLocationAvailability(availability: LocationAvailability) {
                if (!availability.isLocationAvailable) {
                    fusedLocationClient.removeLocationUpdates(this)
                    if (!cont.isActive) return
                    cont.resumeWithException(IllegalStateException("LOCATION_UNAVAILABLE"))
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(
            request,
            callback,
            Looper.getMainLooper()
        )
            .addOnFailureListener {
                if (cont.isActive) cont.resumeWithException(it)
            }

        return callback
    }
}