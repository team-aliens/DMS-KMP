package team.aliens.dms.kmp.core.domain.usecase.file.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import team.aliens.dms.kmp.core.domain.usecase.file.FetchPresignedUrlUseCase
import team.aliens.dms.kmp.core.domain.usecase.file.UploadFileUseCase

internal val fileModule = module {
    singleOf(::FetchPresignedUrlUseCase)
    singleOf(::UploadFileUseCase)
}
