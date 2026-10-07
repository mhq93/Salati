package com.mhq.salati.location.data.repoimpl

import com.mhq.salati.location.data.mapper.bestCityName
import com.mhq.salati.location.data.mapper.toDomainOrNull
import com.mhq.salati.location.datasource.network.service.NominatimApi
import com.mhq.salati.location.domain.model.GeocodeResult
import com.mhq.salati.location.domain.model.LocationSearchResult
import com.mhq.salati.location.domain.repo.GeocoderProvider
import com.mhq.salati.shared.domain.Coordinates
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import javax.inject.Inject

class GeocoderProviderImpl @Inject constructor(
    private val nominatimApi: NominatimApi
) : GeocoderProvider {

    override suspend fun reverseGeocode(
        coordinates: Coordinates,
        acceptLanguage: String?
    ): GeocodeResult = try {
        val dto = nominatimApi.reverse(coordinates.latitude, coordinates.longitude, acceptLanguage)
        if (dto.error != null || dto.address == null) {
            GeocodeResult.NotFound
        } else {
            GeocodeResult.Found(
                cityName = dto.address.bestCityName(),
                countryName = dto.address.country
            )
        } // Delete catch if not used...
    } catch (e: TimeoutCancellationException) {
        GeocodeResult.Failed(e)
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        GeocodeResult.Failed(e)
    }

    /** Throws on network or server failure; an empty list means the search really found nothing. */
    override suspend fun searchByName(
        query: String,
        acceptLanguage: String?
    ): List<LocationSearchResult> =
        nominatimApi.search(query, acceptLanguage).mapNotNull { it.toDomainOrNull() }

    // Nominatim: Autocomplete search stopped;
    // I may stretch the search duration to avoid 1 request per minute limit
    //    override suspend fun searchByName(
    //        query: String,
    //        acceptLanguage: String?
    //    ): List<LocationSearchResult> = try {
    //        nominatimApi.search(query, acceptLanguage).mapNotNull { it.toDomainOrNull() }
    //    } catch (e: TimeoutCancellationException) {
    //        emptyList()
    //    } catch (e: CancellationException) {
    //        throw e
    //    } catch (e: Exception) {
    //        emptyList()
    //    }
}