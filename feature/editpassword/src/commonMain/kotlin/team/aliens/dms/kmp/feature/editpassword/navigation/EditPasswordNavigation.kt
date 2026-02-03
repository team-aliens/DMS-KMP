package team.aliens.dms.kmp.feature.editpassword.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.kmp.feature.editpassword.ui.CheckPassword
import team.aliens.dms.kmp.feature.editpassword.ui.EditPassword

@Serializable
data object CheckPasswordRoute

@Serializable
data class EditPasswordRoute(val currentPassword: String)

fun NavController.navigateToCheckPassword(
    navOptions: NavOptions? = null,
) = navigate(
    route = CheckPasswordRoute,
    navOptions = navOptions,
)

fun NavController.navigateToEditPassword(
    currentPassword: String,
    navOptions: NavOptions? = null,
) = navigate(
    route = EditPasswordRoute(currentPassword = currentPassword),
    navOptions = navOptions,
)

fun NavGraphBuilder.checkPassword(
    onNavigateEditPassword: (String) -> Unit,
    onBackPressed: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    composable<CheckPasswordRoute> {
        CheckPassword(
            onBackPressed = onBackPressed,
            onNavigateResetPassword = onNavigateEditPassword,
            onShowSnackBar = onShowSnackBar,
        )
    }
}

fun NavGraphBuilder.editPassword(
    onBackPressed: () -> Unit,
    onNavigateSetting: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    composable<EditPasswordRoute> { backStackEntry ->
        val route = backStackEntry.toRoute<EditPasswordRoute>()
        EditPassword(
            onBackPressed = onBackPressed,
            currentPassword = route.currentPassword,
            onNavigateSetting = onNavigateSetting,
            onShowSnackBar = onShowSnackBar,
        )
    }
}
