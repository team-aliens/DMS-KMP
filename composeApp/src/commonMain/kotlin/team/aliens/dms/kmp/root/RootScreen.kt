package team.aliens.dms.kmp.root

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.kmp.core.model.type.PointType
import team.aliens.dms.kmp.core.model.votes.VoteModel
import team.aliens.dms.kmp.feature.application.navigation.application
import team.aliens.dms.kmp.feature.home.navigation.HomeRoute
import team.aliens.dms.kmp.feature.home.navigation.home
import team.aliens.dms.kmp.feature.mypage.navigation.myPage
import team.aliens.dms.kmp.ui.BottomNavigationBar

@Composable
internal fun Root(
    onNavigateRemainApplication: () -> Unit,
    onNavigateOutingApplication: () -> Unit,
    onNavigateVolunteerApplication: () -> Unit,
    onNavigateNotification: () -> Unit,
    onNavigateNoticeDetail: (String) -> Unit,
    onNavigateVote: (VoteModel) -> Unit,
    onNavigatePointHistory: (PointType) -> Unit,
    onNavigateMeal: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    RootScreen(
        onNavigateRemainApplication = onNavigateRemainApplication,
        onNavigateOutingApplication = onNavigateOutingApplication,
        onNavigateVolunteerApplication = onNavigateVolunteerApplication,
        onNavigateNotification = onNavigateNotification,
        onNavigateNoticeDetail = onNavigateNoticeDetail,
        onNavigateVote = onNavigateVote,
        onNavigatePointHistory = onNavigatePointHistory,
        onNavigateMeal = onNavigateMeal,
        onShowSnackBar = onShowSnackBar,
    )
}

@Composable
private fun RootScreen(
    onNavigateRemainApplication: () -> Unit,
    onNavigateOutingApplication: () -> Unit,
    onNavigateVolunteerApplication: () -> Unit,
    onNavigateNotification: () -> Unit,
    onNavigateNoticeDetail: (String) -> Unit,
    onNavigateVote: (VoteModel) -> Unit,
    onNavigatePointHistory: (PointType) -> Unit,
    onNavigateMeal: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val navController: NavHostController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) },
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = HomeRoute,
            modifier = Modifier
                .background(DmsTheme.colors.background)
                .padding(paddingValues),
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
        ) {
            home(
                onNavigateNotification = onNavigateNotification,
                onNavigateNoticeDetail = onNavigateNoticeDetail,
                onNavigatePointHistory = onNavigatePointHistory,
                onNavigateMeal = onNavigateMeal,
                onShowSnackBar = onShowSnackBar,
            )
            application(
                onNavigateRemainApplication = onNavigateRemainApplication,
                onNavigateOutingApplication = onNavigateOutingApplication,
                onNavigateVolunteerApplication = onNavigateVolunteerApplication,
                onNavigateVote = onNavigateVote,
                onShowSnackBar = onShowSnackBar,
            )
            myPage(
                onNavigatePointHistory = onNavigatePointHistory,
                onShowSnackBar = onShowSnackBar,
            )
        }
    }
}
