package com.mhq.salati.alarms.domain.usecases

import com.mhq.salati.alarms.domain.scheduler.CustomAlarmScheduler
import com.mhq.salati.alarms.domain.repo.CustomAlarmRepository
import javax.inject.Inject

class ToggleCustomAlarmUseCase @Inject constructor(
    private val customAlarmRepository: CustomAlarmRepository,
    private val customAlarmScheduler: CustomAlarmScheduler
) {
    suspend operator fun invoke(id: Long, enabled: Boolean) {
        customAlarmRepository.setAlarmEnabled(id, enabled)
        if (!enabled) customAlarmScheduler.cancel(id)
    }
}