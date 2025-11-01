package team.aliens.dms.kmp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Scaffold(
            modifier = modifier,
            contentWindowInsets = WindowInsets(0.dp),
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
        SnackbarHost(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .padding(top = 16.dp)
                .zIndex(2f),
            hostState = appState.snackBarHostState,
            snackbar = {
                val visuals = it.visuals as? DmsSnackBarVisuals ?: return@SnackbarHost
                DmsSnackBar(
                    snackBarType = visuals.snackBarType,
                    message = visuals.message,
                )
            },
        )
    }

}
