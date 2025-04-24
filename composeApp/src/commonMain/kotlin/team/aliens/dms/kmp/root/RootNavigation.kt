package team.aliens.dms.kmp.root

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object RootRoute

fun NavGraphBuilder.root(
    onNavigateRemainApplication: () -> Unit,
    onNavigateOutingApplication: () -> Unit,
    onNoticeDetailClick: (String) -> Unit,
) {
    composable<RootRoute> {
        Root(
            onNavigateRemainApplication = onNavigateRemainApplication,
            onNavigateOutingApplication = onNavigateOutingApplication,
            onNoticeDetailClick = onNoticeDetailClick,
        )
    }
}
