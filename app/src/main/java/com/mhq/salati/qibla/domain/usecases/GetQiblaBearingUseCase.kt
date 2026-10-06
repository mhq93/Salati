package com.mhq.salati.qibla.domain.usecases

import com.mhq.salati.shared.domain.Coordinates
import javax.inject.Inject
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

class GetQiblaBearingUseCase @Inject constructor(){

    companion object {
        private const val KAABA_LATITUDE = 21.4225
        private const val KAABA_LONGITUDE = 39.8262
    }

    operator fun invoke(userLocation: Coordinates): Double {
        val lat1 = Math.toRadians(userLocation.latitude)
        val lat2 = Math.toRadians(KAABA_LATITUDE)
        val deltaLon = Math.toRadians(KAABA_LONGITUDE - userLocation.longitude)

        val y = sin(deltaLon) * cos(lat2)
        val x = cos(lat1) * sin(lat2) - sin(lat1) * cos(lat2) * cos(deltaLon)

        val bearingRadians = atan2(y, x)
        val bearingDegrees = Math.toDegrees(bearingRadians)

        return (bearingDegrees + 360) % 360
    }
}