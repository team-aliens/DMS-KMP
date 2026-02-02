package team.aliens.dms.kmp.core.notification.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import team.aliens.dms.kmp.core.notification.DeviceTokenManager
import team.aliens.dms.kmp.core.notification.IosDeviceTokenManager
import team.aliens.dms.kmp.core.notification.IosNotificationManager
import team.aliens.dms.kmp.core.notification.NotificationManager

internal actual val platformNotificationModule = module {
    singleOf(::IosDeviceTokenManager) { bind<DeviceTokenManager>() }
    singleOf(::IosNotificationManager) { bind<NotificationManager>() }
}
