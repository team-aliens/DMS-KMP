package team.aliens.dms.kmp.core.database.datasource.notice

import team.aliens.dms.kmp.core.database.NoticeEntity
import team.aliens.dms.kmp.core.database.dao.NoticeDao

class NoticeDatabaseDataSourceImpl(
    private val noticeDao: NoticeDao,
) : NoticeDatabaseDataSource() {
    override suspend fun queryNotice(id: String): NoticeEntity = noticeDao.findById(id)

    override suspend fun queryAllNotices(): List<NoticeEntity> = noticeDao.findAllNotice()

    override suspend fun saveNotice(noticeEntity: NoticeEntity) = noticeDao.saveNotice(noticeEntity)

    override suspend fun saveAllNotices(notices: List<NoticeEntity>) = noticeDao.saveAllNotices(notices)
}
