package team.aliens.dms.kmp.navigation.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import team.aliens.dms.kmp.DmsNavigator
import team.aliens.dms.kmp.feature.notice.navigation.noticeDetails
import team.aliens.dms.kmp.root.NAVIGATION_ROOT
import team.aliens.dms.kmp.root.root
import tema.aliens.dms.kmp.feature.remain.navigation.remainApplication

private const val NAVIGATION_MAIN = "main"

internal fun NavGraphBuilder.mainNavigation(
    navigator: DmsNavigator,
) {
    navigation(
        route = NAVIGATION_MAIN,
        startDestination = NAVIGATION_ROOT,
    ) {
        root(
            onNavigateRemainApplication = navigator::navigateToRemainApplication,
            onNavigateOutingApplication = {},
            onNoticeDetailsClick = navigator::navigateToNoticeDetails
        )
        noticeDetails()
        remainApplication()
    }
}
