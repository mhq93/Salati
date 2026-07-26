package com.mhq.salati.adhan.domain.repo

import com.mhq.salati.adhan.domain.model.PrayerAlarm

interface AlarmScheduler {
    fun schedule(alarm: PrayerAlarm)
    fun cancel(prayerName: String)
    fun cancelAll()
}