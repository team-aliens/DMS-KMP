package team.aliens.dms.kmp.feature.notice.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.feature.notice.ui.Notices

@Serializable
data object NoticesRoute

fun NavController.navigateToNotices(
    navOptions: NavOptions? = null,
) = navigate(
    route = NoticesRoute,
    navOptions = navOptions,
)

fun NavGraphBuilder.notices(
    onNavigateBack: () -> Unit,
    onNoticeDetailClick: (String) -> Unit,
) {
    composable<NoticesRoute> {
        Notices(
            onNavigateBack = onNavigateBack,
            onNoticeDetailClick = onNoticeDetailClick,
        )
    }
}
