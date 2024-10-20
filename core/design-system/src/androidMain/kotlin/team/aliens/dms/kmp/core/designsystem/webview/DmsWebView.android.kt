package team.aliens.dms.kmp.core.designsystem.webview

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

@Composable
actual fun DmsWebView(
    modifier: Modifier,
    url: String,
) {
    val context = LocalContext.current

    AndroidView(
        factory = {
            WebView(context).apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
            }
        },
        modifier = Modifier.then(modifier),
        update = { it.loadUrl(url) },
    )
}
