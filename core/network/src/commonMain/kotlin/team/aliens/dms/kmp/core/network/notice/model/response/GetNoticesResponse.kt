package team.aliens.dms.kmp.core.network.notice.model.response

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class GetNoticesResponse(
    val notices: List<Notice>,
) {
    @Serializable
    data class Notice(
        val id: String,
        val title: String,
        val createdAt: LocalDate,
    )
}
