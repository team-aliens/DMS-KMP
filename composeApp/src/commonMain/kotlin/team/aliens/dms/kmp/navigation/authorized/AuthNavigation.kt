package team.aliens.dms.kmp.navigation.authorized

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import team.aliens.dms.kmp.DmsNavigator

private const val NAVIGATION_AUTH = "auth"

internal fun NavGraphBuilder.authNavigation(navigator: DmsNavigator) {
    navigation(
        route = NAVIGATION_AUTH,
        startDestination = "",
    ) {
    }
}
