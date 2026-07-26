package com.mhq.salati.prayertimes.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class MonthDto(
    val number: Int,
    val en: String
)