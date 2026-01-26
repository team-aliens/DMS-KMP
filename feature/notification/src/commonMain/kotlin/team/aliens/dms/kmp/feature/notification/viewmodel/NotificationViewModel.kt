package team.aliens.dms.kmp.feature.notification.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.domain.usecase.notification.FetchNotificationsUseCase
import team.aliens.dms.kmp.core.domain.usecase.notification.UpdateNotificationReadStatusUseCase
import team.aliens.dms.kmp.core.model.notification.NotificationsModel
import team.aliens.dms.kmp.core.model.type.NotificationGroupType
import team.aliens.dms.kmp.core.model.type.NotificationType

internal class NotificationViewModel(
    private val fetchNotificationsUseCase: FetchNotificationsUseCase,
    private val updateNotificationReadStatusUseCase: UpdateNotificationReadStatusUseCase,
) : BaseViewModel<NotificationState, NotificationSideEffect>(NotificationState()) {

    init {
        fetchNotifications()
    }

    private fun fetchNotifications() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchNotificationsUseCase().onSuccess { notifications ->
                val notification = notifications.notifications.filter { it.topic == NotificationType.POINT }
                val notices = notifications.notifications.filter { it.topic == NotificationType.NOTICE }

                setState { state.value.copy(
                    notifications = notification,
                    notices = notices
                ) }
            }.onFailure {
                postSideEffect(NotificationSideEffect.FailFetchNotification)
            }
        }
    }

    internal fun updateNotificationReadStatus(notificationId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            updateNotificationReadStatusUseCase(notificationId).fold(
                onSuccess = {
                    fetchNotifications()
                },
                onFailure = {
                    postSideEffect(NotificationSideEffect.FailUpdateNotification)
                },
            )
        }
    }

}

internal data class NotificationState(
    val isRecent: Boolean = false,
    val notices: List<NotificationsModel.NotificationModel> = emptyList(),
    val notifications: List<NotificationsModel.NotificationModel> = emptyList(),
)

internal sealed interface NotificationSideEffect {
    object FailFetchNotification : NotificationSideEffect
    object FailUpdateNotification : NotificationSideEffect
}
