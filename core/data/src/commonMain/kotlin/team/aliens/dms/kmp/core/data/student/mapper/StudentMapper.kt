package team.aliens.dms.kmp.core.data.student.mapper

import team.aliens.dms.kmp.core.model.mypage.MyPageModel
import team.aliens.dms.kmp.core.model.student.EmailModel
import team.aliens.dms.kmp.core.model.student.NameModel
import team.aliens.dms.kmp.core.model.student.ResetPasswordModel
import team.aliens.dms.kmp.core.model.student.StudentModel
import team.aliens.dms.kmp.core.model.type.GenderType
import team.aliens.dms.kmp.core.network.student.model.dto.GenderTypeDto
import team.aliens.dms.kmp.core.network.student.model.dto.StudentDto
import team.aliens.dms.kmp.core.network.student.model.response.ExamineStudentNumberResponse
import team.aliens.dms.kmp.core.network.student.model.response.FindIdResponse
import team.aliens.dms.kmp.core.network.student.model.response.GetCandidateModelStudentsResponse
import team.aliens.dms.kmp.core.network.student.model.response.GetMyPageResponse
import team.aliens.dms.kmp.core.network.student.model.response.GetStudentsResponse
import team.aliens.dms.kmp.core.network.student.model.response.ResetPasswordResponse

internal fun ExamineStudentNumberResponse.toModel() = NameModel(
    name = this.studentName,
)

internal fun FindIdResponse.toModel() = EmailModel(
    email = this.email,
)

internal fun ResetPasswordResponse.toModel() = ResetPasswordModel(
    accountId = accountId,
    name = name,
    email = email,
    authCode = authCode,
    newPassword = newPassword,
)

internal fun GetMyPageResponse.toModel() = MyPageModel(
    schoolName = schoolName,
    name = name,
    gcn = gcn,
    profileImageUrl = profileImageUrl,
    sex = sex.toModel(),
    bonusPoint = bonusPoint,
    minusPoint = minusPoint,
    phrase = phrase,
)

private fun GenderTypeDto.toModel() = when (this) {
    GenderTypeDto.MALE -> GenderType.MALE
    GenderTypeDto.FEMALE -> GenderType.FEMALE
    GenderTypeDto.ALL -> GenderType.ALL
}

internal fun GetStudentsResponse.toModel() = this.students.map { it.toModel() }

internal fun GetCandidateModelStudentsResponse.toModel() = this.students.map { it.toModel() }

private fun StudentDto.toModel() = StudentModel(
    id = id,
    name = name,
    gcn = gcn,
    profileImageUrl = profileImageUrl,
)
