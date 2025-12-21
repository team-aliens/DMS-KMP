package team.aliens.dms.kmp.core.data.schools.repository

import team.aliens.dms.kmp.core.data.schools.mapper.toModel
import team.aliens.dms.kmp.core.model.schools.SchoolModel
import team.aliens.dms.kmp.core.network.schools.datasource.NetworkSchoolsDataSource
import team.aliens.dms.kmp.core.network.schools.model.request.GetSchoolVerificationCodeCheckRequest
import team.aliens.dms.kmp.core.network.schools.model.request.GetSchoolVerificationQuestionAnswerCheckRequest
import team.aliens.dms.kmp.core.network.schools.model.request.GetSchoolVerificationQuestionCheckRequest

internal class SchoolsRepositoryImpl(
    private val networkSchoolsDataSource: NetworkSchoolsDataSource,
) : SchoolsRepository {
    override suspend fun getSchools(): Result<List<SchoolModel>> =
        networkSchoolsDataSource.getSchools().map { it.schools.toModel() }

    override suspend fun getSchoolVerificationQuestionCheck(schoolId: String): Result<String> =
        networkSchoolsDataSource.getSchoolVerificationQuestionCheck(
            request = GetSchoolVerificationQuestionCheckRequest(
                path = GetSchoolVerificationQuestionCheckRequest.Path(
                    schoolId = schoolId,
                ),
            ),
        ).map { it.question }

    override suspend fun getSchoolVerificationQuestionAnswerCheck(
        schoolId: String,
        answer: String,
    ): Result<Unit> = networkSchoolsDataSource.getSchoolVerificationQuestionAnswerCheck(
        request = GetSchoolVerificationQuestionAnswerCheckRequest(
            path = GetSchoolVerificationQuestionAnswerCheckRequest.Path(
                schoolId = schoolId,
            ),
            query = GetSchoolVerificationQuestionAnswerCheckRequest.Query(
                answer = answer,
            ),
        ),
    )

    override suspend fun getSchoolVerificationCodeCheck(schoolCode: String): Result<String> =
        networkSchoolsDataSource.getSchoolVerificationCodeCheck(
            request = GetSchoolVerificationCodeCheckRequest(
                query = GetSchoolVerificationCodeCheckRequest.Query(
                    schoolCode = schoolCode,
                ),
            ),
        ).map { it.schoolId }
}
