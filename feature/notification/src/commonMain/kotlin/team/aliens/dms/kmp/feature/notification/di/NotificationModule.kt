package team.aliens.dms.kmp.feature.notification.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import team.aliens.dms.kmp.feature.notification.viewmodel.NotificationViewModel

val notificationModule = module {
    viewModelOf(::NotificationViewModel)
}