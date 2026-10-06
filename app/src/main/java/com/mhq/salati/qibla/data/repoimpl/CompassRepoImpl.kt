package com.mhq.salati.qibla.data.repoimpl

import com.mhq.salati.qibla.datasource.device.CompassSample
import com.mhq.salati.qibla.datasource.device.RotationSensorDataSource
import com.mhq.salati.qibla.datasource.device.SensorAccuracy
import com.mhq.salati.qibla.domain.model.CompassAccuracy
import com.mhq.salati.qibla.domain.model.CompassReading
import com.mhq.salati.qibla.domain.repo.CompassRepository
import com.mhq.salati.shared.domain.Coordinates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CompassRepoImpl @Inject constructor(
    private val rotationSensorDataSource: RotationSensorDataSource
) : CompassRepository {

    override fun headings(coordinates: Coordinates): Flow<CompassReading> =
        rotationSensorDataSource
            .headings(coordinates.latitude, coordinates.longitude)
            .map { it.toDomain() }

    private fun CompassSample.toDomain() = CompassReading(
        headingDegrees = headingDegrees,
        accuracy = accuracy.toDomain()
    )

    private fun SensorAccuracy.toDomain() = when (this) {
        SensorAccuracy.HIGH -> CompassAccuracy.HIGH
        SensorAccuracy.MEDIUM -> CompassAccuracy.MEDIUM
        SensorAccuracy.LOW -> CompassAccuracy.LOW
        SensorAccuracy.UNRELIABLE -> CompassAccuracy.UNRELIABLE
    }
}