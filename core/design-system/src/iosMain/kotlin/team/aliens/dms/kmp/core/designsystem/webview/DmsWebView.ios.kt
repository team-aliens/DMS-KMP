package team.aliens.dms.kmp.core.designsystem.webview

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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

    webView.apply {
        loadRequest(request)
        allowsBackForwardNavigationGestures = true
    }

    UIKitView(
        factory = { webView },
        modifier = Modifier.fillMaxSize()
    )
}
