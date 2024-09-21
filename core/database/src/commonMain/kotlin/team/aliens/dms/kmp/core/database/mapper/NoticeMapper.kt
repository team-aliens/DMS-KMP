package team.aliens.dms.kmp.core.database.mapper

import team.aliens.dms.kmp.core.database.NoticeEntity
import team.aliens.dms.kmp.core.database.model.Notice

internal fun Notice.toEntity() = NoticeEntity(
    id = this.id,
    title = this.title,
    content = this.content,
    created_at = this.createdAt,
)
