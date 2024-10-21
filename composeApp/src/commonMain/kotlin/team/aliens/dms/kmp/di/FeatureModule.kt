package team.aliens.dms.kmp.di

import org.koin.dsl.module
import team.aliens.dms.kmp.feature.signin.di.signInModule
import team.aliens.dms.kmp.feature.signup.di.signUpModule
import team.aliens.dms.kmp.feature.splash.di.splashModule

internal val featureModule = module {
    includes(
        splashModule,
        signInModule,
        signUpModule,
    )
}
