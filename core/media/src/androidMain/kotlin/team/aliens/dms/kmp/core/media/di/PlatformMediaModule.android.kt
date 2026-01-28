package team.aliens.dms.kmp.core.media.di

import android.content.Context
import org.koin.core.module.Module
import org.koin.dsl.module
import team.aliens.dms.kmp.core.media.AndroidImageCropper
import team.aliens.dms.kmp.core.media.AndroidLocalImageDataSource
import team.aliens.dms.kmp.core.media.ImageCropper
import team.aliens.dms.kmp.core.media.LocalImageDataSource

actual val platformMediaModule: Module = module {
    single<LocalImageDataSource> {
        AndroidLocalImageDataSource(
            contentResolver = get<Context>().contentResolver,
        )
    }
    single<ImageCropper> { AndroidImageCropper() }
}
