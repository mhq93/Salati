package com.mhq.salati.domain.model.qibla

data class CompassReading(
    val headingDegrees: Float,
    val accuracy: CompassAccuracy
)