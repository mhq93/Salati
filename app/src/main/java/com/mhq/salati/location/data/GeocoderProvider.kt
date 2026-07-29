package com.mhq.salati.location.data

interface GeocoderProvider {
    suspend fun reverseGeocode(latitude: Double, longitude: Double): Pair<String?, String?>
}