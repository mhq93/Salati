package com.mhq.salati.data.remote

import com.mhq.salati.data.remote.dto.CalendarResponseDto
import com.mhq.salati.data.remote.dto.TimingsResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class AladhanApiService(private val client: HttpClient) {

    companion object {
        private const val BASE_URL = "https://api.aladhan.com/v1"
    }

    //data for a year...
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
        }.body()
    }

    //data for a day...
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