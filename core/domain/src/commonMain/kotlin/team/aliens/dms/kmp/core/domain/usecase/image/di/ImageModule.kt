package team.aliens.dms.kmp.core.domain.usecase.image.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import team.aliens.dms.kmp.core.domain.usecase.image.CropImageUseCase
import team.aliens.dms.kmp.core.domain.usecase.image.GetGalleryImagesUseCase
import team.aliens.dms.kmp.core.domain.usecase.image.GetImageBytesUseCase

internal val imageModule = module {
    singleOf(::GetGalleryImagesUseCase)
    singleOf(::GetImageBytesUseCase)
    singleOf(::CropImageUseCase)
}
