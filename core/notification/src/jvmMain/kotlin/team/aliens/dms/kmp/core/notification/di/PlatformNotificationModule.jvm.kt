package team.aliens.dms.kmp.core.notification.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import team.aliens.dms.kmp.core.notification.DeviceTokenManager
import team.aliens.dms.kmp.core.notification.JvmDeviceTokenManager
import team.aliens.dms.kmp.core.notification.JvmNotificationManager
import team.aliens.dms.kmp.core.notification.NotificationManager

internal actual val platformNotificationModule = module {
    singleOf(::JvmDeviceTokenManager) { bind<DeviceTokenManager>() }
    singleOf(::JvmNotificationManager) { bind<NotificationManager>() }
}
