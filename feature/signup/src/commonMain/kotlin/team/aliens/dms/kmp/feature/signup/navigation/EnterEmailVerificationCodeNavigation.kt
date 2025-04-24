package team.aliens.dms.kmp.feature.signup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import team.aliens.dms.kmp.core.model.signup.SignUpData
import team.aliens.dms.kmp.feature.signup.ui.EnterEmailVerificationCode

fun NavController.navigateToEnterEmailVerificationCode(
    signUpData: SignUpData,
    navOptions: NavOptions? = null,
) = navigate(
    route = SignUp.Route.EnterEmailVerificationCode(signUpData = signUpData),
    navOptions = navOptions,
)

fun NavGraphBuilder.enterEmailVerificationCode(
    onBackPressed: () -> Unit,
    navigateToEnterStudentNumber: (SignUpData) -> Unit,
) {
    composable<SignUp.Route.EnterEmailVerificationCode>(
        typeMap = SignUp.Route.NavTypeMap,
    ) {
        EnterEmailVerificationCode(
            onBackPressed = onBackPressed,
            navigateToEnterStudentNumber = navigateToEnterStudentNumber,
        )
    }
}
