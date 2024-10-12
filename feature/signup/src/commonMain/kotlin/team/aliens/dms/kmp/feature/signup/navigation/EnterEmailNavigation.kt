package team.aliens.dms.kmp.feature.signup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import team.aliens.dms.kmp.core.common.utils.ResourceKeys
import team.aliens.dms.kmp.feature.signup.model.SignUpData
import team.aliens.dms.kmp.feature.signup.model.toJsonString
import team.aliens.dms.kmp.feature.signup.ui.EnterEmail

const val NAVIGATION_ENTER_EMAIL = "enterEmail"

fun NavGraphBuilder.enterEmail(
    onBackPressed: () -> Unit,
    navigateEnterEmailVerificationCode: (SignUpData) -> Unit,
) {
    composable(
        route = "$NAVIGATION_ENTER_EMAIL/{${ResourceKeys.SIGN_UP}}",
        arguments = listOf(navArgument(ResourceKeys.SIGN_UP) { type = NavType.StringType }),
    ) {
        EnterEmail(
            onBackPressed = onBackPressed,
            navigateEnterEmailVerificationCode = navigateEnterEmailVerificationCode,
            signUpData = it.getSignUpData(),
        )
    }
}

fun NavController.navigateToEnterEmail(signUpData: SignUpData) {
    navigate("$NAVIGATION_ENTER_EMAIL/${signUpData.toJsonString()}")
}
