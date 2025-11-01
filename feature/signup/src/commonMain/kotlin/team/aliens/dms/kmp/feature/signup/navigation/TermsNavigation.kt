package team.aliens.dms.kmp.feature.signup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.kmp.core.model.signup.SignUpData
import team.aliens.dms.kmp.feature.signup.ui.Terms

fun NavController.navigateToTerms(
    signUpData: SignUpData,
    navOptions: NavOptions? = null,
) = navigate(
    route = SignUp.Route.Terms(signUpData = signUpData),
    navOptions = navOptions,
)

fun NavGraphBuilder.terms(
    onBackPressed: () -> Unit,
    navigateToComplete: () -> Unit,
    webViewUrl: String,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    composable<SignUp.Route.Terms>(
        typeMap = SignUp.Route.NavTypeMap,
    ) {
        Terms(
            onBackPressed = onBackPressed,
            navigateToComplete = navigateToComplete,
            webViewUrl = webViewUrl,
            onShowSnackBar = onShowSnackBar,
        )
    }
}
