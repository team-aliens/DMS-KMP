package team.aliens.dms.kmp.feature.signup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.kmp.core.model.signup.SignUpData
import team.aliens.dms.kmp.feature.signup.ui.EnterEmail

fun NavController.navigateToEnterEmail(
    signUpData: SignUpData,
    navOptions: NavOptions? = null,
) = navigate(
    route = SignUp.Route.EnterEmail(signUpData = signUpData),
    navOptions = navOptions,
)

fun NavGraphBuilder.enterEmail(
    onBackPressed: () -> Unit,
    navigateToEnterEmailVerificationCode: (SignUpData) -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    composable<SignUp.Route.EnterEmail>(
        typeMap = SignUp.Route.NavTypeMap,
    ) {
        EnterEmail(
            onBackPressed = onBackPressed,
            navigateToEnterEmailVerificationCode = navigateToEnterEmailVerificationCode,
            onShowSnackBar = onShowSnackBar,
        )
    }
}
