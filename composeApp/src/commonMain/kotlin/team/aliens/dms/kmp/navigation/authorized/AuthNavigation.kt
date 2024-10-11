package team.aliens.dms.kmp.navigation.authorized

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import team.aliens.dms.kmp.DmsNavigator
import team.aliens.dms.kmp.feature.signin.navigation.signIn
import team.aliens.dms.kmp.feature.splash.navigation.NAVIGATION_SPLASH
import team.aliens.dms.kmp.feature.splash.navigation.splash

const val NAVIGATION_AUTH = "auth"

internal fun NavGraphBuilder.authNavigation(
    navigator: DmsNavigator,
) {
    navigation(
        route = NAVIGATION_AUTH,
        startDestination = NAVIGATION_SPLASH,
    ) {
        splash(
            navigateToLogin = navigator::navigateToLogin,
        )
        signIn(
            navigateToMain = { },
            navigateToSignUp = { },
            navigateToFindId = { },
            navigateToFindPassword = { },
        )
    }
}
