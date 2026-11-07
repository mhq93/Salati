package com.mhq.salati.qibla.presentation.handlers

import com.mhq.salati.location.domain.model.CoordinatesLookup
import com.mhq.salati.location.domain.usecases.CheckLocationPrerequisitesUseCase
import com.mhq.salati.location.domain.usecases.GetCurrentCoordinatesUseCase
import com.mhq.salati.location.domain.usecases.GetSavedLocationUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/** "Where is the user" for the Qibla screen: the saved place or a fresh GPS fix. Only coordinates matter here. */
class QiblaLocationHandler @Inject constructor(
    private val checkLocationPrerequisitesUseCase: CheckLocationPrerequisitesUseCase,
    private val getCurrentCoordinatesUseCase: GetCurrentCoordinatesUseCase,
    private val getSavedLocationUseCase: GetSavedLocationUseCase
) {
    /** The saved place if there is one, otherwise a fresh GPS lookup. */
    fun load(): Flow<QiblaLocationLoad> = flow {
        val saved = getSavedLocationUseCase().first()
        if (saved != null) {
            emit(QiblaLocationLoad.Located(saved.coordinates))
            return@flow
        }

        val prerequisites = checkLocationPrerequisitesUseCase()
        if (!prerequisites.areMet) {
            emit(QiblaLocationLoad.Blocked(prerequisites))
            return@flow
        }

        getCurrentCoordinatesUseCase().collect { lookup ->
            when (lookup) {
                is CoordinatesLookup.ServicesDisabled -> emit(QiblaLocationLoad.Blocked(prerequisites.copy(servicesEnabled = false)))
                is CoordinatesLookup.PermissionDenied -> emit(QiblaLocationLoad.Blocked(prerequisites.copy(permissionGranted = false)))
                is CoordinatesLookup.Locating -> emit(QiblaLocationLoad.LookingUpCurrentLocation)
                is CoordinatesLookup.TimedOut -> emit(QiblaLocationLoad.TimedOut)
                is CoordinatesLookup.Failed -> emit(QiblaLocationLoad.Failed(lookup.message))
                is CoordinatesLookup.Found -> emit(QiblaLocationLoad.Located(lookup.coordinates))
            }
        }
    }
}