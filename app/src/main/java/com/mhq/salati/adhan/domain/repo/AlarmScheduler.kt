package com.mhq.salati.adhan.domain.repo

import com.mhq.salati.adhan.domain.model.PrayerAlarm
import com.mhq.salati.shared.domain.PrayerName

interface AlarmScheduler {
    fun schedule(alarm: PrayerAlarm)
    fun cancel(prayerName: PrayerName)
    fun cancelAll()
}