package team.aliens.dms.kmp.core.jwt.di

import org.koin.dsl.module

val jwtModule = module {
    includes(
        storeModule,
        dataSourceModule,
        managerModule,
    )
}
