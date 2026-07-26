package com.mhq.salati.prayertimes.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class DateDto(
    val readable: String,
    val timestamp: String,
    val gregorian: GregorianDto,
    val hijri: HijriDto
)