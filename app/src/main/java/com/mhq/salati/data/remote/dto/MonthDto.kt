package com.mhq.salati.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class MonthDto(
    val number: Int,
    val en: String
)