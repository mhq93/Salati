package com.mhq.salati.shared.domain.usecases

import javax.inject.Inject

class ParseTimeToMinutesUseCase @Inject constructor() {
    operator fun invoke(time: String): Int {
        val parts = time.split(":")
        val hours = parts.getOrNull(0)?.toIntOrNull() ?: 0
        val minutes = parts.getOrNull(1)?.toIntOrNull() ?: 0
        return hours * 60 + minutes
    }
}