package team.aliens.dms.kmp.feature.signup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.core.common.navtype.SignUpDataNavType
import team.aliens.dms.kmp.core.model.signup.SignUpData

@Serializable
sealed interface SignUp {
    @Serializable
    data object Route {
        val NavTypeMap = mapOf(SignUpDataNavType)

        @Serializable
        data object EnterSchoolVerificationCodeRoute : SignUp

        @Serializable
        data class EnterSchoolVerificationQuestion(val signUpData: SignUpData) : SignUp

        @Serializable
        data class EnterEmail(val signUpData: SignUpData) : SignUp

        @Serializable
        data class EnterEmailVerificationCode(val signUpData: SignUpData) : SignUp

        @Serializable
        data class EnterStudentNumber(val signUpData: SignUpData) : SignUp

        @Serializable
        data class SetId(val signUpData: SignUpData) : SignUp

        @Serializable
        data class SetPassword(val signUpData: SignUpData) : SignUp

        @Serializable
        data class Terms(val signUpData: SignUpData) : SignUp
    }
}

fun NavController.navigateToSignUp(
    route: SignUp = SignUp.Route.EnterSchoolVerificationCodeRoute,
    navOptions: NavOptions? = null,
) = navigate(
    route = route,
    navOptions = navOptions,
)

fun NavGraphBuilder.signupGraph(
    onBackPressed: () -> Unit,
    navigateToEnterSchoolVerificationQuestion: (SignUpData) -> Unit,
    navigateToEnterEmail: (SignUpData) -> Unit,
    navigateToEnterEmailVerificationCode: (SignUpData) -> Unit,
    navigateToEnterStudentNumber: (SignUpData) -> Unit,
    navigateToSetId: (SignUpData) -> Unit,
    navigateToSetPassword: (SignUpData) -> Unit,
    navigateToTerms: (SignUpData) -> Unit,
    navigateToSignIn: () -> Unit,
    termsUrl: String,
) {
    navigation<SignUp.Route>(
        startDestination = SignUp.Route.EnterSchoolVerificationCodeRoute,
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
            navigateToEnterStudentNumber = navigateToEnterStudentNumber,
        )
        enterStudentNumber(
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
