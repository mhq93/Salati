package com.mhq.salati.qibla.data.sensor

import android.content.Context
import android.hardware.GeomagneticField
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.mhq.salati.qibla.domain.model.CompassAccuracy
import com.mhq.salati.qibla.domain.model.CompassReading
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class CompassProvider(
    context: Context
) {
    private val sensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val rotationSensor: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

    private val magneticFieldSensor: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

    fun getHeadingFlow(latitude: Double, longitude: Double): Flow<CompassReading> = callbackFlow {
        if (rotationSensor == null) {
            close(IllegalStateException("Rotation vector sensor not available on this device"))
            return@callbackFlow
        }

        val rotationMatrix = FloatArray(9)
        val orientationAngles = FloatArray(3)
        var currentAccuracy = CompassAccuracy.HIGH

        val rotationListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
                SensorManager.getOrientation(rotationMatrix, orientationAngles)

                val azimuthRadians = orientationAngles[0]
                //var azimuthDegrees = Math.toDegrees(azimuthRadians.toDouble()).toFloat()
                //if (azimuthDegrees < 0) azimuthDegrees += 360f
                var magneticAzimuth = Math.toDegrees(azimuthRadians.toDouble()).toFloat()
                if (magneticAzimuth < 0) magneticAzimuth += 360f

                val declination = GeomagneticField(
                    latitude.toFloat(),
                    longitude.toFloat(),
                    0f, // altitude — sea level is fine, declination barely varies with it
                    System.currentTimeMillis()
                ).declination

                var trueAzimuth = magneticAzimuth + declination
                if (trueAzimuth < 0) trueAzimuth += 360f
                if (trueAzimuth >= 360) trueAzimuth -= 360f

                //trySend(CompassReading(azimuthDegrees, currentAccuracy))
                trySend(CompassReading(trueAzimuth, currentAccuracy))
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                // rotation vector's own accuracy callback is unreliable on most devices — ignored
            }
        }

        val magneticFieldListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                // no-op — only registered for onAccuracyChanged
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                currentAccuracy = when (accuracy) {
                    SensorManager.SENSOR_STATUS_ACCURACY_HIGH -> CompassAccuracy.HIGH
                    SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM -> CompassAccuracy.MEDIUM
                    SensorManager.SENSOR_STATUS_ACCURACY_LOW -> CompassAccuracy.LOW
                    else -> CompassAccuracy.UNRELIABLE
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