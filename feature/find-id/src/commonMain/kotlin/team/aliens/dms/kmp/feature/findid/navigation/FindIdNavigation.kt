package team.aliens.dms.kmp.feature.findid.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.kmp.feature.findid.FindIdScreen

@Serializable
data object FindIdRoute

fun NavController.navigateToFindId(
    navOptions: NavOptions? = null,
) = navigate(
    route = FindIdRoute,
    navOptions = navOptions,
)

fun NavGraphBuilder.findId(
    onNavigateToBack: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    composable<FindIdRoute> {
        FindIdScreen(
            onNavigateToBack = onNavigateToBack,
            onShowSnackBar = onShowSnackBar,
        )
    }
}
