package com.mhq.salati.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class TimingsResponseDto(
    val code: Int,
    val status: String,
    val data: TimingsDataDto
)