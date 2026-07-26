package com.mhq.salati.qibla.domain.model

data class CompassReading(
    val headingDegrees: Float,
    val accuracy: CompassAccuracy
)