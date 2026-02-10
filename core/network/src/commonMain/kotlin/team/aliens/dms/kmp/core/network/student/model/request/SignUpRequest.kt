package team.aliens.dms.kmp.core.network.student.model.request

import kotlinx.serialization.Serializable

data class SignUpRequest(
    val body: Body,
) {
    @Serializable
    data class Body(
        val schoolCode: String,
        val schoolAnswer: String,
        val email: String,
        val authCode: String,
        val grade: Int,
        val classRoom: Int,
        val number: Int,
        val accountId: String,
        val password: String,
        val profileImageUrl: String?,
    )
}
