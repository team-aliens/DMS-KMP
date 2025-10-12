package team.aliens.dms.kmp.feature.point.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import team.aliens.dms.kmp.feature.point.viewmodel.PointHistoryViewModel

val pointsModule = module {
    viewModelOf(::PointHistoryViewModel)
}
