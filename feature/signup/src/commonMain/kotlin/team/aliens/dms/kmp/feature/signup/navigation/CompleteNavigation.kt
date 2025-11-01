package team.aliens.dms.kmp.feature.signup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import team.aliens.dms.kmp.feature.signup.ui.CompleteScreen

fun NavController.navigateToComplete(
    navOptions: NavOptions? = null,
) = navigate(
    route = SignUp.Route.Complete,
    navOptions = navOptions,
)

fun NavGraphBuilder.complete(
    navigateToMain: () -> Unit,
) {
    composable<SignUp.Route.Complete> {
        CompleteScreen(
            navigateToMain = navigateToMain,
        )
    }
}
