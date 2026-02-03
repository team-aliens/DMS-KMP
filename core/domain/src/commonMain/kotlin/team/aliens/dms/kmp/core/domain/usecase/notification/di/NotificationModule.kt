package team.aliens.dms.kmp.core.domain.usecase.notification.di

import org.koin.core.module.dsl.singleOf
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

internal val notificationModule = module {
    singleOf(::GetDeviceTokenUseCase)
    singleOf(::RegisterFcmDeviceTokenUseCase)
    singleOf(::CancelFcmDeviceTokenRegistrationUseCase)
    singleOf(::SubscribeNotificationTopicUseCase)
    singleOf(::UnsubscribeNotificationTopicUseCase)
    singleOf(::BatchUpdateNotificationTopicUseCase)
    singleOf(::FetchNotificationTopicStatusUseCase)
    singleOf(::FetchNotificationsUseCase)
    singleOf(::UpdateNotificationReadStatusUseCase)
}
