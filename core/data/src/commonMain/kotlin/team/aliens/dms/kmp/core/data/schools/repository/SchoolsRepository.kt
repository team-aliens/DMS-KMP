package team.aliens.dms.kmp.core.data.schools.repository

import team.aliens.dms.kmp.core.model.schools.SchoolModel

interface SchoolsRepository {
    suspend fun getSchools(): Result<List<SchoolModel>>

    suspend fun getSchoolVerificationQuestionCheck(schoolId: String): Result<String>

    suspend fun getSchoolVerificationQuestionAnswerCheck(
        schoolId: String,
        answer: String,
    ): Result<Unit>

    suspend fun getSchoolVerificationCodeCheck(schoolCode: String): Result<String>
}
