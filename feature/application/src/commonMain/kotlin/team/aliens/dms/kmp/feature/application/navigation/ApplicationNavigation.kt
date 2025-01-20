package team.aliens.dms.kmp.feature.application.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import team.aliens.dms.kmp.feature.application.ui.Application

const val NAVIGATION_APPLICATION = "application"

fun NavGraphBuilder.application(
    onNavigateRemainApplication: () -> Unit,
    onNavigateOutingApplication: () -> Unit,
) {
    composable(NAVIGATION_APPLICATION) {
        Application(
            onNavigateRemainApplication = onNavigateRemainApplication,
            onNavigateOutingApplication = onNavigateOutingApplication,
        )
    }
}
