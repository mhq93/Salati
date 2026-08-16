package com.mhq.salati.alarms.domain.usecases

import com.mhq.salati.alarms.domain.scheduler.CustomAlarmScheduler
import com.mhq.salati.alarms.domain.repo.CustomAlarmRepository
import javax.inject.Inject

class DeleteCustomAlarmUseCase @Inject constructor(
    private val repository: CustomAlarmRepository,
    private val scheduler: CustomAlarmScheduler
) {
    suspend operator fun invoke(id: Long) {
        scheduler.cancel(id)
        repository.deleteAlarm(id)
    }
}