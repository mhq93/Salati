package com.mhq.salati.qibla.presentation.mapper

import com.mhq.salati.qibla.domain.model.CompassAccuracy
import com.mhq.salati.qibla.presentation.model.CompassAccuracyUi

fun CompassAccuracy.toUi(): CompassAccuracyUi = when (this) {
    CompassAccuracy.UNRELIABLE -> CompassAccuracyUi.UNRELIABLE
    CompassAccuracy.LOW -> CompassAccuracyUi.LOW
    CompassAccuracy.MEDIUM -> CompassAccuracyUi.MEDIUM
    CompassAccuracy.HIGH -> CompassAccuracyUi.HIGH
}