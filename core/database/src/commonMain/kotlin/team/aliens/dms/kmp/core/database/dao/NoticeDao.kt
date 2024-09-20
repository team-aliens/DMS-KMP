package team.aliens.dms.kmp.core.database.dao

import team.aliens.dms.kmp.core.database.DmsDatabase
import team.aliens.dms.kmp.core.database.NoticeEntity
import team.aliens.dms.kmp.core.database.dmsDispatchers

class NoticeDao(
    private val dmsDatabase: DmsDatabase,
) {
    private val query get() = dmsDatabase.noticeEntityQueries

    suspend fun findAllNotice() = with(dmsDispatchers.io) {
        query.findAll()
    }

    suspend fun saveNotice(notice: NoticeEntity) = with(dmsDispatchers.io) {
        query.insertNotice(notice)
    }

    suspend fun saveAllNotices(notices: List<NoticeEntity>) = with(dmsDispatchers.io) {
        query.transaction {
            afterRollback { throw Exception("Failed to inserting prayers info") }
            notices.forEach { notice ->
                query.insertNotice(notice)
            }
        }
    }
}
