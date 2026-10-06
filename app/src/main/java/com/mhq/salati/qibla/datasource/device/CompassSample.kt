package com.mhq.salati.qibla.datasource.device

/** A heading measured against true north, with the sensor's accuracy at that moment. */
data class CompassSample(
    val headingDegrees: Float,
    val accuracy: SensorAccuracy
)