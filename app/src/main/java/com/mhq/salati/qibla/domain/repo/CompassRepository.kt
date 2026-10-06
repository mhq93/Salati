package com.mhq.salati.qibla.domain.repo

import com.mhq.salati.qibla.domain.model.CompassReading
import com.mhq.salati.shared.domain.Coordinates
import kotlinx.coroutines.flow.Flow

interface CompassRepository {
    /** Live compass headings for [coordinates].
     * The flow fails when the device has no rotation sensor. */
    fun headings(coordinates: Coordinates): Flow<CompassReading>
}