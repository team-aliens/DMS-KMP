package team.aliens.dms.kmp.feature.application.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.feature.application.ui.Application

@Serializable
data object ApplicationRoute

fun NavGraphBuilder.application(
    onNavigateRemainApplication: () -> Unit,
    onNavigateOutingApplication: () -> Unit,
) {
    composable<ApplicationRoute> {
        Application(
            onNavigateRemainApplication = onNavigateRemainApplication,
            onNavigateOutingApplication = onNavigateOutingApplication,
        )
    }
}
