package team.aliens.dms.kmp.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.kmp.core.model.type.PointType
import team.aliens.dms.kmp.feature.home.ui.Home

@Serializable
data object HomeRoute

fun NavGraphBuilder.home(
    onNavigateNotice: () -> Unit,
    onNavigateNoticeDetail: (String) -> Unit,
    onNavigatePointHistory: (PointType) -> Unit,
    onNavigateMeal: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    composable<HomeRoute> {
        Home(
            onNavigateNotice = onNavigateNotice,
            onNavigateNoticeDetail = onNavigateNoticeDetail,
            onNavigatePointHistory = onNavigatePointHistory,
            onNavigateMeal = onNavigateMeal,
            onShowSnackBar = onShowSnackBar,
        )
    }
}
