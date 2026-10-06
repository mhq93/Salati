package com.mhq.salati.location.datasource.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class NominatimReverseDto(
    val error: String? = null,
    val address: NominatimAddressDto? = null
)