package team.aliens.dms.kmp.root

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val NAVIGATION_ROOT = "root"

fun NavGraphBuilder.root(
    onNoticeDetailsClick: (Long) -> Unit,
) {
    composable(NAVIGATION_ROOT) {
        Root(onNoticeDetailClick = onNoticeDetailsClick)
    }
}

fun NavController.navigateToRoot() {
    navigate(NAVIGATION_ROOT)
}
