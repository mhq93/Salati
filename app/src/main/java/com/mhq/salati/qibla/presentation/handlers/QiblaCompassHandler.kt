package com.mhq.salati.qibla.presentation.handlers

import com.mhq.salati.qibla.domain.model.CompassReading
import com.mhq.salati.qibla.domain.repo.CompassRepository
import com.mhq.salati.qibla.domain.usecases.GetQiblaBearingUseCase
import com.mhq.salati.shared.domain.Coordinates
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/** The Qibla direction for a place and the live compass heading of the device. */
class QiblaCompassHandler @Inject constructor(
    private val compassRepository: CompassRepository,
    private val getQiblaBearingUseCase: GetQiblaBearingUseCase
) {
    fun bearing(coordinates: Coordinates): Float = getQiblaBearingUseCase(coordinates).toFloat()

    /** Fails with an exception when the device has no rotation sensor. */
    fun headings(coordinates: Coordinates): Flow<CompassReading> =
        compassRepository.headings(coordinates)
}