package team.aliens.dms.kmp.core.datastore.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import team.aliens.dms.kmp.core.datastore.auth.AuthPreferencesDataSource
import team.aliens.dms.kmp.core.datastore.auth.AuthPreferencesDataSourceImpl
import team.aliens.dms.kmp.core.datastore.onboarding.OnboardingPreferencesDataSource
import team.aliens.dms.kmp.core.datastore.onboarding.OnboardingPreferencesDataSourceImpl
import team.aliens.dms.kmp.core.datastore.remain.RemainPreferencesDataSource
import team.aliens.dms.kmp.core.datastore.remain.RemainPreferencesDataSourceImpl

internal val dataSourceModule = module {
    singleOf(::AuthPreferencesDataSourceImpl) { bind<AuthPreferencesDataSource>() }
    singleOf(::OnboardingPreferencesDataSourceImpl) { bind<OnboardingPreferencesDataSource>() }
    singleOf(::RemainPreferencesDataSourceImpl) { bind<RemainPreferencesDataSource>() }
}
