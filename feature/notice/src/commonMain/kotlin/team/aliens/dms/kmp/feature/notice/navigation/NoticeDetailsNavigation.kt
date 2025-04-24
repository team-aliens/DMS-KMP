package team.aliens.dms.kmp.feature.notice.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.feature.notice.ui.NoticeDetails

@Serializable
data class NoticeDetailRoute(val noticeId: String)

fun NavController.navigateToNoticeDetails(
    noticeId: String,
    navOptions: NavOptions? = null,
) = navigate(
    route = NoticeDetailRoute(noticeId = noticeId),
    navOptions = navOptions,
)

fun NavGraphBuilder.noticeDetails() {
    composable<NoticeDetailRoute> {
        NoticeDetails()
    }
}

