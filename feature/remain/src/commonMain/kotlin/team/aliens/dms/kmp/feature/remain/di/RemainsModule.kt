package team.aliens.dms.kmp.feature.remain.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import team.aliens.dms.kmp.feature.remain.RemainApplicationViewModel

val remainsModule = module {
    viewModelOf(::RemainApplicationViewModel)
}
