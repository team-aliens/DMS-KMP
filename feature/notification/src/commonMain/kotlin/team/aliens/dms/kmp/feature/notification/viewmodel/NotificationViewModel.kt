package team.aliens.dms.kmp.feature.notification.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.domain.usecase.notice.GetNoticesUseCase
import team.aliens.dms.kmp.core.domain.usecase.notification.FetchNotificationsUseCase
import team.aliens.dms.kmp.core.domain.usecase.notification.UpdateNotificationReadStatusUseCase
import team.aliens.dms.kmp.core.model.notice.NoticeModel
import team.aliens.dms.kmp.core.model.notification.NotificationsModel
import team.aliens.dms.kmp.core.model.type.NotificationType
import team.aliens.dms.kmp.core.model.type.OrderType

internal class NotificationViewModel(
    private val getNoticesUseCase: GetNoticesUseCase,
    private val fetchNotificationsUseCase: FetchNotificationsUseCase,
    private val updateNotificationReadStatusUseCase: UpdateNotificationReadStatusUseCase,
) : BaseViewModel<NotificationState, NotificationSideEffect>(NotificationState()) {

    init {
        fetchNotifications()
        getNotices()
    }

    private fun fetchNotifications() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchNotificationsUseCase().onSuccess { notifications ->
                val notification = notifications.notifications.filter { it.topic != NotificationType.NOTICE }

                setState { state.value.copy(
                    notifications = notification,
                ) }
            }.onFailure {
                postSideEffect(NotificationSideEffect.FailFetchNotification)
            }
        }
    }

    private fun getNotices() {
        viewModelScope.launch(Dispatchers.IO) {
            getNoticesUseCase(orderType = OrderType.NEW)
                .onSuccess { notices ->
                    setState { state.value.copy(notices = notices) }
                }
                .onFailure {
                    postSideEffect(NotificationSideEffect.FailFetchNotice)
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
    val notices: List<NoticeModel> = emptyList(),
    val notifications: List<NotificationsModel.NotificationModel> = emptyList(),
)

internal sealed interface NotificationSideEffect {
    object FailFetchNotification : NotificationSideEffect
    object FailFetchNotice : NotificationSideEffect
    object FailUpdateNotification : NotificationSideEffect
}
