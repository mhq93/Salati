package com.mhq.salati.qibla.datasource.device

class RotationSensorUnavailableException :
    IllegalStateException("Rotation vector sensor not available on this device")