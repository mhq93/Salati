package com.mhq.salati.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CalendarResponseDto(
    val code: Int,
    val status: String,
    val data: List<TimingsDataDto>
)