package team.aliens.dms.kmp.navigation.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.feature.notice.navigation.navigateToNoticeDetails
import team.aliens.dms.kmp.feature.notice.navigation.noticeDetails
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
            onNoticeDetailClick = appState.navController::navigateToNoticeDetails,
        )
        noticeDetails()
        remainApplication()
    }
}
