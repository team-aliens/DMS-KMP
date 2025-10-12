package team.aliens.dms.kmp.root

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.core.model.type.PointType
import team.aliens.dms.kmp.core.model.votes.VoteModel

@Serializable
data object RootRoute

fun NavGraphBuilder.root(
    onNavigateRemainApplication: () -> Unit,
    onNavigateOutingApplication: () -> Unit,
    onNavigateVolunteerApplication: () -> Unit,
    onNoticeDetailClick: (String) -> Unit,
    onNavigateVote: (VoteModel) -> Unit,
    onNavigatePointHistory: (PointType) -> Unit
) {
    composable<RootRoute> {
        Root(
            onNavigateRemainApplication = onNavigateRemainApplication,
            onNavigateOutingApplication = onNavigateOutingApplication,
            onNavigateVolunteerApplication = onNavigateVolunteerApplication,
            onNoticeDetailClick = onNoticeDetailClick,
            onNavigateVote = onNavigateVote,
            onNavigatePointHistory = onNavigatePointHistory,
        )
    }
}
