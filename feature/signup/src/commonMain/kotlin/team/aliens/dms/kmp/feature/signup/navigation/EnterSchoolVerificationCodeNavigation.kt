package team.aliens.dms.kmp.feature.signup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.kmp.core.model.signup.SignUpData
import team.aliens.dms.kmp.feature.signup.ui.EnterSchoolVerificationCode

fun NavController.navigateToEnterSchoolVerificationCode(
    navOptions: NavOptions? = null,
) = navigate(
    route = SignUp.Route.EnterSchoolVerificationCodeRoute,
    navOptions = navOptions,
)

fun NavGraphBuilder.enterSchoolVerificationCode(
    onBackPressed: () -> Unit,
    navigateToEnterSchoolVerificationQuestion: (SignUpData) -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    composable<SignUp.Route.EnterSchoolVerificationCodeRoute>(
        typeMap = SignUp.Route.NavTypeMap,
    ) {
        EnterSchoolVerificationCode(
            onBackPressed = onBackPressed,
            navigateToEnterSchoolVerificationQuestion = navigateToEnterSchoolVerificationQuestion,
            onShowSnackBar = onShowSnackBar,
        )
    }
}
