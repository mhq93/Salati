package com.mhq.salati.permissions.presentation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

/**
 * Per-screen gateway to the shared [LocationPermissionTracker]. Every effect is one thing the
 * screen has to do, and the screen maps it one-to-one. Nothing here asks the system for the
 * permission on its own: only [requirePermission], called from a button, does.
 */
class LocationPermissionDelegate @Inject constructor(
    private val tracker: LocationPermissionTracker
) {
    val state: StateFlow<LocationPermissionState> = tracker.state

    private val _effect = Channel<LocationPermissionEffect>(Channel.BUFFERED)
    val effect: Flow<LocationPermissionEffect> = _effect.receiveAsFlow()

    /** Asks the screen to show the system permission dialog. */
    fun requirePermission() {
        tracker.markPrompted()
        _effect.trySend(LocationPermissionEffect.RequestPermission)
    }

    fun onPermissionGranted() = tracker.markGranted()

    fun onPermissionDenied(permanentlyDenied: Boolean) = tracker.markDenied(permanentlyDenied)

    fun requestAppSettings() {
        _effect.trySend(LocationPermissionEffect.NavigateToAppSettings)
    }

    fun requestLocationSettings() {
        _effect.trySend(LocationPermissionEffect.NavigateToLocationSettings)
    }

    fun reset() = tracker.reset()
}