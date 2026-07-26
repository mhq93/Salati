package com.mhq.salati.prayertimes.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class HijriMonthDto(
    val number: Int,
    val en: String,
    val ar: String
)