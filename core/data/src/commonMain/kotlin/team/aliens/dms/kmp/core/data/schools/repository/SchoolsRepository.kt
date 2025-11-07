package team.aliens.dms.kmp.core.data.schools.repository

interface SchoolsRepository {
    suspend fun getSchoolVerificationQuestionCheck(schoolId: String): Result<String>

    suspend fun getSchoolVerificationQuestionAnswerCheck(
        schoolId: String,
        answer: String,
    ): Result<Unit>

    suspend fun getSchoolVerificationCodeCheck(schoolCode: String): Result<String>
}
