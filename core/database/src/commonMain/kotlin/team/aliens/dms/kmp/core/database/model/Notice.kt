package team.aliens.dms.kmp.core.database.model

data class Notice(
    val id: String,
    val title: String,
    val content: String?,
    val createdAt: String,
)
