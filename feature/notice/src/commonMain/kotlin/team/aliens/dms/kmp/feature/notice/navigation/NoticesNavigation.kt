package team.aliens.dms.kmp.feature.notice.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import team.aliens.dms.kmp.feature.notice.ui.Notices

const val NAVIGATION_NOTICES = "notices"

fun NavGraphBuilder.notices(
    onNoticeDetailsClick: (Long) -> Unit,
) {
    composable(NAVIGATION_NOTICES) {
        Notices(
            onNoticeDetailsClick = onNoticeDetailsClick,
        )
    }
}
