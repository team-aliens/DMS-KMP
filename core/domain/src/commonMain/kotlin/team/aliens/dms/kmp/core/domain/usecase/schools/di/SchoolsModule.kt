package team.aliens.dms.kmp.core.domain.usecase.schools.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import team.aliens.dms.kmp.core.domain.usecase.schools.GetSchoolVerificationCodeCheckUseCase
import team.aliens.dms.kmp.core.domain.usecase.schools.GetSchoolVerificationQuestionAnswerCheckUseCase
import team.aliens.dms.kmp.core.domain.usecase.schools.GetSchoolVerificationQuestionCheckUseCase

internal val schoolsModule = module {
    singleOf(::GetSchoolVerificationQuestionCheckUseCase)
    singleOf(::GetSchoolVerificationQuestionAnswerCheckUseCase)
    singleOf(::GetSchoolVerificationCodeCheckUseCase)
}
