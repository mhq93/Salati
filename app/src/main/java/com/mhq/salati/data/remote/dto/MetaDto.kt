package com.mhq.salati.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class MetaDto(
    val latitude: Double,
    val longitude: Double,
    val timezone: String
)