package team.aliens.dms.kmp.feature.vote.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import team.aliens.dms.kmp.feature.vote.viewmodel.VoteViewModel

val voteModule = module {
    viewModelOf(::VoteViewModel)
}
