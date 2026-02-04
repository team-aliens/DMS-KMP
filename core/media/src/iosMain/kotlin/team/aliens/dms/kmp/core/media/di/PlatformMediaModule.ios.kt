package team.aliens.dms.kmp.core.media.di

import org.koin.core.module.Module
import org.koin.dsl.module
import team.aliens.dms.kmp.core.media.ImageCropper
import team.aliens.dms.kmp.core.media.IosImageCropper
import team.aliens.dms.kmp.core.media.IosLocalImageDataSource
import team.aliens.dms.kmp.core.media.LocalImageDataSource

actual val platformMediaModule: Module = module {
    single<LocalImageDataSource> { IosLocalImageDataSource() }
    single<ImageCropper> { IosImageCropper() }
}
