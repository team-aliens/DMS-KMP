package resetpassword.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
import resetpassword.ResetPasswordScreen

@Serializable
data object ResetPasswordRoute

fun NavController.navigateToResetPassword(
    navOptions: NavOptions? = null,
) = navigate(
    route = ResetPasswordRoute,
    navOptions = navOptions,
)

fun NavGraphBuilder.resetPassword(
    onNavigateToBack: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    composable<ResetPasswordRoute> {
        ResetPasswordScreen(
            onNavigateBack = onNavigateToBack,
            onShowSnackBar = onShowSnackBar,
        )
    }
}
