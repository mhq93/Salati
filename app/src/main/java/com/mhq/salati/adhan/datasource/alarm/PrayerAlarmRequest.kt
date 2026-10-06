package com.mhq.salati.adhan.datasource.alarm

/** One prayer alarm as the system alarm service needs it.
 * [prayerKey] is the stored name of the prayer. */
data class PrayerAlarmRequest(
    val prayerKey: String,
    val triggerAtMillis: Long,
    val isMinorTiming: Boolean,
    val isMuted: Boolean
)