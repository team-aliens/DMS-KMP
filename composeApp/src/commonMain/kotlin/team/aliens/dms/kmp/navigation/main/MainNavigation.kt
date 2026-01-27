package team.aliens.dms.kmp.navigation.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import androidx.navigation.navigation
import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.core.network.PlatformConfig
import team.aliens.dms.kmp.feature.meal.navigation.meal
import team.aliens.dms.kmp.feature.meal.navigation.navigateToMeal
import team.aliens.dms.kmp.feature.notice.navigation.navigateToNoticeDetails
import team.aliens.dms.kmp.feature.notice.navigation.navigateToNotices
import team.aliens.dms.kmp.feature.notice.navigation.noticeDetails
import team.aliens.dms.kmp.feature.notice.navigation.notices
import team.aliens.dms.kmp.feature.notification.navigation.navigateToNotification
import team.aliens.dms.kmp.feature.notification.navigation.notification
import team.aliens.dms.kmp.feature.point.navigation.navigateToPointHistory
import team.aliens.dms.kmp.feature.point.navigation.pointHistory
import team.aliens.dms.kmp.feature.setting.navigation.navigateToSetting
import team.aliens.dms.kmp.feature.setting.navigation.setting
import team.aliens.dms.kmp.feature.signin.navigation.navigateToSignIn
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
    navOptions: NavOptions? = navOptions {
        popUpTo(graph.id) {
            inclusive = true
        }
    },
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
            onNavigateNotification = appState.navController::navigateToNotification,
            onNavigateNoticeDetail = appState.navController::navigateToNoticeDetails,
            onNavigateVote = appState.navController::navigateToVote,
            onNavigatePointHistory = appState.navController::navigateToPointHistory,
            onNavigateMeal = appState.navController::navigateToMeal,
            onNavigateSetting = appState.navController::navigateToSetting,
            onShowSnackBar = appState::showSnackBar,
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
        pointHistory(onNavigateBack = appState.navController::navigateUp)
        notices(
            onNavigateBack = appState.navController::navigateUp,
            onNoticeDetailClick = appState.navController::navigateToNoticeDetails,
        )
        meal(onNavigateToBack = appState.navController::navigateUp)
        notification(
            onNavigateBack = appState.navController::navigateUp,
            onNavigateNotificationDetailClick = appState.navController::navigateToNoticeDetails,
            onNavigatePointHistory = appState.navController::navigateToPointHistory,
            onShowSnackBar = appState::showSnackBar,
        )
        setting(
            onBackPressed = appState.navController::navigateUp,
            onNavigateResetPassword = { },
            onNavigateSelectProfile = { },
            onNavigateSignIn = appState.navController::navigateToSignIn,
            onShowSnackBar = appState::showSnackBar,
        )
    }
}
