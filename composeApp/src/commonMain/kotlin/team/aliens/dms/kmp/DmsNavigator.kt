package team.aliens.dms.kmp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import team.aliens.dms.kmp.feature.signin.navigation.navigateToSignIn

internal class DmsNavigator(
    val navController: NavHostController,
) {

    fun navigateToLogin() {
        navController.navigateToSignIn()
    }
}

@Composable
internal fun rememberDmsNavigator(navController: NavHostController = rememberNavController()): DmsNavigator =
    remember(navController) {
        DmsNavigator(navController)
    }
