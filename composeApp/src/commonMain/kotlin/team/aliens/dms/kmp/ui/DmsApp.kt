package team.aliens.dms.kmp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.navigation.authorized.AuthRoute
import team.aliens.dms.kmp.navigation.authorized.authGraph
import team.aliens.dms.kmp.navigation.main.mainGraph

@Composable
internal fun DmsApp(
    appState: DmsAppState = rememberDmsAppState(),
) {
    DmsTheme {
        NavHost(
            modifier = Modifier
                .background(DmsTheme.colors.background)
                .navigationBarsPadding()
                .statusBarsPadding(),
            navController = appState.navController,
            startDestination = AuthRoute,
        ) {
            authGraph(appState = appState)
            mainGraph(appState = appState)
        }
    }
}
