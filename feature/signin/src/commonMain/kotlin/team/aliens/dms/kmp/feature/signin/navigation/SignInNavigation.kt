package team.aliens.dms.kmp.feature.signin.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.feature.signin.ui.SignIn

@Serializable
data object SignInRoute

fun NavController.navigateToSignIn(
    navOptions: NavOptions? = null,
) = navigate(
    route = SignInRoute,
    navOptions = navOptions,
)

fun NavGraphBuilder.signIn(
    navigateToMain: () -> Unit,
    navigateToSignUp: () -> Unit,
    navigateToFindId: () -> Unit,
    navigateToFindPassword: () -> Unit,
) {
    composable<SignInRoute> {
        SignIn(
            navigateToMain = navigateToMain,
            navigateToSignUp = navigateToSignUp,
            navigateToFindId = navigateToFindId,
            navigateToFindPassword = navigateToFindPassword,
        )
    }
}
