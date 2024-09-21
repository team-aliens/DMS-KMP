package team.aliens.dms.kmp.core.network.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule =
    module {
        single {
            fun provideHttpClient() =
                HttpClient {
                    defaultRequest {
                        header(HttpHeaders.ContentType, ContentType.Application.Json)
                        contentType(ContentType.Application.Json)
                        accept(ContentType.Application.Json)
                        url {
                            protocol = URLProtocol.HTTPS
                            host = "" // base_URL
                        }
                    }

                    install(ContentNegotiation) {
                        json(
                            Json {
                                prettyPrint = true
                                isLenient = true
                                ignoreUnknownKeys = true
                            },
                        )
                    }

                    install(Logging) {
                        logger =
                            object : Logger {
                                override fun log(message: String) {
                                    logger.log("Logger Ktor => $message")
                                }
                            }
                        level = LogLevel.ALL
                    }

                    install(ResponseObserver) {
                        onResponse { response ->
                            logger.info("HTTP status: ${response.status.value}")
                        }
                    }
                }
        }
    }
