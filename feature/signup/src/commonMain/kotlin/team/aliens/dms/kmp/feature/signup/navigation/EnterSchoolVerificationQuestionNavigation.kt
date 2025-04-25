package team.aliens.dms.kmp.feature.signup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import team.aliens.dms.kmp.core.model.signup.SignUpData
import team.aliens.dms.kmp.feature.signup.ui.EnterSchoolVerificationQuestion

fun NavController.navigateToEnterSchoolVerificationQuestion(
    signUpData: SignUpData,
    navOptions: NavOptions? = null,
) = navigate(
    route = SignUp.Route.EnterSchoolVerificationQuestion(signUpData = signUpData),
    navOptions = navOptions,
)

fun NavGraphBuilder.enterSchoolVerificationQuestion(
    onBackPressed: () -> Unit,
    navigateToEnterEmail: (SignUpData) -> Unit,
) {
    composable<SignUp.Route.EnterSchoolVerificationQuestion>(
        typeMap = SignUp.Route.NavTypeMap,
    ) {
        EnterSchoolVerificationQuestion(
            onBackPressed = onBackPressed,
            navigateToEnterEmail = navigateToEnterEmail,
        )
    }
}
