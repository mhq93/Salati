package com.mhq.salati.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class HijriDto(
    val date: String,
    val day: String,
    val month: HijriMonthDto,
    val year: String
)