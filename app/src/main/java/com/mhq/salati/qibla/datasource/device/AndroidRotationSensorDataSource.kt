package com.mhq.salati.qibla.datasource.device

import android.content.Context
import android.hardware.GeomagneticField
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AndroidRotationSensorDataSource @Inject constructor(
    @ApplicationContext context: Context
) : RotationSensorDataSource {

    private val sensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val rotationSensor: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

    private val magneticFieldSensor: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

    override fun headings(latitude: Double, longitude: Double): Flow<CompassSample> = callbackFlow {
        if (rotationSensor == null) {
            close(RotationSensorUnavailableException())
            return@callbackFlow
        }

        val rotationMatrix = FloatArray(9)
        val orientationAngles = FloatArray(3)
        var currentAccuracy = SensorAccuracy.HIGH

        val rotationListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
                SensorManager.getOrientation(rotationMatrix, orientationAngles)

                val azimuthRadians = orientationAngles[0]
                var magneticAzimuth = Math.toDegrees(azimuthRadians.toDouble()).toFloat()
                if (magneticAzimuth < 0) magneticAzimuth += 360f

                val declination = GeomagneticField(
                    latitude.toFloat(),
                    longitude.toFloat(),
                    0f,
                    System.currentTimeMillis()
                ).declination

                var trueAzimuth = magneticAzimuth + declination
                if (trueAzimuth < 0) trueAzimuth += 360f
                if (trueAzimuth >= 360) trueAzimuth -= 360f

                trySend(CompassSample(trueAzimuth, currentAccuracy))
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            }
        }

        val magneticFieldListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                currentAccuracy = when (accuracy) {
                    SensorManager.SENSOR_STATUS_ACCURACY_HIGH -> SensorAccuracy.HIGH
                    SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM -> SensorAccuracy.MEDIUM
                    SensorManager.SENSOR_STATUS_ACCURACY_LOW -> SensorAccuracy.LOW
                    else -> SensorAccuracy.UNRELIABLE
                }
            }
        }

        sensorManager.registerListener(
            rotationListener,
            rotationSensor,
            SensorManager.SENSOR_DELAY_UI
        )
        magneticFieldSensor?.let {
            sensorManager.registerListener(
                magneticFieldListener,
                it,
                SensorManager.SENSOR_DELAY_UI
            )
        }

        awaitClose {
            sensorManager.unregisterListener(rotationListener)
            sensorManager.unregisterListener(magneticFieldListener)
        }
    }
}