package team.aliens.dms.kmp.core.network.student.model.response

import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.core.network.student.model.dto.StudentDto

@Serializable
data class GetStudentsResponse(
    val students: List<StudentDto>,
)
