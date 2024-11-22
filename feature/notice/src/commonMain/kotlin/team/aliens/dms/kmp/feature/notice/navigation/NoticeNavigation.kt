package team.aliens.dms.kmp.feature.notice.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import team.aliens.dms.kmp.feature.notice.ui.Notice

const val NAVIGATION_NOTICE = "notice"

fun NavGraphBuilder.notice() {
    composable(NAVIGATION_NOTICE) {
        Notice()
    }
}
