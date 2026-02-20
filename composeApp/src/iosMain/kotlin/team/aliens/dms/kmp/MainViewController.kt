package team.aliens.dms.kmp

import androidx.compose.ui.window.ComposeUIViewController
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import team.aliens.dms.kmp.coil.PhAssetFetcher
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.ui.DmsApp

fun mainViewController() = ComposeUIViewController {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .components {
                add(KtorNetworkFetcherFactory())
                add(PhAssetFetcher.Factory())
            }
            .build()
    }
    DmsTheme {
        DmsApp()
    }
}