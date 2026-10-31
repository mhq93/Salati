package com.mhq.salati.shared.ui

import com.mhq.salati.settings.domain.model.TimeFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

private val Time24Formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.US)
private val Time12Formatter = DateTimeFormatter.ofPattern("h:mm", Locale.US)

/**
 * Formats this time for display in the chosen [timeFormat].
 *
 * Digits stay ASCII in both formats (as prayer times always have); only the AM/PM marker follows
 * [locale], e.g. "5:23 AM" in English and "5:23 ص" in Arabic.
 */
fun LocalTime.formatForDisplay(timeFormat: TimeFormat, locale: Locale): String =
    when (timeFormat) {
        TimeFormat.HOUR_24 -> format(Time24Formatter)
        TimeFormat.HOUR_12 -> {
            val marker = format(DateTimeFormatter.ofPattern("a", locale))
            "${format(Time12Formatter)} $marker"
        }
    }
