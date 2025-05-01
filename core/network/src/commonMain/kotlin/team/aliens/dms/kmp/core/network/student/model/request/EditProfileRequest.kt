package team.aliens.dms.kmp.core.network.student.model.request

data class EditProfileRequest(
    val body: Body,
) {
    data class Body(
        val profileImageUrl: String,
    )
}
