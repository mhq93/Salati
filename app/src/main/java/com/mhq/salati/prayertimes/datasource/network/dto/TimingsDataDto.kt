package com.mhq.salati.prayertimes.datasource.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class TimingsDataDto(
    val timings: TimingsDto,
    val date: DateDto,
    val meta: MetaDto
)