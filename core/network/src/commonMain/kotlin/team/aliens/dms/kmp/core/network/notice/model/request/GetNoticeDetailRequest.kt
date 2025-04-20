package team.aliens.dms.kmp.core.network.notice.model.request

data class GetNoticeDetailRequest(
    val path: Path,
) {
    data class Path(
        val noticeId: String,
    )
}
