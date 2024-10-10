package team.aliens.dms.kmp.core.domain.di

import org.koin.dsl.module
import team.aliens.dms.kmp.core.domain.usecase.auth.di.authModule

val domainModule = module {
    includes(
        authModule
    )
}
