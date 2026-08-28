package com.mhq.salati.location.data

import com.mhq.salati.location.data.dto.NominatimAddressDto
import com.mhq.salati.location.data.dto.NominatimReverseDto
import com.mhq.salati.location.data.dto.NominatimSearchDto
import com.mhq.salati.location.domain.GeocodeResult
import com.mhq.salati.location.domain.model.LocationSearchResult
import com.mhq.salati.location.domain.repo.GeocoderProvider
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

class NominatimGeocoderProvider @Inject constructor(
    private val httpClient: HttpClient
) : GeocoderProvider {

    companion object {
        private const val BASE_URL = "https://nominatim.openstreetmap.org"
        private val REQUEST_TIMEOUT = 8_000L.milliseconds
    }

    override suspend fun reverseGeocode(
        latitude: Double,
        longitude: Double,
        acceptLanguage: String?
    ): GeocodeResult = try {
        val response = withTimeout(REQUEST_TIMEOUT) {
            httpClient.get("$BASE_URL/reverse") {
                parameter("lat", latitude)
                parameter("lon", longitude)
                parameter("format", "json")
                parameter("addressdetails", "1")
                acceptLanguage?.let { parameter("accept-language", it) }
            }
        }

        if (response.status != HttpStatusCode.OK) {
            GeocodeResult.Failed(Exception("HTTP ${response.status.value}"))
        } else {
            val dto = response.body<NominatimReverseDto>()
            if (dto.error != null || dto.address == null) {
                GeocodeResult.NotFound
            } else {
                GeocodeResult.Found(
                    cityName = extractBestCityName(dto.address),
                    countryName = dto.address.country
                )
            }
        }
    } catch (e: TimeoutCancellationException) {
        GeocodeResult.Failed(e)
    } catch (e: Exception) {
        GeocodeResult.Failed(e)
    }

    override suspend fun searchByName(
        query: String,
        acceptLanguage: String?
    ): List<LocationSearchResult> = try {
        val response = withTimeout(REQUEST_TIMEOUT) {
            httpClient.get("$BASE_URL/search") {
                parameter("q", query)
                parameter("format", "json")
                parameter("addressdetails", "1")
                parameter("limit", "5")
                acceptLanguage?.let { parameter("accept-language", it) }
            }
        }

        if (response.status != HttpStatusCode.OK) {
            emptyList()
        } else {
            val results = response.body<List<NominatimSearchDto>>()
            results.mapNotNull { dto ->
                if (dto.lat == null || dto.lon == null) return@mapNotNull null

                // ✅ FIX: Construct a specific display name from address components
                val bestDisplayName = constructDisplayName(dto.address, dto.displayName ?: "")

                LocationSearchResult(
                    displayName = bestDisplayName,
                    latitude = dto.lat,
                    longitude = dto.lon
                )
            }
        }
    } catch (e: TimeoutCancellationException) {
        emptyList()
    } catch (e: Exception) {
        emptyList()
    }

    // ✅ Helper: Prioritize the most specific location name
    private fun extractBestCityName(address: NominatimAddressDto): String? {
        return address.city
            ?: address.town
            ?: address.village
            ?: address.suburb
            ?: address.municipality
            ?: address.stateDistrict
            ?: address.county
            ?: address.state
    }

    // ✅ Helper: Build a clean "City, State, Country" string
    private fun constructDisplayName(address: NominatimAddressDto?, fallback: String): String {
        if (address == null) return fallback.split(",").firstOrNull()?.trim() ?: fallback

        val city = extractBestCityName(address)
        val state = address.state ?: address.stateDistrict
        val country = address.country

        // Build a list of non-null, non-blank parts and remove duplicates (e.g., if city and state are both "Cairo")
        val parts = listOfNotNull(city, state, country)
            .map { it.trim() }
            .filter { it.isNotBlank() }
            .distinct()

        return if (parts.isNotEmpty()) {
            parts.joinToString(", ")
        } else {
            // Fallback to the first part of the original display name if address is useless
            fallback.split(",").firstOrNull()?.trim() ?: fallback
        }
    }
}