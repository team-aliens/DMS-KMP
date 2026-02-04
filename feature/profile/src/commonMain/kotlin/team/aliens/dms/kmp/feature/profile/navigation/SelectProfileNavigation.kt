package team.aliens.dms.kmp.feature.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.kmp.feature.profile.ui.SelectProfile

@Serializable
data object SelectProfileRoute

fun NavController.navigateToSelectProfile(navOptions: NavOptions? = null) =
    navigate(SelectProfileRoute, navOptions)


fun NavGraphBuilder.selectProfile(
    onBackPressed: () -> Unit,
    onImageSelected: (imageId: String) -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit
) {
    composable<SelectProfileRoute> {
        SelectProfile(
            onBackPressed = onBackPressed,
            onImageSelected = onImageSelected,
            onShowSnackBar = onShowSnackBar,
        )
    }
}
