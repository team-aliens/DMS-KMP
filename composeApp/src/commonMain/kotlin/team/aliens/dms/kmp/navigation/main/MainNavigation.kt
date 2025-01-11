package team.aliens.dms.kmp.navigation.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import team.aliens.dms.kmp.DmsNavigator
import team.aliens.dms.kmp.feature.notice.navigation.noticeDetails
import team.aliens.dms.kmp.root.NAVIGATION_ROOT
import team.aliens.dms.kmp.root.root

private const val NAVIGATION_MAIN = "main"

internal fun NavGraphBuilder.mainNavigation(
    navigator: DmsNavigator,
) {
    navigation(
        route = NAVIGATION_MAIN,
        startDestination = NAVIGATION_ROOT,
    ) {
        root(onNoticeDetailsClick = navigator::navigateToNoticeDetails)
        noticeDetails()
    }
}
