package com.mhq.salati.presentation.common.location

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class LocationPermissionDelegate {

    private val _state = MutableStateFlow(LocationPermissionState())
    val state: StateFlow<LocationPermissionState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<LocationPermissionEffect>()
    val effect: SharedFlow<LocationPermissionEffect> = _effect.asSharedFlow()

    suspend fun requirePermission() {
        _state.value = LocationPermissionState(required = true)
        _effect.emit(LocationPermissionEffect.RequestPermission)
    }

    fun onPermissionGranted() {
        _state.value = LocationPermissionState(required = false)
    }

    fun markServicesDisabled() {
        _state.value = _state.value.copy(required = false, servicesDisabled = true)
    }

    fun onPermissionDenied(permanentlyDenied: Boolean) {
        _state.value = LocationPermissionState(permanentlyDenied = permanentlyDenied)
    }

    fun reset() {
        _state.value = LocationPermissionState()
    }

    suspend fun requestAppSettings() {
        _effect.emit(LocationPermissionEffect.NavigateToAppSettings)
    }

    suspend fun requestLocationSettings() {
        _effect.emit(LocationPermissionEffect.NavigateToLocationSettings)
    }
}