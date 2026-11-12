package com.mhq.salati.home.domain.model

/**
 * Why loading prayer times failed, in the granularity the UI needs.
 *
 * [Offline] and [TimedOut] get their own user-facing messages;
 * [Other] carries any unexpected failure verbatim.
 */
sealed interface PrayerTimesError {
    /** The device had no usable network connection. */
    data object Offline : PrayerTimesError

    /** The network request exceeded the fetch timeout. */
    data object TimedOut : PrayerTimesError

    /** Any other failure; [message] is the underlying exception's message, when there is one. */
    data class Other(val message: String?) : PrayerTimesError
}