package team.aliens.dms.kmp.core.domain.usecase.points.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import team.aliens.dms.kmp.core.domain.usecase.points.GetPointsUseCase

internal val pointsModule = module {
    singleOf(::GetPointsUseCase)
}
