package com.mhq.salati.prayertimes.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class TimingsDataDto(
    val timings: TimingsDto,
    val date: DateDto,
    val meta: MetaDto
)