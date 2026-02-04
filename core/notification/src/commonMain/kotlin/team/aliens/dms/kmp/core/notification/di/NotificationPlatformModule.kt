package team.aliens.dms.kmp.core.notification.di

import org.koin.dsl.module

val notificationPlatformModule = module {
    includes(platformNotificationModule)
}
