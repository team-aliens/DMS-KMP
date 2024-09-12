package team.aliens.dms.kmp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import team.aliens.dms.kmp.navigation.authorized.authNavigation
import team.aliens.dms.kmp.navigation.main.mainNavigation

@Composable
internal fun DmsApp() {
    val navigator: DmsNavigator = rememberDmsNavigator()

    // TODO: 배경 색 디자인 시스템 적용
    NavHost(
        modifier = Modifier
            .background(Color.White)
            .navigationBarsPadding()
            .statusBarsPadding(),
        navController = navigator.navController,
        startDestination = ""
    ) {
        authNavigation(navigator = navigator)
        mainNavigation(navigator = navigator)
    }
}
