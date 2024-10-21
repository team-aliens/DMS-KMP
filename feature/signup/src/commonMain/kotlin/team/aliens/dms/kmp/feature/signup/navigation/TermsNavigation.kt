package team.aliens.dms.kmp.feature.signup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import team.aliens.dms.kmp.core.common.utils.ResourceKeys
import team.aliens.dms.kmp.feature.signup.model.SignUpData
import team.aliens.dms.kmp.feature.signup.model.toJsonString
import team.aliens.dms.kmp.feature.signup.ui.Terms

const val NAVIGATION_TERMS = "terms"

fun NavGraphBuilder.terms(
    onBackPressed: () -> Unit,
    navigateToSignIn: () -> Unit,
    termsUrl: String,
) {
    composable(
        route = "$NAVIGATION_TERMS/{${ResourceKeys.SIGN_UP}}",
        arguments = listOf(navArgument(ResourceKeys.SIGN_UP) { type = NavType.StringType }),
    ) {
        Terms(
            onBackPressed = onBackPressed,
            navigateToSignIn = navigateToSignIn,
            termsUrl = termsUrl,
        )
    }
}

fun NavController.navigateToTerms(signUpData: SignUpData) {
    navigate("$NAVIGATION_TERMS/${signUpData.toJsonString()}")
}
