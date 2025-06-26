package team.aliens.dms.kmp.core.network.auth.model.request

data class ReissueRequest(
    val header: Header
) {
    data class Header(
        val refreshToken: String,
    )
}
