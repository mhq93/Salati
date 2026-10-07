package com.mhq.salati.qibla.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.R
import com.mhq.salati.permissions.presentation.LocationPermissionEffect
import com.mhq.salati.qibla.domain.model.CompassUnavailableException
import com.mhq.salati.qibla.presentation.contract.QiblaContract
import com.mhq.salati.qibla.presentation.handlers.QiblaCompassHandler
import com.mhq.salati.qibla.presentation.handlers.QiblaLocationHandler
import com.mhq.salati.qibla.presentation.handlers.QiblaLocationLoad
import com.mhq.salati.shared.domain.Coordinates
import com.mhq.salati.shared.ui.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Drives the Qibla screen: resolves the user's location (saved or fresh GPS fix), computes the
 * Qibla bearing from it, and streams live compass heading while the screen is active.
 *
 * Location resolution never hard-blocks on connectivity — a GPS fix is sufficient to compute a
 * bearing; network is only used opportunistically to look up a human-readable place name.
 *
 * Compass streaming is explicitly lifecycle-scoped via [QiblaContract.Intent.ScreenPaused] /
 * [QiblaContract.Intent.CompassResumed] rather than tied to the ViewModel's own lifetime, since a
 * ViewModel surviving a bottom-nav tab switch would otherwise keep the sensor listener alive
 * (and draining battery) while the user is on a different tab.
 */

@HiltViewModel
class QiblaViewModel @Inject constructor(
    private val locationHandler: QiblaLocationHandler,
    private val compassHandler: QiblaCompassHandler
) : ViewModel() {

    private val _state = MutableStateFlow(QiblaContract.State())
    val state: StateFlow<QiblaContract.State> = _state.asStateFlow()

    private val _effect = Channel<QiblaContract.Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    private var loadQiblaJob: Job? = null
    private var compassJob: Job? = null
    // Remembers the coords compass was last running for, so CompassResumed
    // can restart it without re-running the whole loadQibla() flow
    private var compassLocation: Coordinates? = null

    init {
        viewModelScope.launch {
            locationHandler.permissionEffects.collect { permissionEffect ->
                val mapped = when (permissionEffect) {
                    is LocationPermissionEffect.RequestPermission ->
                        QiblaContract.Effect.RequestLocationPermission
                    is LocationPermissionEffect.NavigateToAppSettings ->
                        QiblaContract.Effect.NavigateToAppSettings
                    is LocationPermissionEffect.NavigateToLocationSettings ->
                        QiblaContract.Effect.NavigateToLocationSettings
                    is LocationPermissionEffect.PermissionResolved -> null
                }
                mapped?.let { _effect.send(it) }
            }
        }

        viewModelScope.launch {
            locationHandler.permissionState.collect { ps ->
                _state.update {
                    it.copy(
                        isLocationPermissionGranted = ps.granted,
                        isLocationPermissionPermanentlyDenied = ps.permanentlyDenied,
                        areLocationServicesDisabled = ps.servicesDisabled,
                        isLocationPermissionRequired = ps.required
                    )
                }
            }
        }

        locationHandler.localizedNameChanges
            .onEach { name -> _state.update { it.copy(locationName = name) } }
            .launchIn(viewModelScope)
    }

    /** Single entry point for all user/UI-driven events, per the MVI contract. */
    fun onIntent(intent: QiblaContract.Intent) {
        when (intent) {
            is QiblaContract.Intent.LoadQibla -> loadQibla()
            is QiblaContract.Intent.Retry -> loadQibla()
            is QiblaContract.Intent.RetryClicked -> {
                locationHandler.allowPermissionPromptAgain()
                loadQibla()
            }
            is QiblaContract.Intent.LocationPermissionGranted -> {
                viewModelScope.launch { locationHandler.onPermissionGranted() }
                loadQibla()
            }
            is QiblaContract.Intent.LocationPermissionDenied -> {
                viewModelScope.launch {
                    locationHandler.onPermissionDenied(intent.permanentlyDenied)
                }
                _state.update {
                    it.copy(
                        errorMessage = if (intent.permanentlyDenied) {
                            UiText.Res(R.string.location_permission_permanently_denied)
                        } else {
                            UiText.Res(R.string.location_permission_required)
                        }
                    )
                }
            }
            is QiblaContract.Intent.AccessAppSettings -> {
                viewModelScope.launch { locationHandler.openAppSettings() }
            }
            is QiblaContract.Intent.AccessDeviceLocationSettings -> {
                viewModelScope.launch { locationHandler.openLocationSettings() }
            }
            is QiblaContract.Intent.LocationPillClicked -> {
                viewModelScope.launch {
                    _effect.send(QiblaContract.Effect.NavigateToLocationPicker)
                }
            }
            is QiblaContract.Intent.RecalibrateClicked -> {
                _state.update { it.copy(isCalibrationGuideVisible = true) }
            }
            is QiblaContract.Intent.DismissCalibrationGuide -> {
                _state.update { it.copy(isCalibrationGuideVisible = false) }
            }
            is QiblaContract.Intent.ScreenResumed -> {
                val hasValidData = _state.value.qiblaBearing != null && _state.value.locationName != null
                if (hasValidData) {
                    // Already have a bearing + location — just resume the compass stream
                    // instead of re-hitting GPS/geocoding on every tab/app resume.
                    onIntent(QiblaContract.Intent.CompassResumed)
                } else {
                    loadQibla()
                }
            }
            is QiblaContract.Intent.LocationServicesToggled -> {
                if (intent.enabled && _state.value.areLocationServicesDisabled) {
                    loadQibla()
                }
            }
            is QiblaContract.Intent.ScreenPaused -> {
                stopCompassUpdates()
            }
            is QiblaContract.Intent.CompassResumed -> {
                compassLocation?.let { startCompassUpdates(it) }
            }
        }
    }

    /**
     * Loads (or reloads) the Qibla screen: prefers a saved manual location, otherwise resolves a
     * fresh GPS fix. Cancels any in-flight load so rapid re-triggers (tab switches, retries)
     * can't race each other.
     *
     * If a bearing + location name are already held in memory, the loading spinner and the
     * permission reset are skipped — this keeps re-entering the tab from flashing back
     * to a loading state when we already have something valid to show.
     */
    private fun loadQibla() {
        val hasCalculatedData = _state.value.qiblaBearing != null && _state.value.locationName != null

        loadQiblaJob?.cancel()

        loadQiblaJob = viewModelScope.launch {
            try {
                // Only clear state and show loading spinner on a true cold launch
                if (!hasCalculatedData) {
                    locationHandler.recheckPermissions()
                    _state.update { it.copy(isLoading = true, errorMessage = null) }
                }

                locationHandler.load().collect { load -> handleLocationLoad(load) }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                showLoadError(e)
            }
        }
    }

    private suspend fun handleLocationLoad(load: QiblaLocationLoad) {
        when (load) {
            is QiblaLocationLoad.Located -> showLocation(load)
            is QiblaLocationLoad.LookingUpCurrentLocation -> {
                // Only push raw loading status if no bearing exists in memory yet (prevents tab flicker)
                if (_state.value.qiblaBearing == null) {
                    _state.update {
                        it.copy(
                            isLoading = true,
                            errorMessage = null,
                            sensorUnavailable = false,
                            isLocationPermissionRequired = false,
                            areLocationServicesDisabled = false,
                            isLocationPermissionPermanentlyDenied = false
                        )
                    }
                }
            }
            is QiblaLocationLoad.ServicesDisabled ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = UiText.Res(R.string.location_services_disabled),
                        areLocationServicesDisabled = true,
                        isLocationPermissionRequired = false,
                        isLocationPermissionPermanentlyDenied = false,
                        sensorUnavailable = false
                    )
                }
            is QiblaLocationLoad.PermissionPermanentlyDenied ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = UiText.Res(R.string.location_permission_permanently_denied),
                        isLocationPermissionPermanentlyDenied = true,
                        areLocationServicesDisabled = false,
                        isLocationPermissionRequired = false,
                        sensorUnavailable = false
                    )
                }
            is QiblaLocationLoad.PermissionRequired ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = UiText.Res(R.string.location_permission_required),
                        isLocationPermissionRequired = true,
                        areLocationServicesDisabled = false,
                        isLocationPermissionPermanentlyDenied = false,
                        sensorUnavailable = false
                    )
                }
            is QiblaLocationLoad.PermissionPrompted ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        isLocationPermissionRequired = true,
                        areLocationServicesDisabled = false,
                        isLocationPermissionPermanentlyDenied = false,
                        sensorUnavailable = false
                    )
                }
            is QiblaLocationLoad.TimedOut ->
                showError(UiText.Res(R.string.failed_to_get_location))
            is QiblaLocationLoad.Failed ->
                showError(UiText.Res(R.string.failed_to_load_qibla_direction))
        }
    }

    /** Computes the bearing and display name for the place and starts the compass for it. */
    private suspend fun showLocation(located: QiblaLocationLoad.Located) {
        val coordinates = located.location.coordinates
        val name = locationHandler.localizedName(located)
        val bearing = compassHandler.bearing(coordinates)

        _state.update {
            it.copy(
                isLoading = false,
                errorMessage = null,
                qiblaBearing = bearing,
                locationName = name,
                sensorUnavailable = false,
                isLocationPermissionRequired = false,
                areLocationServicesDisabled = false,
                isLocationPermissionPermanentlyDenied = false
            )
        }
        startCompassUpdates(coordinates)
    }

    private suspend fun showError(message: UiText) {
        _state.update { it.copy(isLoading = false, errorMessage = message) }
        _effect.send(QiblaContract.Effect.ShowError(message))
    }

    // Also covers a device without a rotation sensor, which makes the compass stream fail.
    private suspend fun showLoadError(error: Throwable) {
        val isSensorMissing = error is CompassUnavailableException
        val message = UiText.Res(R.string.failed_to_load_qibla_direction)
        _state.update {
            it.copy(
                isLoading = false,
                errorMessage = message,
                sensorUnavailable = isSensorMissing
            )
        }
        _effect.send(QiblaContract.Effect.ShowError(message))
    }

    /**
     * Starts streaming compass headings for the given coordinates, cancelling any previous
     * stream first. [compassLocation] is cached so [QiblaContract.Intent.CompassResumed] can
     * restart the stream directly without re-running the whole [loadQibla] flow.
     */
    private fun startCompassUpdates(coordinates: Coordinates) {
        compassLocation = coordinates
        compassJob?.cancel()
        compassJob = viewModelScope.launch {
            compassHandler.headings(coordinates)
                .catch { error -> showLoadError(error) }
                .collect { reading ->
                    _state.update {
                        it.copy(
                            deviceHeading = reading.headingDegrees,
                            compassAccuracy = reading.accuracy
                        )
                    }
                }
        }
    }

    /** Cancels the active compass stream. Called on tab-pause and on [onCleared]. */
    private fun stopCompassUpdates() {
        compassJob?.cancel()
        compassJob = null
    }

    override fun onCleared() {
        compassJob?.cancel()
        super.onCleared()
    }
}