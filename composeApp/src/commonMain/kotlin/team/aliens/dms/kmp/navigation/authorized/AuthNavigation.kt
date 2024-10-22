package team.aliens.dms.kmp.navigation.authorized

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import team.aliens.dms.kmp.DmsNavigator
import team.aliens.dms.kmp.core.network.PlatformConfig
import team.aliens.dms.kmp.feature.signin.navigation.signIn
import team.aliens.dms.kmp.feature.signup.navigation.signUp
import team.aliens.dms.kmp.feature.splash.navigation.NAVIGATION_SPLASH
import team.aliens.dms.kmp.feature.splash.navigation.splash

const val NAVIGATION_AUTH = "auth"

internal fun NavGraphBuilder.authNavigation(
    navigator: DmsNavigator,
) {
    navigation(
        route = NAVIGATION_AUTH,
        startDestination = NAVIGATION_SPLASH,
    ) {
        splash(
            navigateToLogin = navigator::navigateToSignIn,
        )
        signIn(
            navigateToMain = { },
            navigateToSignUp = navigator::navigateToSignUp,
            navigateToFindId = { },
            navigateToFindPassword = { },
        )
        signUp(
            onBackPressed = navigator::popBackStack,
            navigateToEnterSchoolVerificationQuestion = navigator::navigateToEnterSchoolVerificationQuestion,
            navigateToEnterEmail = navigator::navigateToEnterEmail,
            navigateToEnterEmailVerificationCode = navigator::navigateToEnterEmailVerificationCode,
            navigateToSetId = navigator::navigateToSetId,
            navigateToSetPassword = navigator::navigateToSetPassword,
            navigateToTerms = navigator::navigateToTerms,
            navigateToSignIn = navigator::navigateToSignIn,
            termsUrl = PlatformConfig.termsUrl,
        )
    }
}
