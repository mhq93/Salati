package com.mhq.salati.prayertimes.datasource.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class MonthDto(
    val number: Int,
    val en: String
)