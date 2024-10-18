package team.aliens.dms.kmp.feature.signup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import team.aliens.dms.kmp.core.common.utils.ResourceKeys
import team.aliens.dms.kmp.feature.signup.model.SignUpData
import team.aliens.dms.kmp.feature.signup.model.toJsonString
import team.aliens.dms.kmp.feature.signup.ui.SetPassword

const val NAVIGATION_SET_PASSWORD = "setPassword"

fun NavGraphBuilder.setPassword(
    onBackPressed: () -> Unit,
    navigateToTerms: (SignUpData) -> Unit,
) {
    composable(
        route = "$NAVIGATION_SET_PASSWORD/{${ResourceKeys.SIGN_UP}}",
        arguments = listOf(navArgument(ResourceKeys.SIGN_UP) { type = NavType.StringType }),
    ) {
        SetPassword(
            onBackPressed = onBackPressed,
            navigateToTerms = navigateToTerms,
            signUpData = it.getSignUpData(),
        )
    }
}

fun NavController.navigateToSetPassword(signUpData: SignUpData) {
    navigate("$NAVIGATION_SET_PASSWORD/${signUpData.toJsonString()}")
}

