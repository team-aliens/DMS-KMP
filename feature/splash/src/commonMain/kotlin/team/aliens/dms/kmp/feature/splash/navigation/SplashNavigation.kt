package team.aliens.dms.kmp.feature.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import team.aliens.dms.kmp.feature.splash.ui.Splash

const val NAVIGATION_SPLASH = "splash"

fun NavGraphBuilder.splash(
    navigateToLogin: () -> Unit,
) {
    composable(NAVIGATION_SPLASH) {
        Splash(
            navigateToLogin = navigateToLogin,
        )
    }
}
