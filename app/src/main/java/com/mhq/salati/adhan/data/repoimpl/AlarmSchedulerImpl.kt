package com.mhq.salati.adhan.data.repoimpl

import com.mhq.salati.adhan.datasource.alarm.PrayerAlarmDataSource
import com.mhq.salati.adhan.datasource.alarm.PrayerAlarmRequest
import com.mhq.salati.adhan.domain.model.PrayerAlarm
import com.mhq.salati.adhan.domain.repo.AlarmScheduler
import com.mhq.salati.shared.data.mapper.storageKey
import com.mhq.salati.shared.domain.PrayerName
import javax.inject.Inject

class AlarmSchedulerImpl @Inject constructor(
    private val prayerAlarmDataSource: PrayerAlarmDataSource
) : AlarmScheduler {

    override fun schedule(alarm: PrayerAlarm) {
        prayerAlarmDataSource.schedule(
            PrayerAlarmRequest(
                prayerKey = alarm.prayerName.storageKey,
                triggerAtMillis = alarm.triggerAtMillis,
                isMinorTiming = alarm.isMinorTiming,
                isMuted = alarm.isMuted
            )
        )
    }

    override fun cancel(prayerName: PrayerName) {
        prayerAlarmDataSource.cancel(prayerName.storageKey)
    }

    override fun cancelAll() {
        PrayerName.entries.forEach { cancel(it) }
    }
}