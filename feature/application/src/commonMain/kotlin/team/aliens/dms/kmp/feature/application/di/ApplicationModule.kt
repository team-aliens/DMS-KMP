package team.aliens.dms.kmp.feature.application.di

import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module
import team.aliens.dms.kmp.core.data.latestudy.repository.LateStudyRepository
import team.aliens.dms.kmp.core.domain.usecase.remains.GetRemainUseCase
import team.aliens.dms.kmp.core.domain.usecase.votes.GetAllVotesUseCase
import team.aliens.dms.kmp.feature.application.viewmodel.ApplicationViewModel

val applicationModule = module {
    viewModel {
        ApplicationViewModel(
            getRemainUseCase = get<GetRemainUseCase>(),
            getAllVotesUseCase = get<GetAllVotesUseCase>(),
            lateStudyRepository = get<LateStudyRepository>(),
        )
    }
}
