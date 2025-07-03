package team.aliens.dms.kmp.core.domain.di

import org.koin.dsl.module
import team.aliens.dms.kmp.core.domain.usecase.auth.di.authModule
import team.aliens.dms.kmp.core.domain.usecase.meal.di.mealModule
import team.aliens.dms.kmp.core.domain.usecase.notice.di.noticeModule
import team.aliens.dms.kmp.core.domain.usecase.remains.di.remainsModule
import team.aliens.dms.kmp.core.domain.usecase.student.di.studentModule
import team.aliens.dms.kmp.core.domain.usecase.votes.di.votesModule

val domainModule = module {
    includes(
        authModule,
        mealModule,
        noticeModule,
        studentModule,
        remainsModule,
        votesModule,
    )
}
