package team.aliens.dms.kmp.feature.outing.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import team.aliens.dms.kmp.feature.outing.OutingApplication

const val NAVIGATION_OUTING_APPLICATION = "outingApplication"

fun NavGraphBuilder.outingApplication() {
    composable(NAVIGATION_OUTING_APPLICATION) {
        OutingApplication()
    }
}

fun NavController.navigateToOutingApplication() {
    navigate(NAVIGATION_OUTING_APPLICATION)
}
