package tema.aliens.dms.kmp.feature.remain.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import tema.aliens.dms.kmp.feature.remain.RemainApplication

@Serializable
data object RemainApplicationRoute

fun NavController.navigateToRemainApplication(
    navOptions: NavOptions? = null,
) = navigate(
    route = RemainApplicationRoute,
    navOptions = navOptions,
)

fun NavGraphBuilder.remainApplication(
    onNavigateBack: () -> Unit,
) {
    composable<RemainApplicationRoute> {
        RemainApplication(onNavigateBack = onNavigateBack)
    }
}
