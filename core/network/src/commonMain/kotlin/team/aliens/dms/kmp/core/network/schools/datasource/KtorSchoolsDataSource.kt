package team.aliens.dms.kmp.core.network.schools.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import team.aliens.dms.kmp.core.network.schools.model.request.GetSchoolVerificationCodeCheckRequest
import team.aliens.dms.kmp.core.network.schools.model.request.GetSchoolVerificationQuestionAnswerCheckRequest
import team.aliens.dms.kmp.core.network.schools.model.request.GetSchoolVerificationQuestionCheckRequest
import team.aliens.dms.kmp.core.network.schools.model.response.GetSchoolVerificationCodeCheckResponse
import team.aliens.dms.kmp.core.network.schools.model.response.GetSchoolVerificationQuestionCheckResponse
import team.aliens.dms.kmp.core.network.schools.model.response.GetSchoolsResponse

internal class KtorSchoolsDataSource(private val client: HttpClient) : NetworkSchoolsDataSource {
    override suspend fun getSchools(): Result<GetSchoolsResponse> = kotlin.runCatching {
        client.get("/schools").body()
    }

    override suspend fun getSchoolVerificationQuestionCheck(request: GetSchoolVerificationQuestionCheckRequest): Result<GetSchoolVerificationQuestionCheckResponse> =
        kotlin.runCatching {
            client.get("/schools/question/${request.path.schoolId}").body()
        }

    override suspend fun getSchoolVerificationQuestionAnswerCheck(request: GetSchoolVerificationQuestionAnswerCheckRequest): Result<Unit> =
        kotlin.runCatching {
            client.get("/schools/answer/${request.path.schoolId}") {
                parameter("answer", request.query.answer)
            }.body()
        }

    override suspend fun getSchoolVerificationCodeCheck(request: GetSchoolVerificationCodeCheckRequest): Result<GetSchoolVerificationCodeCheckResponse> =
        kotlin.runCatching {
            client.get("/schools/code") {
                parameter("school_code", request.query.schoolCode)
            }.body()
        }
}
