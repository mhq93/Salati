package com.mhq.salati.location.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//NOMINATIM…
@Serializable
data class NominatimAddressDto(
    val city: String? = null,
    val town: String? = null,
    val village: String? = null,
    val suburb: String? = null,
    val municipality: String? = null,
    val stateDistrict: String? = null,
    val state: String? = null,
    val county: String? = null,
    val country: String? = null,
    val countryCode: String? = null
)