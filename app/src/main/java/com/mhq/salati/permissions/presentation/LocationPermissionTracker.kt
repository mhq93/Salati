package com.mhq.salati.permissions.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

/** The location permission outcome, shared by Home and Qibla. */
@Singleton
class LocationPermissionTracker @Inject constructor() {

    private val _state = MutableStateFlow(LocationPermissionState())
    val state: StateFlow<LocationPermissionState> = _state.asStateFlow()

    fun markPrompted() = _state.update {
        LocationPermissionState(required = !it.permanentlyDenied, permanentlyDenied = it.permanentlyDenied)
    }

    fun markGranted() = _state.update {
        LocationPermissionState(granted = true)
    }

    fun markDenied(permanentlyDenied: Boolean) = _state.update {
        it.copy(granted = false, required = !permanentlyDenied, permanentlyDenied = permanentlyDenied)
    }

    fun reset() = _state.update {
        LocationPermissionState(permanentlyDenied = it.permanentlyDenied)
    }
}