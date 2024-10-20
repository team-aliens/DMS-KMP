package team.aliens.dms.kmp.core.designsystem.webview

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun DmsWebView(
    modifier: Modifier = Modifier,
    url: String,
)
