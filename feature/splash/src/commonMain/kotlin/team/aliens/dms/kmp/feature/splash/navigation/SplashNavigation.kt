package team.aliens.dms.kmp.feature.splash.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.feature.splash.ui.Splash

@Serializable
data object SplashRoute

fun NavController.navigateToSplash(
    navOptions: NavOptions? = null,
) = navigate(
    route = SplashRoute,
    navOptions = navOptions,
)

fun NavGraphBuilder.splash(
    navigateToOnboarding: () -> Unit,
    navigateToSignIn: () -> Unit,
    navigateToMain: () -> Unit,
) {
    composable<SplashRoute> {
        Splash(
            navigateToLogin = navigateToSignIn,
            navigateToMain = navigateToMain,
            navigateToOnboarding = navigateToOnboarding
        )
    }
}
