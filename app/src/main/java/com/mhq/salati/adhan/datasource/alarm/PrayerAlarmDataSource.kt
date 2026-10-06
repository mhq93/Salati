package com.mhq.salati.adhan.datasource.alarm

interface PrayerAlarmDataSource {
    fun schedule(request: PrayerAlarmRequest)
    fun cancel(prayerKey: String)
}