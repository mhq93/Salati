package com.mhq.salati.prayertimes.datasource.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class MetaDto(
    val latitude: Double,
    val longitude: Double,
    val timezone: String
)