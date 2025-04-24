package team.aliens.dms.kmp.feature.signup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import team.aliens.dms.kmp.core.model.signup.SignUpData
import team.aliens.dms.kmp.feature.signup.ui.EnterStudentNumber

fun NavController.navigateToEnterStudentNumber(
    signUpData: SignUpData,
    navOptions: NavOptions? = null,
) = navigate(
    route = SignUp.Route.EnterStudentNumber(signUpData = signUpData),
    navOptions = navOptions,
)

fun NavGraphBuilder.enterStudentNumber(
    onBackPressed: () -> Unit,
    navigateToSetId: (SignUpData) -> Unit,
) {
    composable<SignUp.Route.EnterStudentNumber>(
        typeMap = SignUp.Route.NavTypeMap,
    ) {
        EnterStudentNumber(
            onBackPressed = onBackPressed,
            navigateToSetId = navigateToSetId,
        )
    }
}
