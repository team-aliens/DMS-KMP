package team.aliens.dms.kmp.core.designsystem.webview

import android.graphics.Bitmap
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.status.ErrorStatus
import team.aliens.dms.kmp.core.designsystem.webview.exception.WebViewErrorException

@Composable
actual fun DmsWebView(
    modifier: Modifier,
    url: String,
    jwtToken: String?,
) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    val headers = mapOf("Authorization" to "Bearer $jwtToken")

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                WebView(context).apply {
                    webViewClient = object : WebViewClient() {
                        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                            super.onPageStarted(view, url, favicon)
                            isLoading = true
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            isLoading = false
                        }

                        override fun onReceivedError(
                            view: WebView?,
                            request: WebResourceRequest?,
                            error: WebResourceError?,
                        ) {
                            super.onReceivedError(view, request, error)
                            isLoading = false
                            try {
                                throw WebViewErrorException("WebView error: ${error?.description}")
                            } catch (e: Exception) {
                                e.printStackTrace()
                                isError = true
                            }
                        }

                        override fun shouldOverrideUrlLoading(
                            view: WebView?,
                            request: WebResourceRequest?,
                        ): Boolean {
                            request?.let {
                                view?.loadUrl(url, headers)
                                return true
                            }
                            return false
                        }

                        override fun shouldInterceptRequest(
                            view: WebView?,
                            request: WebResourceRequest?,
                        ): WebResourceResponse? {
                            Log.d("DmsWebView", request?.requestHeaders.toString())
                            return super.shouldInterceptRequest(view, request)
                        }
                    }
                    webChromeClient = WebChromeClient()
                    settings.javaScriptEnabled = true
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                    )
                    settings.userAgentString = "userAgent"
                    settings.allowFileAccess = true
                    settings.domStorageEnabled = true
                }
            },
            update = {
                it.loadUrl(url, headers)
            },
        )

        if (isLoading) {
            CircularProgressIndicator()
        }

        if (isError) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DmsTheme.colors.background),
                contentAlignment = Alignment.Center,
            ) {
                ErrorStatus(title = "화면을 불러오지 못했어요.")
            }
        }
    }
}
