package team.aliens.dms.kmp.root

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.model.votes.VoteModel
import team.aliens.dms.kmp.feature.application.navigation.application
import team.aliens.dms.kmp.feature.home.navigation.HomeRoute
import team.aliens.dms.kmp.feature.home.navigation.home
import team.aliens.dms.kmp.feature.mypage.navigation.myPage
import team.aliens.dms.kmp.feature.notice.navigation.notices
import team.aliens.dms.kmp.ui.BottomNavigationBar

@Composable
internal fun Root(
    onNavigateRemainApplication: () -> Unit,
    onNavigateOutingApplication: () -> Unit,
    onNavigateVolunteerApplication: () -> Unit,
    onNoticeDetailClick: (String) -> Unit,
    onNavigateVote: (VoteModel) -> Unit,
) {
    RootScreen(
        onNavigateRemainApplication = onNavigateRemainApplication,
        onNavigateOutingApplication = onNavigateOutingApplication,
        onNavigateVolunteerApplication = onNavigateVolunteerApplication,
        onNoticeDetailClick = onNoticeDetailClick,
        onNavigateVote = onNavigateVote,
    )
}

@Composable
private fun RootScreen(
    onNavigateRemainApplication: () -> Unit,
    onNavigateOutingApplication: () -> Unit,
    onNavigateVolunteerApplication: () -> Unit,
    onNoticeDetailClick: (String) -> Unit,
    onNavigateVote: (VoteModel) -> Unit,
) {
    val navController: NavHostController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) },
    ) {
        NavHost(
            navController = navController,
            startDestination = HomeRoute,
            modifier = Modifier
                .background(DmsTheme.colors.onBackground)
                .padding(bottom = it.calculateBottomPadding()),
        ) {
            home()
            application(
                onNavigateRemainApplication = onNavigateRemainApplication,
                onNavigateOutingApplication = onNavigateOutingApplication,
                onNavigateVolunteerApplication = onNavigateVolunteerApplication,
                onNavigateVote = onNavigateVote,
            )
            notices(onNoticeDetailClick = onNoticeDetailClick)
            myPage()
        }
    }
}
