package com.mhq.salati.alarms.domain.usecases

import com.mhq.salati.alarms.domain.model.CustomAlarm
import com.mhq.salati.alarms.domain.repo.CustomAlarmRepository
import javax.inject.Inject

class CreateCustomAlarmUseCase @Inject constructor(
    private val customAlarmRepository: CustomAlarmRepository
) {
    suspend operator fun invoke(alarm: CustomAlarm): Long = customAlarmRepository.createAlarm(alarm)
}