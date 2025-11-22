package team.aliens.dms.kmp.feature.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.feature.onboarding.ui.OnboardingScreen

@Serializable
data object OnboardingRoute

fun NavController.navigateToOnboarding(
    navOptions: NavOptions? = androidx.navigation.navOptions {
        popUpTo(graph.id) {
            inclusive = true
        }
    },
) = navigate(
    route = OnboardingRoute,
    navOptions = navOptions,
)

fun NavGraphBuilder.onboarding(
    navigateToSignIn: () -> Unit,
) {
    composable<OnboardingRoute> {
        OnboardingScreen(
            navigateToSignIn = navigateToSignIn,
        )
    }
}
