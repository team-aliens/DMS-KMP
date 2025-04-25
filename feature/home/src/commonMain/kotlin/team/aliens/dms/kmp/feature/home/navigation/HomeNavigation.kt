package team.aliens.dms.kmp.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.feature.home.ui.Home

@Serializable
data object HomeRoute

fun NavGraphBuilder.home() {
    composable<HomeRoute> {
        Home()
    }
}
