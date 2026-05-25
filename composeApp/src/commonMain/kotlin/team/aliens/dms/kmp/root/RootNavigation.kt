package team.aliens.dms.kmp.root

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.kmp.core.model.type.PointType
import team.aliens.dms.kmp.core.model.votes.VoteModel

@Serializable
data object RootRoute

fun NavGraphBuilder.root(
    onNavigateRemainApplication: () -> Unit,
    onNavigateOutingApplication: () -> Unit,
    onNavigateLateStudyApplication: () -> Unit,
    onNavigateVolunteerApplication: () -> Unit,
    onNavigateNotification: () -> Unit,
    onNavigateNoticeDetail: (String) -> Unit,
    onNavigateVote: (VoteModel) -> Unit,
    onNavigatePointHistory: (PointType) -> Unit,
    onNavigateMeal: () -> Unit,
    onNavigateSetting: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    composable<RootRoute> {
        Root(
            onNavigateRemainApplication = onNavigateRemainApplication,
            onNavigateOutingApplication = onNavigateOutingApplication,
            onNavigateLateStudyApplication = onNavigateLateStudyApplication,
            onNavigateVolunteerApplication = onNavigateVolunteerApplication,
            onNavigateNotification = onNavigateNotification,
            onNavigateNoticeDetail = onNavigateNoticeDetail,
            onNavigateVote = onNavigateVote,
            onNavigatePointHistory = onNavigatePointHistory,
            onNavigateMeal = onNavigateMeal,
            onNavigateSetting = onNavigateSetting,
            onShowSnackBar = onShowSnackBar,
        )
    }
}
