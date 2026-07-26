package com.mhq.salati.prayertimes.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class GregorianDto(
    val date: String,
    val day: String,
    val month: MonthDto,
    val year: String
)