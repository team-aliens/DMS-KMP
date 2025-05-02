package team.aliens.dms.kmp.core.network.student.model.request

data class SignUpRequest(
    val body: Body,
) {
    data class Body(
        val schoolCode: String,
        val schoolAnswer: String,
        val email: String,
        val emailVerificationCode: String,
        val grade: Int,
        val classRoom: Int,
        val number: Int,
        val accountId: String,
        val password: String,
        val profileImageUrl: String?,
    )
}
