package com.mhq.salati.prayertimes.datasource.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class CalendarResponseDto(
    val code: Int,
    val status: String,
    val data: Map<String, List<TimingsDataDto>>
)