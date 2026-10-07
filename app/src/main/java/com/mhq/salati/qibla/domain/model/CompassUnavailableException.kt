package com.mhq.salati.qibla.domain.model

/** The device has no rotation sensor, so there is no compass. */
class CompassUnavailableException : Exception("Compass unavailable")