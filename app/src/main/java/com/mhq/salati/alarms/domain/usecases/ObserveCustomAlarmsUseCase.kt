package com.mhq.salati.alarms.domain.usecases

import com.mhq.salati.alarms.domain.model.CustomAlarm
import com.mhq.salati.alarms.domain.repo.CustomAlarmRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveCustomAlarmsUseCase @Inject constructor(
    private val customAlarmRepository: CustomAlarmRepository
) {
    operator fun invoke(): Flow<List<CustomAlarm>> = customAlarmRepository.observeAlarms()
}