package team.aliens.dms.kmp.core.network.remains.model.request

data class UpdateRemainsOptionRequest(
    val path: Path,
) {
    data class Path(
        val remainOptionId: String,
    )
}
