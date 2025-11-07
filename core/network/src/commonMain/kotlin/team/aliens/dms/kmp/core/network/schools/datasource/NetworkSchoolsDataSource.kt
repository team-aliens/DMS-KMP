package team.aliens.dms.kmp.core.network.schools.datasource

import team.aliens.dms.kmp.core.network.schools.model.request.GetSchoolVerificationCodeCheckRequest
import team.aliens.dms.kmp.core.network.schools.model.request.GetSchoolVerificationQuestionAnswerCheckRequest
import team.aliens.dms.kmp.core.network.schools.model.request.GetSchoolVerificationQuestionCheckRequest
import team.aliens.dms.kmp.core.network.schools.model.response.GetSchoolVerificationCodeCheckResponse
import team.aliens.dms.kmp.core.network.schools.model.response.GetSchoolVerificationQuestionCheckResponse

interface NetworkSchoolsDataSource {
    suspend fun getSchoolVerificationQuestionCheck(request: GetSchoolVerificationQuestionCheckRequest): Result<GetSchoolVerificationQuestionCheckResponse>
    suspend fun getSchoolVerificationQuestionAnswerCheck(request: GetSchoolVerificationQuestionAnswerCheckRequest): Result<Unit>
    suspend fun getSchoolVerificationCodeCheck(request: GetSchoolVerificationCodeCheckRequest): Result<GetSchoolVerificationCodeCheckResponse>
}
