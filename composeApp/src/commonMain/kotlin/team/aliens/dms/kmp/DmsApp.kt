package team.aliens.dms.kmp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.navigation.authorized.NAVIGATION_AUTH
import team.aliens.dms.kmp.navigation.authorized.authNavigation
import team.aliens.dms.kmp.navigation.main.mainNavigation

@Composable
internal fun DmsApp() {
    val navigator: DmsNavigator = rememberDmsNavigator()

    DmsTheme {
        NavHost(
            modifier = Modifier
                .background(DmsTheme.colors.background)
                .navigationBarsPadding()
                .statusBarsPadding(),
            navController = navigator.navController,
            startDestination = NAVIGATION_AUTH,
        ) {
            authNavigation(navigator = navigator)
            //mainNavigation(navigator = navigator)
        }
    }
}
