package team.aliens.dms.kmp.feature.application.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import team.aliens.dms.kmp.feature.application.viewmodel.ApplicationViewModel

val applicationModule = module {
    viewModel {
        ApplicationViewModel(
            getRemainUseCase = get(),
            getAllVotesUseCase = get(),
            lateStudyRepository = get(),
        )
    }
}
