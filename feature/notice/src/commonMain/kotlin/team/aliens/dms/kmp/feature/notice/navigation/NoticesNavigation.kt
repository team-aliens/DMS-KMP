package team.aliens.dms.kmp.feature.notice.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.feature.notice.ui.Notices

@Serializable
data object NoticesRoute

fun NavGraphBuilder.notices(
    onNoticeDetailClick: (String) -> Unit,
) {
    composable<NoticesRoute> {
        Notices(
            onNoticeDetailClick = onNoticeDetailClick,
        )
    }
}
