package com.mhq.salati.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class TimingsDataDto(
    val timings: TimingsDto,
    val date: DateDto,
    val meta: MetaDto
)