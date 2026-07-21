package com.mhq.salati.domain.repo.alarms

import com.mhq.salati.domain.model.alarms.PrayerAlarm

interface AlarmScheduler {
    fun schedule(alarm: PrayerAlarm)
    fun cancel(prayerName: String)
    fun cancelAll()
}