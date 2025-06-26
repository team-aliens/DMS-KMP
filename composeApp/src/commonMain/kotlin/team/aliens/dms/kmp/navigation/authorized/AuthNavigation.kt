package team.aliens.dms.kmp.navigation.authorized

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.core.network.PlatformConfig
import team.aliens.dms.kmp.feature.signin.navigation.navigateToSignIn
import team.aliens.dms.kmp.feature.signin.navigation.signIn
import team.aliens.dms.kmp.feature.signup.navigation.navigateToEnterEmail
import team.aliens.dms.kmp.feature.signup.navigation.navigateToEnterEmailVerificationCode
import team.aliens.dms.kmp.feature.signup.navigation.navigateToEnterSchoolVerificationQuestion
import team.aliens.dms.kmp.feature.signup.navigation.navigateToEnterStudentNumber
import team.aliens.dms.kmp.feature.signup.navigation.navigateToSetId
import team.aliens.dms.kmp.feature.signup.navigation.navigateToSetPassword
import team.aliens.dms.kmp.feature.signup.navigation.navigateToSignUp
import team.aliens.dms.kmp.feature.signup.navigation.navigateToTerms
import team.aliens.dms.kmp.feature.signup.navigation.signupGraph
import team.aliens.dms.kmp.feature.splash.navigation.SplashRoute
import team.aliens.dms.kmp.feature.splash.navigation.splash
import team.aliens.dms.kmp.navigation.main.navigateToMain
import team.aliens.dms.kmp.ui.DmsAppState

@Serializable
data object AuthRoute

fun NavController.navigateToAuth(
    navOptions: NavOptions? = null,
) = navigate(
    route = AuthRoute,
    navOptions = navOptions,
)

fun NavGraphBuilder.authGraph(
    appState: DmsAppState,
) {
    navigation<AuthRoute>(
        startDestination = SplashRoute,
    ) {
        splash(
            navigateToSignIn = appState.navController::navigateToSignIn,
            navigateToMain = appState.navController::navigateToMain,
        )
        signIn(
            navigateToMain = appState.navController::navigateToMain,
            navigateToSignUp = appState.navController::navigateToSignUp,
            navigateToFindId = { },
            navigateToFindPassword = { },
            onShowSnackBar = appState::showSnackBar,
        )
        signupGraph(
            onBackPressed = appState.navController::navigateUp,
            navigateToEnterSchoolVerificationQuestion = appState.navController::navigateToEnterSchoolVerificationQuestion,
            navigateToEnterEmail = appState.navController::navigateToEnterEmail,
            navigateToEnterEmailVerificationCode = appState.navController::navigateToEnterEmailVerificationCode,
            navigateToEnterStudentNumber = appState.navController::navigateToEnterStudentNumber,
            navigateToSetId = appState.navController::navigateToSetId,
            navigateToSetPassword = appState.navController::navigateToSetPassword,
            navigateToTerms = appState.navController::navigateToTerms,
            navigateToSignIn = appState.navController::navigateToSignIn,
            termsUrl = PlatformConfig.termsUrl,
        )
    }
}
