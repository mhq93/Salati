package com.mhq.salati.shared.datasource.network

import com.mhq.salati.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object KtorClient {
    private const val REQUEST_TIMEOUT_MILLIS = 8_000L
    private const val CONNECT_TIMEOUT_MILLIS = 5_000L
    private const val SOCKET_TIMEOUT_MILLIS = 8_000L

    val instance: HttpClient by lazy {
        HttpClient(CIO) {

            defaultRequest {
                header(HttpHeaders.UserAgent, "SalatiApp/1.0 (${BuildConfig.CONTACT_EMAIL})")
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                    }
                )
            }
            install(Logging) {
                level = if (BuildConfig.DEBUG) LogLevel.INFO else LogLevel.NONE
            }
            install(HttpTimeout) {
                requestTimeoutMillis = REQUEST_TIMEOUT_MILLIS
                connectTimeoutMillis = CONNECT_TIMEOUT_MILLIS
                socketTimeoutMillis = SOCKET_TIMEOUT_MILLIS
            }
        }
    }
}