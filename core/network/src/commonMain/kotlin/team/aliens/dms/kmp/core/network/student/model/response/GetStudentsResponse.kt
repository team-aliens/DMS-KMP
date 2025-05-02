package team.aliens.dms.kmp.core.network.student.model.response

import kotlinx.serialization.Serializable

@Serializable
data class GetStudentsResponse(
    val students: List<Student>,
) {
    @Serializable
    data class Student(
        val id: String,
        val name: String,
        val gcn: String,
        val profileImageUrl: String,
    )
}
