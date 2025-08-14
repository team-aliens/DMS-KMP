package team.aliens.dms.kmp.feature.volunteer.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel
import team.aliens.dms.kmp.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.kmp.core.designsystem.webview.DmsWebView
import team.aliens.dms.kmp.feature.volunteer.viewmodel.VolunteerState
import team.aliens.dms.kmp.feature.volunteer.viewmodel.VolunteerViewModel

@Composable
internal fun Volunteer(
    viewModel: VolunteerViewModel = koinViewModel(),
    onNavigateBack: () -> Unit,
    webViewUrl: String,
) {
    val state by viewModel.state.collectAsState()
    val theme = if (isSystemInDarkTheme()) {
        "dark"
    } else {
        "light"
    }

    VolunteerScreen(
        state = state,
        onNavigateBack = onNavigateBack,
        webViewUrl = webViewUrl,
        theme = theme,
    )
}

@Composable
private fun VolunteerScreen(
    state: VolunteerState,
    onNavigateBack: () -> Unit,
    webViewUrl: String,
    theme: String,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        DmsTopAppBar(
            title = "봉사 신청",
            onBackPressed = onNavigateBack,
        )
        if(state.jwtToken.isNotEmpty()) {
            DmsWebView(
                modifier = Modifier.fillMaxSize(),
                url = "$webViewUrl/volunteer/application?theme=$theme",
                jwtToken = state.jwtToken,
            )
        }
    }
}
