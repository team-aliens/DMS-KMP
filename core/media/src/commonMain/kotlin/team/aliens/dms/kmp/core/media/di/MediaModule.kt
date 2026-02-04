package team.aliens.dms.kmp.core.media.di

import org.koin.dsl.module

val mediaModule = module {
    includes(platformMediaModule)
}
