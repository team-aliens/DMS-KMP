package team.aliens.dms.kmp.feature.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.kmp.feature.profile.ui.AdjustProfile


@Serializable
data class AdjustProfileRoute(val imageId: String)

fun NavController.navigateToAdjustProfile(imageId: String, navOptions: NavOptions? = null) =
    navigate(AdjustProfileRoute(imageId = imageId), navOptions)

fun NavGraphBuilder.adjustProfile(
    onBackPressed: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit
) {
    composable<AdjustProfileRoute> {
        AdjustProfile(
            onBackPressed = onBackPressed,
            onShowSnackBar = onShowSnackBar,
        )
    }
}
