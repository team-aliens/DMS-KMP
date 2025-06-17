package team.aliens.dms.kmp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.compose.NavHost
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBar
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarVisuals
import team.aliens.dms.kmp.navigation.authorized.AuthRoute
import team.aliens.dms.kmp.navigation.authorized.authGraph
import team.aliens.dms.kmp.navigation.main.mainGraph

@Composable
internal fun DmsApp(
    modifier: Modifier = Modifier,
    appState: DmsAppState = rememberDmsAppState(),
) {
    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0.dp),
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier
                    .zIndex(2f)
                    .padding(bottom = 100.dp),
                hostState = appState.snackBarHostState,
                snackbar = {
                    val visuals = it.visuals as? DmsSnackBarVisuals ?: return@SnackbarHost
                    DmsSnackBar(
                        snackBarType = visuals.snackBarType,
                        message = visuals.message,
                    )
                },
            )
        },
    ) { innerPadding ->
        NavHost(
            modifier = Modifier
                .background(DmsTheme.colors.background)
                .padding(innerPadding),
            navController = appState.navController,
            startDestination = AuthRoute,
        ) {
            authGraph(appState = appState)
            mainGraph(appState = appState)
        }
    }
}
