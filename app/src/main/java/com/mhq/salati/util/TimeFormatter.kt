package com.mhq.salati.util

object TimeFormatter {

    fun parseTimeToMinutes(time: String): Int {
        val parts = time.split(":")
        val hours = parts.getOrNull(0)?.toIntOrNull() ?: 0
        val minutes = parts.getOrNull(1)?.toIntOrNull() ?: 0
        return hours * 60 + minutes
    }

    fun sanitizeTimestamp(raw: String): String {
        return raw.substringBefore(" (").trim()
    }
}