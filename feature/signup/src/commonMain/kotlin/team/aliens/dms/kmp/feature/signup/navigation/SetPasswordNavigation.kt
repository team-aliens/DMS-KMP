package team.aliens.dms.kmp.feature.signup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import team.aliens.dms.kmp.core.model.signup.SignUpData
import team.aliens.dms.kmp.feature.signup.ui.SetPassword

fun NavController.navigateToSetPassword(
    signUpData: SignUpData,
    navOptions: NavOptions? = null,
) = navigate(
    route = SignUp.Route.SetPassword(signUpData = signUpData),
    navOptions = navOptions,
)

fun NavGraphBuilder.setPassword(
    onBackPressed: () -> Unit,
    navigateToTerms: (SignUpData) -> Unit,
) {
    composable<SignUp.Route.SetPassword>(
        typeMap = SignUp.Route.NavTypeMap,
    ) {
        SetPassword(
            onBackPressed = onBackPressed,
            navigateToTerms = navigateToTerms,
        )
    }
}
