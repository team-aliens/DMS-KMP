package team.aliens.dms.kmp.database.notice

import team.aliens.dms.kmp.core.database.NoticeEntity

abstract class DatabaseNoticeDataSource {
    abstract suspend fun queryNotice(id: String): NoticeEntity
    abstract suspend fun queryAllNotices(): List<NoticeEntity>
    abstract suspend fun saveNotice(noticeEntity: NoticeEntity)
    abstract suspend fun saveAllNotices(notices: List<NoticeEntity>)
}
