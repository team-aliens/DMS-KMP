package team.aliens.dms.kmp.core.data.student.repository

import kotlinx.datetime.LocalDate
import team.aliens.dms.kmp.core.model.mypage.MyPageModel
import team.aliens.dms.kmp.core.model.student.EmailModel
import team.aliens.dms.kmp.core.model.student.NameModel
import team.aliens.dms.kmp.core.model.student.ResetPasswordModel
import team.aliens.dms.kmp.core.model.student.StudentModel

interface StudentRepository {
    suspend fun signUp(
        schoolVerificationCode: String,
        schoolVerificationAnswer: String,
        email: String,
        authCode: String,
        grade: Int,
        classRoom: Int,
        number: Int,
        accountId: String,
        password: String,
        profileImageUrl: String?,
    ): Result<Unit>

    suspend fun examineStudentNumber(
        schoolId: String,
        grade: Int,
        classroom: Int,
        number: Int,
    ): Result<NameModel>

    suspend fun findId(
        schoolId: String,
        studentName: String,
        grade: Int,
        classRoom: Int,
        number: Int,
    ): Result<EmailModel>

    suspend fun resetPassword(
        accountId: String,
        name: String,
        email: String,
        emailVerificationCode: String,
        newPassword: String,
    ): Result<Unit>

    suspend fun checkIdDuplication(id: String): Result<Unit>

    suspend fun checkEmailDuplication(email: String): Result<Unit>

    suspend fun getMyPage(): Result<MyPageModel>

    suspend fun editProfile(profileImageUrl: String): Result<Unit>

    suspend fun withdraw(): Result<Unit>

    suspend fun getStudents(name: String?): Result<List<StudentModel>>

    suspend fun getCandidateModelStudents(requestDate: LocalDate): Result<List<StudentModel>>
}
