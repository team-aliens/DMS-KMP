package team.aliens.dms.kmp.feature.signup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import team.aliens.dms.kmp.core.common.utils.ResourceKeys
import team.aliens.dms.kmp.feature.signup.model.SignUpData
import team.aliens.dms.kmp.feature.signup.model.toJsonString
import team.aliens.dms.kmp.feature.signup.ui.SetId

const val NAVIGATION_SET_ID = "setId"

fun NavGraphBuilder.setId(
    onBackPressed: () -> Unit,
    navigateToSetPassword: (SignUpData) -> Unit,
) {
    composable(
        route = "$NAVIGATION_SET_ID/{${ResourceKeys.SIGN_UP}}",
        arguments = listOf(navArgument(ResourceKeys.SIGN_UP) { type = NavType.StringType }),
    ) {
        SetId(
            onBackPressed = onBackPressed,
            navigateToSetPassword = navigateToSetPassword,
            signUpData = it.getSignUpData(),
        )
    }
}

fun NavController.navigateToSetId(signUpData: SignUpData) {
    navigate("$NAVIGATION_SET_ID/${signUpData.toJsonString()}")
}


