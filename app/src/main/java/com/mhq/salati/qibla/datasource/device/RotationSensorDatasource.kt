package com.mhq.salati.qibla.datasource.device

import kotlinx.coroutines.flow.Flow

interface RotationSensorDataSource {
    /** Fails with an exception when the device has no rotation vector sensor. */
    fun headings(latitude: Double, longitude: Double): Flow<CompassSample>
}