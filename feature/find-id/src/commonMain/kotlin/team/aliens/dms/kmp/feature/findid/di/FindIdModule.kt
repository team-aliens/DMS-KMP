package team.aliens.dms.kmp.feature.findid.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import team.aliens.dms.kmp.feature.findid.FindIdViewModel

val findIdModule = module {
    viewModelOf(::FindIdViewModel)
}
