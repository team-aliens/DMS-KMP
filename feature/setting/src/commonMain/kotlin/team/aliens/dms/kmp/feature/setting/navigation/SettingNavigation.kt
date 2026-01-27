package team.aliens.dms.kmp.feature.setting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.kmp.feature.setting.ui.Setting

@Serializable
data object SettingRoute

fun NavController.navigateToSetting(
    navOptions: NavOptions? = null,
) = navigate(
    route = SettingRoute,
    navOptions = navOptions,
)

fun NavGraphBuilder.setting(
    onBackPressed: () -> Unit,
    onNavigateResetPassword: () -> Unit,
    onNavigateSelectProfile: () -> Unit,
    onNavigateSignIn: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit
) {
    composable<SettingRoute> {
        Setting(
            onBackPressed = onBackPressed,
            onNavigateResetPassword = onNavigateResetPassword,
            onNavigateSelectProfile = onNavigateSelectProfile,
            onNavigateSignIn = onNavigateSignIn,
            onShowSnackBar = onShowSnackBar,
        )
    }
}
