package team.aliens.dms.kmp.core.designsystem.webview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import platform.Foundation.NSMutableURLRequest
import platform.Foundation.NSURL
import platform.WebKit.WKWebView

@Composable
actual fun DmsWebView(
    modifier: Modifier,
    url: String,
) {
    val webView = remember { WKWebView() }
    val request = NSMutableURLRequest.requestWithURL(URL = NSURL(string = url))

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        UIKitView(
            factory = {
                webView.apply {
                    allowsBackForwardNavigationGestures = true
                }
            },
            modifier = Modifier.fillMaxSize(),
        )
        webView.loadRequest(request)
    }
}
