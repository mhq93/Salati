package com.mhq.salati.data.remote

import com.mhq.salati.data.remote.dto.TimingsResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class AladhanApiService(private val client: HttpClient) {

    companion object {
        private const val BASE_URL = "https://api.aladhan.com/v1"
    }

    suspend fun getTimings(
        date: String,
        latitude: Double,
        longitude: Double,
        method: Int = 5
    ): TimingsResponseDto {
        return client.get("$BASE_URL/timings/$date") {
            parameter("latitude", latitude)
            parameter("longitude", longitude)
            parameter("method", method)
        }.body()
    }
}