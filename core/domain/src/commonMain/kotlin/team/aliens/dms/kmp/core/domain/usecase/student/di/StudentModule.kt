package team.aliens.dms.kmp.core.domain.usecase.student.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import team.aliens.dms.kmp.core.domain.usecase.student.CheckEmailDuplicationUseCase
import team.aliens.dms.kmp.core.domain.usecase.student.CheckIdDuplicationUseCase
import team.aliens.dms.kmp.core.domain.usecase.student.EditProfileUseCase
import team.aliens.dms.kmp.core.domain.usecase.student.ExamineStudentNumberUseCase
import team.aliens.dms.kmp.core.domain.usecase.student.FindIdUseCase
import team.aliens.dms.kmp.core.domain.usecase.student.GetMyPageUseCase
import team.aliens.dms.kmp.core.domain.usecase.student.GetStudentsUseCase
import team.aliens.dms.kmp.core.domain.usecase.student.ResetPasswordUseCase
import team.aliens.dms.kmp.core.domain.usecase.student.SignUpUseCase
import team.aliens.dms.kmp.core.domain.usecase.student.WithdrawUseCase

internal val studentModule = module {
    singleOf(::CheckEmailDuplicationUseCase)
    singleOf(::CheckIdDuplicationUseCase)
    singleOf(::EditProfileUseCase)
    singleOf(::ExamineStudentNumberUseCase)
    singleOf(::FindIdUseCase)
    singleOf(::GetMyPageUseCase)
    singleOf(::GetStudentsUseCase)
    singleOf(::ResetPasswordUseCase)
    singleOf(::SignUpUseCase)
    singleOf(::WithdrawUseCase)
}
