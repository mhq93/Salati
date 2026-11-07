package com.mhq.salati.location.presentation.handlers

import com.mhq.salati.location.domain.model.LocationPrerequisites
import com.mhq.salati.location.domain.usecases.CheckLocationPrerequisitesUseCase
import com.mhq.salati.location.domain.usecases.GetSavedLocationUseCase
import com.mhq.salati.location.presentation.mapper.toPrerequisitesUi
import com.mhq.salati.location.presentation.model.PrerequisitesUi
import com.mhq.salati.permissions.domain.usecases.MarkLocationPermissionRequestedUseCase
import com.mhq.salati.permissions.domain.usecases.WasLocationPermissionRequestedUseCase
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * What Home and Qibla both need to know about a blocked lookup. [showRationale] comes from the UI
 * (only it can ask the system): false after the system stopped showing the dialog.
 */
class LocationPrerequisitesHandler @Inject constructor(
    private val checkLocationPrerequisitesUseCase: CheckLocationPrerequisitesUseCase,
    private val getSavedLocationUseCase: GetSavedLocationUseCase,
    private val wasLocationPermissionRequestedUseCase: WasLocationPermissionRequestedUseCase,
    private val markLocationPermissionRequestedUseCase: MarkLocationPermissionRequestedUseCase
) {
    /** What the blocked screen shows now, or null when nothing stands in the way (a saved place counts). */
    suspend fun blocked(showRationale: Boolean): PrerequisitesUi? {
        if (getSavedLocationUseCase().first() != null) return null
        val prerequisites = checkLocationPrerequisitesUseCase()
        return if (prerequisites.areMet) null else toUi(prerequisites, showRationale)
    }

    suspend fun toUi(prerequisites: LocationPrerequisites, showRationale: Boolean): PrerequisitesUi =
        prerequisites.toPrerequisitesUi(
            permissionPermanentlyDenied = !prerequisites.permissionGranted &&
                    wasLocationPermissionRequestedUseCase() &&
                    !showRationale
        )

    /** Remembers that the system dialog was asked for, so a later silent refusal reads as permanent. */
    suspend fun onPermissionRequested() = markLocationPermissionRequestedUseCase()
}