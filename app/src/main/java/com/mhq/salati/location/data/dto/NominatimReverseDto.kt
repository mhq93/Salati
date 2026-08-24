package com.mhq.salati.location.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


//NOMINATIM…
@Serializable
data class NominatimReverseDto(
    val error: String? = null,
    val address: NominatimAddressDto? = null // ✅ CRITICAL
)

//data class NominatimReverseDto(
//    @SerialName("place_id")
//    val placeId: Long? = null,
//    @SerialName("display_name")
//    val displayName: String? = null,
//    @SerialName("address")
//    val address: NominatimAddressDto? = null,
//    @SerialName("error")
//    val error: String? = null
//)