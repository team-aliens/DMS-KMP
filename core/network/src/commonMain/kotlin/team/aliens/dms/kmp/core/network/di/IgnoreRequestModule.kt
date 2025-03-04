package team.aliens.dms.kmp.core.network.di

import io.ktor.http.HttpMethod
import org.koin.dsl.module
import team.aliens.dms.kmp.core.network.IgnoreRequests
import team.aliens.dms.kmp.core.network.HttpRequest

internal val ignoreRequestModule = module {
    single<IgnoreRequests> {
        object : IgnoreRequests {
            override val requests: List<HttpRequest> = listOf(
                // Auth
                HttpRequest(
                    method = HttpMethod.Post,
                    path = "/auth/tokens",
                ),
                HttpRequest(
                    method = HttpMethod.Post,
                    path = "/auth/code",
                ),
                HttpRequest(
                    method = HttpMethod.Get,
                    path = "/auth/code",
                ),
                HttpRequest(
                    method = HttpMethod.Get,
                    path = "/auth/account-id",
                ),

                // Student
                HttpRequest(
                    method = HttpMethod.Post,
                    path = "/students/signup",
                ),
                HttpRequest(
                    method = HttpMethod.Get,
                    path = "/students/name",
                ),
                HttpRequest(
                    method = HttpMethod.Get,
                    path = "/students/account-id/",
                ),
                HttpRequest(
                    method = HttpMethod.Patch,
                    path = "/students/password/initialization",
                ),
                HttpRequest(
                    method = HttpMethod.Get,
                    path = "/students/account-id/duplication",
                ),
                HttpRequest(
                    method = HttpMethod.Get,
                    path = "/students/email/duplication",
                ),

                // School
                HttpRequest(
                    method = HttpMethod.Get,
                    path = "/schools",
                ),
                HttpRequest(
                    method = HttpMethod.Get,
                    path = "/schools/question/",
                ),
                HttpRequest(
                    method = HttpMethod.Get,
                    path = "/schools/answer/",
                ),
                HttpRequest(
                    method = HttpMethod.Get,
                    path = "/schools/code",
                ),

                // File
                HttpRequest(
                    method = HttpMethod.Get,
                    path = "/files/url",
                ),
            )
        }
    }
}
