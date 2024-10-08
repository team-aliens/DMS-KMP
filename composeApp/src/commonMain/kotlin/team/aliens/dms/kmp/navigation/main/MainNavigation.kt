package team.aliens.dms.kmp.navigation.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import team.aliens.dms.kmp.DmsNavigator

private const val NAVIGATION_MAIN = "main"

internal fun NavGraphBuilder.mainNavigation(
    navigator: DmsNavigator,
) {
    navigation(
        route = NAVIGATION_MAIN,
        startDestination = "",
    ) {
    }
}
