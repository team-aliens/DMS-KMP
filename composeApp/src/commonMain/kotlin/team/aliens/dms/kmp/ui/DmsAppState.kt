package team.aliens.dms.kmp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberDmsAppState(
    navController: NavHostController = rememberNavController(),
): DmsAppState {
    return remember(navController) {
        DmsAppState(navController = navController)
    }
}

@Stable
class DmsAppState(
    val navController: NavHostController,
)
