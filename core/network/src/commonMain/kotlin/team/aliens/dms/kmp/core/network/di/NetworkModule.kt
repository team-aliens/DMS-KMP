package team.aliens.dms.kmp.core.network.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import team.aliens.dms.kmp.core.network.exception.BadRequestException
import team.aliens.dms.kmp.core.network.exception.ConflictException
import team.aliens.dms.kmp.core.network.exception.ForbiddenException
import team.aliens.dms.kmp.core.network.exception.InternalServerErrorException
import team.aliens.dms.kmp.core.network.exception.NotFoundException
import team.aliens.dms.kmp.core.network.exception.RequestTimeoutException
import team.aliens.dms.kmp.core.network.exception.ServiceUnavailableException
import team.aliens.dms.kmp.core.network.exception.TooManyRequestsException
import team.aliens.dms.kmp.core.network.exception.UnAuthorizedException
import team.aliens.dms.kmp.core.network.exception.UnsupportedMediaTypeException

val networkModule =
    module {
        single {
            HttpClient {
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
                                println("Logger Ktor => $message")
                            }
                        }
                    level = LogLevel.ALL
                }

                install(ResponseObserver) {
                    onResponse { response ->
                        logger.info("HTTP status: ${response.status.value}")
                    }
                }

                HttpResponseValidator {
                    validateResponse { response ->
                        when (response.status) {
                            HttpStatusCode.BadRequest -> throw BadRequestException()
                            HttpStatusCode.Unauthorized -> throw UnAuthorizedException()
                            HttpStatusCode.Forbidden -> throw ForbiddenException()
                            HttpStatusCode.NotFound -> throw NotFoundException()
                            HttpStatusCode.RequestTimeout -> throw RequestTimeoutException()
                            HttpStatusCode.Conflict -> throw ConflictException()
                            HttpStatusCode.UnsupportedMediaType -> throw UnsupportedMediaTypeException()
                            HttpStatusCode.TooManyRequests -> throw TooManyRequestsException()
                            HttpStatusCode.InternalServerError -> throw InternalServerErrorException()
                            HttpStatusCode.ServiceUnavailable -> throw ServiceUnavailableException()
                        }
                    }

                    handleResponseExceptionWithRequest { cause, request ->
                        throw cause
                    }
                }
            }
        }
    }
