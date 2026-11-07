package com.mhq.salati.qibla.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhq.salati.location.presentation.handlers.LocationPrerequisitesHandler
import com.mhq.salati.location.presentation.model.PrerequisitesUi
import com.mhq.salati.qibla.domain.model.CompassUnavailableException
import com.mhq.salati.qibla.presentation.contract.QiblaContract
import com.mhq.salati.qibla.presentation.handlers.QiblaCompassHandler
import com.mhq.salati.qibla.presentation.handlers.QiblaLocationHandler
import com.mhq.salati.qibla.presentation.handlers.QiblaLocationLoad
import com.mhq.salati.qibla.presentation.mapper.toUi
import com.mhq.salati.shared.domain.Coordinates
import com.mhq.salati.shared.presentation.model.MessageUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Drives the Qibla screen: resolves the user's location (saved or fresh GPS fix), computes the
 * Qibla bearing from it, and streams live compass heading while the screen is active.
 *
 * Location resolution hard-blocks on connectivity.
 * With nothing saved, the first lookup needs the internet.
 *
 * Compass streaming is explicitly lifecycle-scoped via [QiblaContract.Intent.ScreenPaused] /
 * [QiblaContract.Intent.CompassResumed] rather than tied to the ViewModel's own lifetime, since a
 * ViewModel surviving a bottom-nav tab switch would otherwise keep the sensor listener alive
 * (and draining battery) while the user is on a different tab.
 */
@HiltViewModel
class QiblaViewModel @Inject constructor(
    private val locationHandler: QiblaLocationHandler,
    private val compassHandler: QiblaCompassHandler,
    private val prerequisitesHandler: LocationPrerequisitesHandler
) : ViewModel() {

    private val _state = MutableStateFlow(QiblaContract.State())
    val state: StateFlow<QiblaContract.State> = _state.asStateFlow()

    private val _effect = Channel<QiblaContract.Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    private var loadQiblaJob: Job? = null
    private var compassJob: Job? = null
    private var compassLocation: Coordinates? = null

    /** Last value the UI reported: the system would still show its dialog for the location permission. */
    private var showLocationRationale = false

    init {
        loadQibla()
    }

    /** Single entry point for all user/UI-driven events, per the MVI contract. */
    fun onIntent(intent: QiblaContract.Intent) {
        when (intent) {
            is QiblaContract.Intent.LoadQibla -> {
                val current = _state.value
                when {
                    current.prerequisites != null -> reevaluatePrerequisites()
                    current.errorMessage == null && loadQiblaJob?.isActive != true -> loadQibla()
                }
            }

            is QiblaContract.Intent.RetryRequested -> loadQibla()

            is QiblaContract.Intent.LocationPermissionRequested -> {
                viewModelScope.launch {
                    prerequisitesHandler.onPermissionRequested()
                    _effect.send(QiblaContract.Effect.RequestLocationPermission)
                }
            }

            is QiblaContract.Intent.LocationPermissionGranted -> reevaluatePrerequisites()

            is QiblaContract.Intent.LocationPermissionDenied -> {
                showLocationRationale = intent.showRationale
                reevaluatePrerequisites()
            }

            is QiblaContract.Intent.AppSettingsRequested ->
                sendEffect(QiblaContract.Effect.NavigateToAppSettings)

            is QiblaContract.Intent.LocationSettingsRequested ->
                sendEffect(QiblaContract.Effect.NavigateToLocationSettings)

            is QiblaContract.Intent.InternetSettingsRequested ->
                sendEffect(QiblaContract.Effect.NavigateToInternetSettings)

            is QiblaContract.Intent.LocationPickerRequested ->
                sendEffect(QiblaContract.Effect.NavigateToLocationPicker)

            is QiblaContract.Intent.RecalibrationRequested -> {
                _state.update { it.copy(isCalibrationGuideVisible = true) }
            }

            is QiblaContract.Intent.DismissCalibrationGuide -> {
                _state.update { it.copy(isCalibrationGuideVisible = false) }
            }

            is QiblaContract.Intent.ScreenResumed -> {
                showLocationRationale = intent.showLocationRationale
                val current = _state.value
                val hasValidData = current.qiblaBearing != null

                when {
                    hasValidData -> onIntent(QiblaContract.Intent.CompassResumed)
                    current.prerequisites != null -> reevaluatePrerequisites()
                    current.errorMessage == null && !current.isLoading -> loadQibla()
                }
            }

            is QiblaContract.Intent.LocationServicesToggled -> {
                if (_state.value.prerequisites != null) reevaluatePrerequisites()
            }

            is QiblaContract.Intent.ConnectivityChanged -> {
                if (_state.value.prerequisites != null) reevaluatePrerequisites()
            }

            is QiblaContract.Intent.ScreenPaused -> stopCompassUpdates()

            is QiblaContract.Intent.CompassResumed -> {
                compassLocation?.let { startCompassUpdates(it) }
            }
        }
    }

    private fun sendEffect(effect: QiblaContract.Effect) {
        viewModelScope.launch { _effect.send(effect) }
    }

    /**
     * Loads (or reloads) the Qibla screen: prefers a saved manual location, otherwise resolves a
     * fresh GPS fix. Cancels any in-flight load so rapid re-triggers (tab switches, retries)
     * can't race each other.
     *
     * If a bearing + location name are already held in memory, the loading spinner is skipped,
     * which keeps re-entering the tab from flashing back to a loading state when we already have
     * something valid to show.
     */
    private fun loadQibla() {
        val current = _state.value
        val hasCalculatedData = current.qiblaBearing != null
        loadQiblaJob?.cancel()

        loadQiblaJob = viewModelScope.launch {
            try {
                if (!hasCalculatedData) {
                    val latest = _state.value
                    if (latest.errorMessage == null && latest.prerequisites == null) {
                        _state.update { it.copy(isLoading = true) }
                    }
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
            is QiblaLocationLoad.Located -> showLocation(load.coordinates)

            is QiblaLocationLoad.LookingUpCurrentLocation -> {
                if (_state.value.qiblaBearing == null) {
                    _state.update {
                        it.copy(
                            isLoading = true,
                            errorMessage = null,
                            prerequisites = null,
                            sensorUnavailable = false
                        )
                    }
                }
            }

            is QiblaLocationLoad.Blocked ->
                showBlocked(prerequisitesHandler.toUi(load.prerequisites, showLocationRationale))

            is QiblaLocationLoad.TimedOut ->
                showError(MessageUi.FAILED_TO_GET_LOCATION)

            is QiblaLocationLoad.Failed ->
                showError(MessageUi.FAILED_TO_LOAD_QIBLA_DIRECTION)
        }
    }

    /** Shows the compass for the place and starts the live heading. */
    private fun showLocation(coordinates: Coordinates) {
        _state.update {
            it.copy(
                isLoading = false,
                errorMessage = null,
                prerequisites = null,
                qiblaBearing = compassHandler.bearing(coordinates),
                sensorUnavailable = false
            )
        }
        startCompassUpdates(coordinates)
    }

    /**
     * Re-checks what the blocked screen is waiting for. The rows update in place, with no loading
     * state in between; the lookup only restarts once nothing is missing (or a place was picked).
     */
    private fun reevaluatePrerequisites() {
        viewModelScope.launch {
            val blocked = prerequisitesHandler.blocked(showLocationRationale)
            if (blocked == null) loadQibla() else showBlocked(blocked)
        }
    }

    private fun showBlocked(prerequisites: PrerequisitesUi) {
        _state.update {
            it.copy(
                isLoading = false,
                errorMessage = null,
                sensorUnavailable = false,
                prerequisites = prerequisites
            )
        }
    }

    private suspend fun showError(message: MessageUi) {
        _state.update {
            it.copy(
                isLoading = false,
                prerequisites = null,
                errorMessage = message
            )
        }
        _effect.send(QiblaContract.Effect.ShowError(message))
    }

    private suspend fun showLoadError(error: Throwable) {
        val isSensorMissing = error is CompassUnavailableException
        val message = MessageUi.FAILED_TO_LOAD_QIBLA_DIRECTION
        _state.update {
            it.copy(
                isLoading = false,
                prerequisites = null,
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
                            compassAccuracy = reading.accuracy.toUi()
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