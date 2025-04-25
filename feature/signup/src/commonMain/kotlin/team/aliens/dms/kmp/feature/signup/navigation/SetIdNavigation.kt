package team.aliens.dms.kmp.feature.signup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import team.aliens.dms.kmp.core.model.signup.SignUpData
import team.aliens.dms.kmp.feature.signup.ui.SetId

fun NavController.navigateToSetId(
    signUpData: SignUpData,
    navOptions: NavOptions? = null,
) = navigate(
    route = SignUp.Route.SetId(signUpData = signUpData),
    navOptions = navOptions,
)

fun NavGraphBuilder.setId(
    onBackPressed: () -> Unit,
    navigateToSetPassword: (SignUpData) -> Unit,
) {
    composable<SignUp.Route.SetId>(
        typeMap = SignUp.Route.NavTypeMap,
    ) {
        SetId(
            onBackPressed = onBackPressed,
            navigateToSetPassword = navigateToSetPassword,
        )
    }
}
