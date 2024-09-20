package team.aliens.dms.kmp.core.database

import kotlinx.coroutines.CoroutineDispatcher

interface DmsDispatchers {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}

expect val dmsDispatchers: DmsDispatchers
