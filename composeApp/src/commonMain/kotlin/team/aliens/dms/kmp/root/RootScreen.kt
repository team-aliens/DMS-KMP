package team.aliens.dms.kmp.root

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.feature.application.navigation.application
import team.aliens.dms.kmp.feature.home.navigation.NAVIGATION_HOME
import team.aliens.dms.kmp.feature.home.navigation.home
import team.aliens.dms.kmp.feature.mypage.navigation.myPage
import team.aliens.dms.kmp.feature.notice.navigation.notices
import team.aliens.dms.kmp.ui.BottomNavigationBar

@Composable
internal fun Root() {
    RootScreen()
}

@Composable
private fun RootScreen() {
    val navController: NavHostController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) },
    ) {
        NavHost(
            navController = navController,
            startDestination = NAVIGATION_HOME,
            modifier = Modifier
                .background(DmsTheme.colors.onBackground)
                .padding(bottom = it.calculateBottomPadding()),
        ) {
            home()
            application()
            notices()
            myPage()
        }
    }
}
