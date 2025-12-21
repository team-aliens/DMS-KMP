package team.aliens.dms.kmp.feature.find_id.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import team.aliens.dms.kmp.feature.find_id.FindIdViewModel

val findIdModule = module {
    viewModelOf(::FindIdViewModel)
}
