package team.aliens.dms.kmp.feature.mypage.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.kmp.core.model.type.PointType
import team.aliens.dms.kmp.feature.mypage.ui.MyPage

@Serializable
data object MyPageRoute

fun NavGraphBuilder.myPage(
    onNavigatePointHistory: (PointType) -> Unit,
    onNavigateSetting: () -> Unit,
) {
    composable<MyPageRoute> {
        MyPage(
            onNavigatePointHistory = onNavigatePointHistory,
            onNavigateSetting = onNavigateSetting,
        )
    }
}
