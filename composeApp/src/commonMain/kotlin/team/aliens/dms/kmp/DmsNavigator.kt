package team.aliens.dms.kmp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import team.aliens.dms.kmp.core.network.PlatformConfig
import team.aliens.dms.kmp.feature.signin.navigation.navigateToSignIn
import team.aliens.dms.kmp.feature.signup.model.SignUpData
import team.aliens.dms.kmp.feature.signup.navigation.navigateToEnterEmail
import team.aliens.dms.kmp.feature.signup.navigation.navigateToEnterEmailVerificationCode
import team.aliens.dms.kmp.feature.signup.navigation.navigateToEnterSchoolVerificationCode
import team.aliens.dms.kmp.feature.signup.navigation.navigateToEnterSchoolVerificationQuestion
import team.aliens.dms.kmp.feature.signup.navigation.navigateToSetId
import team.aliens.dms.kmp.feature.signup.navigation.navigateToSetPassword
import team.aliens.dms.kmp.feature.signup.navigation.navigateToSignUp
import team.aliens.dms.kmp.feature.signup.navigation.navigateToTerms

internal class DmsNavigator(
    val navController: NavHostController,
) {

    fun navigateToSignIn() {
        navController.navigateToSignIn()
    }

    fun navigateToSignUp() {
        navController.navigateToSignUp()
    }

    fun navigateToEnterSchoolVerificationCode() {
        navController.navigateToEnterSchoolVerificationCode()
    }

    fun navigateToEnterSchoolVerificationQuestion(signUpData: SignUpData) {
        navController.navigateToEnterSchoolVerificationQuestion(signUpData = signUpData)
    }

    fun navigateToEnterEmail(signUpData: SignUpData) {
        navController.navigateToEnterEmail(signUpData = signUpData)
    }

    fun navigateToEnterEmailVerificationCode(signUpData: SignUpData) {
        navController.navigateToEnterEmailVerificationCode(signUpData = signUpData)
    }

    fun navigateToSetId(signUpData: SignUpData) {
        navController.navigateToSetId(signUpData = signUpData)
    }

    fun navigateToSetPassword(signUpData: SignUpData) {
        navController.navigateToSetPassword(signUpData = signUpData)
    }

    fun navigateToTerms(signUpData: SignUpData) {
        navController.navigateToTerms(signUpData = signUpData)
    }

    fun popBackStackIfNotHome() {
        if (!isSameCurrentDestination()) {
            popBackStack()
        }
    }

    // TODO: private로 변경
    fun popBackStack() {
        navController.popBackStack()
    }

    private fun isSameCurrentDestination(): Boolean {
        return navController.currentDestination?.route == ""
    }
}

@Composable
internal fun rememberDmsNavigator(navController: NavHostController = rememberNavController()): DmsNavigator =
    remember(navController) {
        DmsNavigator(navController)
    }
