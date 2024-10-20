package team.aliens.dms.kmp.feature.signup.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import team.aliens.dms.kmp.core.common.utils.ResourceKeys
import team.aliens.dms.kmp.feature.signup.model.SignUpData
import team.aliens.dms.kmp.feature.signup.model.toSignUpData

const val NAVIGATION_SIGN_UP = "signUp"

fun NavGraphBuilder.signUp(
    onBackPressed: () -> Unit,
    navigateToEnterSchoolVerificationQuestion: (SignUpData) -> Unit,
    navigateToEnterEmail: (SignUpData) -> Unit,
    navigateToEnterEmailVerificationCode: (SignUpData) -> Unit,
    navigateToSetId: (SignUpData) -> Unit,
    navigateToSetPassword: (SignUpData) -> Unit,
    navigateToTerms: (SignUpData) -> Unit,
    navigateToSignIn: () -> Unit,
    termsUrl: String,
) {
    navigation(
        route = NAVIGATION_SIGN_UP,
        startDestination = NAVIGATION_ENTER_SCHOOL_VERIFICATION_CODE,
    ) {
        enterSchoolVerificationCode(
            onBackPressed = onBackPressed,
            navigateToEnterSchoolVerificationQuestion = navigateToEnterSchoolVerificationQuestion,
        )
        enterSchoolVerificationQuestion(
            onBackPressed = onBackPressed,
            navigateToEnterEmail = navigateToEnterEmail,
        )
        enterEmail(
            onBackPressed = onBackPressed,
            navigateToEnterEmailVerificationCode = navigateToEnterEmailVerificationCode,
        )
        enterEmailVerificationCode(
            onBackPressed = onBackPressed,
            navigateToSetId = navigateToSetId,
        )
        setId(
            onBackPressed = onBackPressed,
            navigateToSetPassword = navigateToSetPassword,
        )
        setPassword(
            onBackPressed = onBackPressed,
            navigateToTerms = navigateToTerms,
        )
        terms(
            onBackPressed = onBackPressed,
            navigateToSignIn = navigateToSignIn,
            termsUrl = termsUrl,
        )
    }
}

fun NavController.navigateToSignUp() {
    navigate(NAVIGATION_SIGN_UP)
}

internal fun NavBackStackEntry.getSignUpData(): SignUpData {
    val signUpData = arguments?.getString(ResourceKeys.SIGN_UP) ?: throw NullPointerException()
    return signUpData.toSignUpData()
}
