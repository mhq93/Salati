package com.mhq.salati.shared.domain.usecases

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ParseToEpochMillisUseCase @Inject constructor() {
    operator fun invoke(date: String, time: String, zoneId: ZoneId = ZoneId.systemDefault()): Long {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
        return LocalDateTime.parse("$date $time", formatter)
            .atZone(zoneId)
            .toInstant()
            .toEpochMilli()
    }
}