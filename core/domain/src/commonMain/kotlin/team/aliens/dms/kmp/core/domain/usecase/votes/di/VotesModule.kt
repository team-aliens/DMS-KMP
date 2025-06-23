package team.aliens.dms.kmp.core.domain.usecase.votes.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import team.aliens.dms.kmp.core.domain.usecase.votes.GetAllVotesUseCase
import team.aliens.dms.kmp.core.domain.usecase.votes.GetVoteItemsUseCase
import team.aliens.dms.kmp.core.domain.usecase.votes.PostVoteUseCase

internal val votesModule = module {
    singleOf(::GetAllVotesUseCase)
    singleOf(::GetVoteItemsUseCase)
    singleOf(::PostVoteUseCase)
}
