package team.aliens.dms.kmp.core.network.student.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import team.aliens.dms.kmp.core.network.student.model.request.CheckEmailDuplicationRequest
import team.aliens.dms.kmp.core.network.student.model.request.CheckIdDuplicationRequest
import team.aliens.dms.kmp.core.network.student.model.request.EditProfileRequest
import team.aliens.dms.kmp.core.network.student.model.request.ExamineStudentNumberRequest
import team.aliens.dms.kmp.core.network.student.model.request.FindIdRequest
import team.aliens.dms.kmp.core.network.student.model.request.GetStudentsRequest
import team.aliens.dms.kmp.core.network.student.model.request.ResetPasswordRequest
import team.aliens.dms.kmp.core.network.student.model.request.SignUpRequest
import team.aliens.dms.kmp.core.network.student.model.response.ExamineStudentNumberResponse
import team.aliens.dms.kmp.core.network.student.model.response.FindIdResponse
import team.aliens.dms.kmp.core.network.student.model.response.GetMyPageResponse
import team.aliens.dms.kmp.core.network.student.model.response.GetStudentsResponse
import team.aliens.dms.kmp.core.network.student.model.response.ResetPasswordResponse
import team.aliens.dms.kmp.core.network.student.model.response.SignUpResponse

internal class KtorStudentDataSource(
    private val client: HttpClient,
) : NetworkStudentDataSource {
    override suspend fun signUp(request: SignUpRequest): Result<SignUpResponse> =
        kotlin.runCatching {
            client.post("/students/signup").body()
        }

    override suspend fun examineStudentNumber(request: ExamineStudentNumberRequest): Result<ExamineStudentNumberResponse> =
        kotlin.runCatching {
            client.get("/students/name"){
                parameter("school_id",request.query.schoolId)
                parameter("grade",request.query.grade)
                parameter("class_room",request.query.classroom)
                parameter("number",request.query.number)
            }.body()
        }

    override suspend fun findId(request: FindIdRequest): Result<FindIdResponse> =
        kotlin.runCatching {
            client.get("/students/${request.path.schoolId}") {
                parameter("name", request.query.name)
                parameter("grade", request.query.grade)
                parameter("class_room", request.query.classRoom)
                parameter("number", request.query.number)
            }.body()
        }

    override suspend fun resetPassword(request: ResetPasswordRequest): Result<ResetPasswordResponse> =
        kotlin.runCatching {
            client.patch("/students/password/initialization") {
                setBody(request.body)
            }.body()
        }

    override suspend fun checkIdDuplication(request: CheckIdDuplicationRequest): Result<Unit> =
        kotlin.runCatching {
            client.get("/students/account-id/duplication") {
                parameter("account_id", request.query.id)
            }
        }

    override suspend fun checkEmailDuplication(request: CheckEmailDuplicationRequest): Result<Unit> =
        kotlin.runCatching {
            client.get("/students/email/duplication") {
                parameter("email", request.query.email)
            }
        }

    override suspend fun getMyPage(): Result<GetMyPageResponse> = kotlin.runCatching {
        client.get("/students/profile").body()
    }

    override suspend fun editProfile(request: EditProfileRequest): Result<Unit> =
        kotlin.runCatching {
            client.patch("/students/profile") {
                setBody(request.body)
            }
        }

    override suspend fun withdraw(): Result<Unit> = kotlin.runCatching {
        client.delete("/students/")
    }

    override suspend fun getStudents(request: GetStudentsRequest): Result<GetStudentsResponse> =
        kotlin.runCatching {
            client.get("/students/") {
                parameter("name", request.query.name)
            }.body()
        }
}
