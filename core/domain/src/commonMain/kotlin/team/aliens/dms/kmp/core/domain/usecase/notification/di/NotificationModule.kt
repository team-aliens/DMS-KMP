package team.aliens.dms.kmp.core.domain.usecase.notification.di

import org.koin.dsl.module
import team.aliens.dms.kmp.core.domain.usecase.notification.BatchUpdateNotificationTopicUseCase
import team.aliens.dms.kmp.core.domain.usecase.notification.CancelFcmDeviceTokenRegistrationUseCase
import team.aliens.dms.kmp.core.domain.usecase.notification.FetchNotificationsUseCase
import team.aliens.dms.kmp.core.domain.usecase.notification.FetchNotificationTopicStatusUseCase
import team.aliens.dms.kmp.core.domain.usecase.notification.GetDeviceTokenUseCase
import team.aliens.dms.kmp.core.domain.usecase.notification.RegisterFcmDeviceTokenUseCase
import team.aliens.dms.kmp.core.domain.usecase.notification.SubscribeNotificationTopicUseCase
import team.aliens.dms.kmp.core.domain.usecase.notification.UnsubscribeNotificationTopicUseCase
import team.aliens.dms.kmp.core.domain.usecase.notification.UpdateNotificationReadStatusUseCase

val notificationModule = module {
    factory { GetDeviceTokenUseCase(get()) }
    factory { RegisterFcmDeviceTokenUseCase(get()) }
    factory { CancelFcmDeviceTokenRegistrationUseCase(get()) }
    factory { SubscribeNotificationTopicUseCase(get()) }
    factory { UnsubscribeNotificationTopicUseCase(get()) }
    factory { BatchUpdateNotificationTopicUseCase(get()) }
    factory { FetchNotificationTopicStatusUseCase(get()) }
    factory { FetchNotificationsUseCase(get()) }
    factory { UpdateNotificationReadStatusUseCase(get()) }
}
