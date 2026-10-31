package com.mhq.salati.home.domain.model

sealed interface PrayerTimesError {
    data object Offline : PrayerTimesError
    data object TimedOut : PrayerTimesError
    data class Other(val message: String?) : PrayerTimesError
}