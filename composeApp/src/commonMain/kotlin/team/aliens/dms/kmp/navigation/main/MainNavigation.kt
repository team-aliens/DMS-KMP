package team.aliens.dms.kmp.navigation.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.core.network.PlatformConfig
import team.aliens.dms.kmp.feature.notice.navigation.navigateToNoticeDetails
import team.aliens.dms.kmp.feature.notice.navigation.noticeDetails
import team.aliens.dms.kmp.feature.volunteer.navigation.navigateToVolunteer
import team.aliens.dms.kmp.feature.volunteer.navigation.volunteer
import team.aliens.dms.kmp.feature.vote.navigation.navigateToVote
import team.aliens.dms.kmp.feature.vote.navigation.vote
import team.aliens.dms.kmp.root.RootRoute
import team.aliens.dms.kmp.root.root
import team.aliens.dms.kmp.ui.DmsAppState
import tema.aliens.dms.kmp.feature.remain.navigation.navigateToRemainApplication
import tema.aliens.dms.kmp.feature.remain.navigation.remainApplication

@Serializable
data object MainRoute

fun NavController.navigateToMain(
    navOptions: NavOptions? = null,
) = navigate(
    route = MainRoute,
    navOptions = navOptions,
)

internal fun NavGraphBuilder.mainGraph(
    appState: DmsAppState,
) {
    navigation<MainRoute>(
        startDestination = RootRoute,
    ) {
        root(
            onNavigateRemainApplication = appState.navController::navigateToRemainApplication,
            onNavigateOutingApplication = { },
            onNavigateVolunteerApplication = appState.navController::navigateToVolunteer,
            onNoticeDetailClick = appState.navController::navigateToNoticeDetails,
            onNavigateVote = appState.navController::navigateToVote,
        )
        noticeDetails(onNavigateBack = appState.navController::navigateUp)
        remainApplication(onNavigateBack = appState.navController::navigateUp)
        vote(
            onShowSnackBar = appState::showSnackBar,
            onNavigateBack = appState.navController::navigateUp,
        )
        volunteer(
            onNavigateBack = appState.navController::navigateUp,
            webViewUrl = PlatformConfig.webViewUrl,
        )
    }
}
