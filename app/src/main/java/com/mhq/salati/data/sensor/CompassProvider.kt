package com.mhq.salati.data.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
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

    fun getHeadingFlow(): Flow<Float> = callbackFlow {
        if (rotationSensor == null) {
            close(
                IllegalStateException(
                    "Rotation vector sensor not available on this device"
                )
            )
            return@callbackFlow
        }

        val rotationMatrix = FloatArray(9)
        val orientationAngles = FloatArray(3)

        val sensorEventListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                SensorManager.getRotationMatrixFromVector(
                    rotationMatrix,
                    event.values
                )
                SensorManager.getOrientation(
                    rotationMatrix,
                    orientationAngles
                )

                val azimuthRadians = orientationAngles[0]
                var azimuthDegrees = Math.toDegrees(azimuthRadians.toDouble()).toFloat()

                // Normalize to 0-360
                if (azimuthDegrees < 0) {
                    azimuthDegrees += 360f
                }

                trySend(azimuthDegrees)
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
        }

        sensorManager.registerListener(
            sensorEventListener,
            rotationSensor,
            SensorManager.SENSOR_DELAY_UI
        )

        awaitClose {
            sensorManager.unregisterListener(sensorEventListener)
        }
    }
}