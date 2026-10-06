package com.mhq.salati.location.datasource.network.service

import com.mhq.salati.location.datasource.network.dto.NominatimReverseDto
import com.mhq.salati.location.datasource.network.dto.NominatimSearchDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.withTimeout
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

class KtorNominatimApi @Inject constructor(
    private val httpClient: HttpClient
) : NominatimApi {

    companion object {
        private const val BASE_URL = "https://nominatim.openstreetmap.org"
        private val REQUEST_TIMEOUT = 8_000L.milliseconds
    }

    override suspend fun reverse(
        latitude: Double,
        longitude: Double,
        acceptLanguage: String?
    ): NominatimReverseDto {
        val response = withTimeout(REQUEST_TIMEOUT) {
            httpClient.get("$BASE_URL/reverse") {
                parameter("lat", latitude)
                parameter("lon", longitude)
                parameter("format", "json")
                parameter("addressdetails", "1")
                acceptLanguage?.let { parameter("accept-language", it) }
            }
        }
        if (response.status != HttpStatusCode.OK) throw NominatimHttpException(response.status.value)
        return response.body()
    }

    override suspend fun search(
        query: String,
        acceptLanguage: String?
    ): List<NominatimSearchDto> {
        val response = withTimeout(REQUEST_TIMEOUT) {
            httpClient.get("$BASE_URL/search") {
                parameter("q", query)
                parameter("format", "json")
                parameter("addressdetails", "1")
                parameter("limit", "5")
                acceptLanguage?.let { parameter("accept-language", it) }
            }
        }
        if (response.status != HttpStatusCode.OK) throw NominatimHttpException(response.status.value)
        return response.body()
    }
}