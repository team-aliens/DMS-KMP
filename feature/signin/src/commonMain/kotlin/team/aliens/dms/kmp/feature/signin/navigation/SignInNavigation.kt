package team.aliens.dms.kmp.feature.signin.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.kmp.feature.signin.ui.SignIn

@Serializable
data object SignInRoute

fun NavController.navigateToSignIn(
    navOptions: NavOptions? = androidx.navigation.navOptions {
        popUpTo(graph.id) {
            inclusive = true
        }
    },
) = navigate(
    route = SignInRoute,
    navOptions = navOptions,
)

fun NavGraphBuilder.signIn(
    navigateToMain: () -> Unit,
    navigateToSignUp: () -> Unit,
    navigateToFindId: () -> Unit,
    navigateToFindPassword: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    composable<SignInRoute> {
        SignIn(
            navigateToMain = navigateToMain,
            navigateToSignUp = navigateToSignUp,
            navigateToFindId = navigateToFindId,
            navigateToFindPassword = navigateToFindPassword,
            onShowSnackBar = onShowSnackBar,
        )
    }
}
