package com.mhq.salati.location.domain.repo

interface GeocoderProvider {
    suspend fun reverseGeocode(latitude: Double, longitude: Double): Pair<String?, String?>
}