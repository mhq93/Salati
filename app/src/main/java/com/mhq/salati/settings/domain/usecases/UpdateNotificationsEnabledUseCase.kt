package com.mhq.salati.settings.domain.usecases

import com.mhq.salati.adhan.domain.repo.AlarmScheduler
import com.mhq.salati.adhan.domain.usecases.RescheduleAllAlarmsUseCase
import com.mhq.salati.settings.domain.repo.SettingsRepository
import javax.inject.Inject

class UpdateNotificationsEnabledUseCase @Inject constructor(
    private val alarmScheduler: AlarmScheduler,
    private val settingsRepository: SettingsRepository,
    private val rescheduleAllAlarmsUseCase: RescheduleAllAlarmsUseCase
) {
    suspend operator fun invoke(enabled: Boolean) {
        settingsRepository.setNotificationsEnabled(enabled)
        if (!enabled) alarmScheduler.cancelAll()
        /** Off: skips the prayer alarms and cancels the custom ones.
         *  On: arms both. */
        rescheduleAllAlarmsUseCase()
    }
}