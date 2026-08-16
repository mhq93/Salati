package com.mhq.salati.alarms.domain.usecases

import com.mhq.salati.alarms.domain.scheduler.CustomAlarmScheduler
import com.mhq.salati.alarms.domain.repo.CustomAlarmRepository
import javax.inject.Inject

class ToggleCustomAlarmUseCase @Inject constructor(
    private val repository: CustomAlarmRepository,
    private val scheduler: CustomAlarmScheduler
) {
    suspend operator fun invoke(id: Long, enabled: Boolean) {
        repository.setEnabled(id, enabled)
        if (!enabled) scheduler.cancel(id)
    }
}