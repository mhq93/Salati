package com.mhq.salati.permissions.presentation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class LocationPermissionDelegate @Inject constructor() {

    private val _state = MutableStateFlow(LocationPermissionState())
    val state: StateFlow<LocationPermissionState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<LocationPermissionEffect>()
    val effect: SharedFlow<LocationPermissionEffect> = _effect.asSharedFlow()

    // Whether the system permission prompt was already requested, so a screen only asks once per attempt.
    private var promptShown = false

    /** Requests the permission the first time it is called; returns false when it was already requested. */
    suspend fun requirePermissionOnce(): Boolean {
        if (promptShown) return false
        promptShown = true
        requirePermission()
        return true
    }

    fun allowPromptAgain() {
        promptShown = false
    }

    suspend fun requirePermission() {
        _state.update { LocationPermissionState(required = true) }
        _effect.emit(LocationPermissionEffect.RequestPermission)
    }

    suspend fun onPermissionGranted() {
        _state.update { LocationPermissionState(required = false) }
        _effect.emit(LocationPermissionEffect.PermissionResolved)
    }

    suspend fun markServicesDisabled() {
        _state.update { it.copy(required = false, servicesDisabled = true) }
        _effect.emit(LocationPermissionEffect.PermissionResolved)
    }

    suspend fun onPermissionDenied(permanentlyDenied: Boolean) {
        _state.update { it.copy(granted = false, required = false, permanentlyDenied = permanentlyDenied) }
        _effect.emit(LocationPermissionEffect.PermissionResolved)
    }

    fun reset() {
        _state.update {
            LocationPermissionState(
                granted = false,
                required = false,
                permanentlyDenied = it.permanentlyDenied,
                servicesDisabled = it.servicesDisabled
            )
        }
    }

    suspend fun requestAppSettings() {
        _effect.emit(LocationPermissionEffect.NavigateToAppSettings)
    }

    suspend fun requestLocationSettings() {
        _effect.emit(LocationPermissionEffect.NavigateToLocationSettings)
    }
}