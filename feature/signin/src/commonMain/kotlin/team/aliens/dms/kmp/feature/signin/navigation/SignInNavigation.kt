package team.aliens.dms.kmp.feature.signin.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import team.aliens.dms.kmp.feature.signin.ui.SignIn

const val NAVIGATION_SIGN_IN = "signIn"

fun NavGraphBuilder.signIn(
    navigateToMain: () -> Unit,
    navigateToSignUp: () -> Unit,
) {
    composable(NAVIGATION_SIGN_IN) {
        SignIn()
    }
}

fun NavController.navigateToSignIn() {
    navigate(NAVIGATION_SIGN_IN)
}
