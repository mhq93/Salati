package com.mhq.salati.location.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NominatimReverseDto(
    val error: String? = null,
    val address: NominatimAddressDto? = null
)