package team.aliens.dms.kmp.feature.signup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import team.aliens.dms.kmp.feature.signup.model.SignUpData
import team.aliens.dms.kmp.feature.signup.ui.EnterSchoolVerificationCode

const val NAVIGATION_ENTER_SCHOOL_VERIFICATION_CODE = "enterSchoolVerificationCode"

internal fun NavGraphBuilder.enterSchoolVerificationCode(
    onBackPressed: () -> Unit,
    navigateToEnterSchoolVerificationQuestion: (SignUpData) -> Unit,
) {
    composable(NAVIGATION_ENTER_SCHOOL_VERIFICATION_CODE) {
        EnterSchoolVerificationCode(
            onBackPressed = onBackPressed,
            navigateToEnterSchoolVerificationQuestion = navigateToEnterSchoolVerificationQuestion
        )
    }
}

fun NavController.navigateToEnterSchoolVerificationCode() {
    navigate(NAVIGATION_ENTER_SCHOOL_VERIFICATION_CODE)
}
