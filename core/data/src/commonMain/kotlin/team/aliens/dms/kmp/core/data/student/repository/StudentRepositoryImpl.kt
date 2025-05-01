package team.aliens.dms.kmp.core.data.student.repository

import team.aliens.dms.kmp.core.data.student.mapper.toModel
import team.aliens.dms.kmp.core.model.mypage.MyPageModel
import team.aliens.dms.kmp.core.model.student.EmailModel
import team.aliens.dms.kmp.core.model.student.NameModel
import team.aliens.dms.kmp.core.model.student.ResetPasswordModel
import team.aliens.dms.kmp.core.model.student.StudentModel
import team.aliens.dms.kmp.core.network.student.datasource.NetworkStudentDataSource
import team.aliens.dms.kmp.core.network.student.model.request.CheckEmailDuplicationRequest
import team.aliens.dms.kmp.core.network.student.model.request.CheckIdDuplicationRequest
import team.aliens.dms.kmp.core.network.student.model.request.EditProfileRequest
import team.aliens.dms.kmp.core.network.student.model.request.ExamineStudentNumberRequest
import team.aliens.dms.kmp.core.network.student.model.request.FindIdRequest
import team.aliens.dms.kmp.core.network.student.model.request.GetStudentsRequest
import team.aliens.dms.kmp.core.network.student.model.request.ResetPasswordRequest
import team.aliens.dms.kmp.core.network.student.model.request.SignUpRequest

internal class StudentRepositoryImpl(
    private val networkStudentDataSource: NetworkStudentDataSource,
) : StudentRepository {
    override suspend fun signUp(
        schoolVerificationCode: String,
        schoolVerificationAnswer: String,
        email: String,
        emailVerificationCode: String,
        grade: Int,
        classRoom: Int,
        number: Int,
        accountId: String,
        password: String,
        profileImageUrl: String?,
    ): Result<Unit> = networkStudentDataSource.signUp(
        request = SignUpRequest(
            body = SignUpRequest.Body(
                schoolCode = schoolVerificationCode,
                schoolAnswer = schoolVerificationAnswer,
                email = email,
                emailVerificationCode = emailVerificationCode,
                grade = grade,
                classRoom = classRoom,
                number = number,
                accountId = accountId,
                password = password,
                profileImageUrl = profileImageUrl,
            ),
        ),
    ).map { }

    override suspend fun examineStudentNumber(
        schoolId: String,
        grade: Int,
        classroom: Int,
        number: Int,
    ): Result<NameModel> = networkStudentDataSource.examineStudentNumber(
        request = ExamineStudentNumberRequest(
            query = ExamineStudentNumberRequest.Query(
                schoolId = schoolId,
                grade = grade,
                classroom = classroom,
                number = number,
            ),
        ),
    ).map { it.toModel() }

    override suspend fun findId(
        schoolId: String,
        studentName: String,
        grade: Int,
        classRoom: Int,
        number: Int,
    ): Result<EmailModel> = networkStudentDataSource.findId(
        request = FindIdRequest(
            path = FindIdRequest.Path(
                schoolId = schoolId,
            ),
            query = FindIdRequest.Query(
                name = studentName,
                grade = grade,
                classRoom = classRoom,
                number = number,
            ),
        ),
    ).map { it.toModel() }

    override suspend fun resetPassword(
        accountId: String,
        studentName: String,
        email: String,
        emailVerificationCode: String,
        newPassword: String,
    ): Result<ResetPasswordModel> = networkStudentDataSource.resetPassword(
        request = ResetPasswordRequest(
            body = ResetPasswordRequest.Body(
                accountId = accountId,
                studentName = studentName,
                email = email,
                emailVerificationCode = emailVerificationCode,
                newPassword = newPassword,
            ),
        ),
    ).map { it.toModel() }

    override suspend fun checkIdDuplication(id: String): Result<Unit> =
        networkStudentDataSource.checkIdDuplication(
            request = CheckIdDuplicationRequest(
                query = CheckIdDuplicationRequest.Query(id = id),
            ),
        )

    override suspend fun checkEmailDuplication(email: String): Result<Unit> =
        networkStudentDataSource.checkEmailDuplication(
            request = CheckEmailDuplicationRequest(
                query = CheckEmailDuplicationRequest.Query(
                    email = email,
                ),
            ),
        )

    override suspend fun getMyPage(): Result<MyPageModel> =
        networkStudentDataSource.getMyPage().map { it.toModel() }

    override suspend fun editProfile(profileImageUrl: String): Result<Unit> =
        networkStudentDataSource.editProfile(
            request = EditProfileRequest(
                body = EditProfileRequest.Body(profileImageUrl = profileImageUrl),
            ),
        )

    override suspend fun withdraw(): Result<Unit> = networkStudentDataSource.withdraw()

    override suspend fun getStudents(name: String): Result<List<StudentModel>> =
        networkStudentDataSource.getStudents(
            request = GetStudentsRequest(query = GetStudentsRequest.Query(name = name)),
        ).map { it.toModel() }
}
