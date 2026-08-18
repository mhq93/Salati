package com.mhq.salati.alarms.domain.usecases

import com.mhq.salati.alarms.domain.model.CustomAlarm
import com.mhq.salati.alarms.domain.repo.CustomAlarmRepository
import javax.inject.Inject

class UpdateCustomAlarmUseCase @Inject constructor(
    private val customAlarmRepository: CustomAlarmRepository
) {
    suspend operator fun invoke(alarm: CustomAlarm) = customAlarmRepository.updateAlarm(alarm)
}