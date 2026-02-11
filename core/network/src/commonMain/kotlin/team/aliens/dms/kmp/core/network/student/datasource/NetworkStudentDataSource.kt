package team.aliens.dms.kmp.core.network.student.datasource

import team.aliens.dms.kmp.core.network.auth.model.dto.TokenDto
import team.aliens.dms.kmp.core.network.student.model.request.CheckEmailDuplicationRequest
import team.aliens.dms.kmp.core.network.student.model.request.CheckIdDuplicationRequest
import team.aliens.dms.kmp.core.network.student.model.request.EditProfileRequest
import team.aliens.dms.kmp.core.network.student.model.request.ExamineStudentNumberRequest
import team.aliens.dms.kmp.core.network.student.model.request.FindIdRequest
import team.aliens.dms.kmp.core.network.student.model.request.GetCandidateModelStudentsRequest
import team.aliens.dms.kmp.core.network.student.model.request.GetStudentsRequest
import team.aliens.dms.kmp.core.network.student.model.request.ResetPasswordRequest
import team.aliens.dms.kmp.core.network.student.model.request.SignUpRequest
import team.aliens.dms.kmp.core.network.student.model.response.ExamineStudentNumberResponse
import team.aliens.dms.kmp.core.network.student.model.response.FindIdResponse
import team.aliens.dms.kmp.core.network.student.model.response.GetCandidateModelStudentsResponse
import team.aliens.dms.kmp.core.network.student.model.response.GetMyPageResponse
import team.aliens.dms.kmp.core.network.student.model.response.GetStudentsResponse
interface NetworkStudentDataSource {
    suspend fun signUp(request: SignUpRequest): Result<TokenDto>
    suspend fun examineStudentNumber(request: ExamineStudentNumberRequest): Result<ExamineStudentNumberResponse>
    suspend fun findId(request: FindIdRequest): Result<FindIdResponse>
    suspend fun resetPassword(request: ResetPasswordRequest): Result<Unit>
    suspend fun checkIdDuplication(request: CheckIdDuplicationRequest): Result<Unit>
    suspend fun checkEmailDuplication(request: CheckEmailDuplicationRequest): Result<Unit>
    suspend fun getMyPage(): Result<GetMyPageResponse>
    suspend fun editProfile(request: EditProfileRequest): Result<Unit>
    suspend fun withdraw(): Result<Unit>
    suspend fun getStudents(request: GetStudentsRequest): Result<GetStudentsResponse>
    suspend fun getCandidateModelStudents(request: GetCandidateModelStudentsRequest): Result<GetCandidateModelStudentsResponse>
}
