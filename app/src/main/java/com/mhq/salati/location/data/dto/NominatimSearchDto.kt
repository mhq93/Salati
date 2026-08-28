package com.mhq.salati.location.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NominatimSearchDto(
    @SerialName("display_name")
    val displayName: String = "",
    @SerialName("lat")
    val lat: Double? = null,
    @SerialName("lon")
    val lon: Double? = null,
    val address: NominatimAddressDto? = null
)