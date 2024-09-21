package team.aliens.dms.kmp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import team.aliens.dms.kmp.navigation.authorized.AuthNavigator
import team.aliens.dms.kmp.navigation.main.MainNavigator

internal class DmsNavigator(
    val navController: NavHostController,
) : AuthNavigator, MainNavigator

@Composable
internal fun rememberDmsNavigator(navController: NavHostController = rememberNavController()): DmsNavigator =
    remember(navController) {
        DmsNavigator(navController)
    }
