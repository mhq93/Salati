package com.mhq.salati.prayertimes.data.api

import com.mhq.salati.home.data.dto.CalendarResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class AladhanApiService(private val client: HttpClient) {

    companion object {
        private const val BASE_URL = "https://api.aladhan.com/v1"
    }

    suspend fun getCalendar(
        year: Int,
        latitude: Double,
        longitude: Double,
        method: Int = 5
    ): CalendarResponseDto {
        return client.get("$BASE_URL/calendar") {
            parameter("latitude", latitude)
            parameter("longitude", longitude)
            parameter("year", year)
            parameter("method", method)
            parameter("annual", true)
        }.body()
    }
}