package tema.aliens.dms.kmp.feature.remain.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import tema.aliens.dms.kmp.feature.remain.RemainApplication

const val NAVIGATION_REMAIN_APPLICATION = "remainApplication"

fun NavGraphBuilder.remainApplication() {
    composable(NAVIGATION_REMAIN_APPLICATION) {
        RemainApplication()
    }
}

fun NavController.navigateToRemainApplication() {
    navigate(NAVIGATION_REMAIN_APPLICATION)
}
