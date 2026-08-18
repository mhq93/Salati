package com.mhq.salati.alarms.domain.usecases

import com.mhq.salati.alarms.domain.scheduler.CustomAlarmScheduler
import com.mhq.salati.alarms.domain.repo.CustomAlarmRepository
import javax.inject.Inject

class DeleteCustomAlarmUseCase @Inject constructor(
    private val customAlarmRepository: CustomAlarmRepository,
    private val customAlarmScheduler: CustomAlarmScheduler
) {
    suspend operator fun invoke(id: Long) {
        customAlarmScheduler.cancel(id)
        customAlarmRepository.deleteAlarm(id)
    }
}