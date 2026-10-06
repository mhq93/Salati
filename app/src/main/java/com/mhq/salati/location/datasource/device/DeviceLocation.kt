package com.mhq.salati.location.datasource.device

/** A position as the GPS client reports it. */
data class DeviceLocation(
    val latitude: Double,
    val longitude: Double
)