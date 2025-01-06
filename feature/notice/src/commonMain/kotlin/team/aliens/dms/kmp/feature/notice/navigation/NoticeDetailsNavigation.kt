package team.aliens.dms.kmp.feature.notice.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import team.aliens.dms.kmp.feature.notice.ui.NoticeDetails

const val NAVIGATION_NOTICE_DETAILS = "noticeDetails"
private const val NOTICE_ID = "notice-id"

fun NavGraphBuilder.noticeDetails() {
    composable(
        route = "$NAVIGATION_NOTICE_DETAILS/{$NOTICE_ID}",
        arguments = listOf(
            navArgument(NOTICE_ID) { type = NavType.LongType },
        ),
    ) {
        val noticeId = it.arguments?.getLong(NOTICE_ID) ?: 0L
        NoticeDetails(noticeId = noticeId)
    }
}

fun NavController.navigateToNoticeDetails(noticeId: Long) {
    navigate("$NAVIGATION_NOTICE_DETAILS/$noticeId")
}
