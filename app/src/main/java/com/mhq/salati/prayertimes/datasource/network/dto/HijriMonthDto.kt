package com.mhq.salati.prayertimes.datasource.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class HijriMonthDto(
    val number: Int,
    val en: String,
    val ar: String
)