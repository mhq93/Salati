//package com.mhq.salati.locationpicker.data.remote.api
//
//import com.mhq.salati.locationpicker.data.remote.dto.NominatimReverseResponse
//import com.mhq.salati.locationpicker.data.remote.dto.NominatimSearchResponse
//import retrofit2.http.GET
//import retrofit2.http.Query
//
////NEW OSMDroid REPLACE...
//interface NominatimApiService {
//
//    @GET("search")
//    suspend fun searchLocation(
//        @Query("q") query: String,
//        @Query("format") format: String = "json",
//        @Query("addressdetails") addressDetails: Int = 1,
//        @Query("limit") limit: Int = 5,
//        @Query("accept-language") language: String,
//        @Query("extratags") extraTags: Int = 0
//    ): List<NominatimSearchResponse>
//
//    @GET("reverse")
//    suspend fun reverseGeocode(
//        @Query("lat") latitude: Double,
//        @Query("lon") longitude: Double,
//        @Query("format") format: String = "json",
//        @Query("addressdetails") addressDetails: Int = 1,
//        @Query("accept-language") language: String,
//        @Query("extratags") extraTags: Int = 0
//    ): NominatimReverseResponse
//}