package team.aliens.dms.kmp.feature.outing.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.feature.outing.OutingApplication

@Serializable
data object OutingApplicationRoute

fun NavController.navigateToOutingApplication(
    navOptions: NavOptions? = null,
) = navigate(
    route = OutingApplicationRoute,
    navOptions = navOptions,
)

fun NavGraphBuilder.outingApplication() {
    composable<OutingApplicationRoute> {
        OutingApplication()
    }
}
