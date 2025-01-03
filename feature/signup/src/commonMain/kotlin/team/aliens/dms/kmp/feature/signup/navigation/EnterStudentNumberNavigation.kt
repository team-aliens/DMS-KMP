package team.aliens.dms.kmp.feature.signup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import team.aliens.dms.kmp.core.common.utils.ResourceKeys
import team.aliens.dms.kmp.feature.signup.model.SignUpData
import team.aliens.dms.kmp.feature.signup.model.toJsonString
import team.aliens.dms.kmp.feature.signup.ui.EnterStudentNumber

const val NAVIGATION_ENTER_STUDENT_NUMBER = "enterStudentNumber"

fun NavGraphBuilder.enterStudentNumber(
    onBackPressed: () -> Unit,
    navigateToSetId: (SignUpData) -> Unit,
) {
    composable(
        route = "$NAVIGATION_ENTER_STUDENT_NUMBER/{${ResourceKeys.SIGN_UP}}",
        arguments = listOf(navArgument(ResourceKeys.SIGN_UP) { type = NavType.StringType }),
    ) {
        EnterStudentNumber(
            onBackPressed = onBackPressed,
            navigateToSetId = navigateToSetId,
            signUpData = it.getSignUpData(),
        )
    }
}

fun NavController.navigateToEnterStudentNumber(signUpData: SignUpData) {
    navigate("$NAVIGATION_ENTER_STUDENT_NUMBER/${signUpData.toJsonString()}")
}
