package com.mhq.salati.adhan.presentation.presenter

import com.mhq.salati.adhan.domain.usecases.RescheduleAllAlarmsUseCase
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

/** What the receivers ask for when alarms need to be armed again. */
class AlarmReschedulePresenter @Inject constructor(
    private val rescheduleAllAlarmsUseCase: RescheduleAllAlarmsUseCase
) {
    suspend fun onRescheduleNeeded() {
        try {
            rescheduleAllAlarmsUseCase()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            // A failed re-arm must never crash a receiver.
        }
    }
}