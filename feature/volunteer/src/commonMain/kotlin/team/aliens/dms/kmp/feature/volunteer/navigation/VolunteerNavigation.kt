package team.aliens.dms.kmp.feature.volunteer.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.feature.volunteer.ui.Volunteer

@Serializable
data object VolunteerRoute

fun NavController.navigateToVolunteer(
    navOptions: NavOptions? = null,
) = navigate(
    route = VolunteerRoute,
    navOptions = navOptions,
)

fun NavGraphBuilder.volunteer(
    onNavigateBack: () -> Unit,
    webViewUrl: String,
) {
    composable<VolunteerRoute> {
        Volunteer(
            onNavigateBack = onNavigateBack,
            webViewUrl = webViewUrl,
        )
    }
}
