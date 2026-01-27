package team.aliens.dms.kmp.feature.setting.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.domain.usecase.auth.SignOutUseCase
import team.aliens.dms.kmp.core.domain.usecase.notification.FetchNotificationTopicStatusUseCase
import team.aliens.dms.kmp.core.domain.usecase.student.WithdrawUseCase
import team.aliens.dms.kmp.core.model.notification.NotificationTopicStatusModel

class SettingViewModel(
    private val signOutUseCase: SignOutUseCase,
    private val withdrawUseCase: WithdrawUseCase,
    private val fetchNotificationTopicStatusUseCase: FetchNotificationTopicStatusUseCase,
) : BaseViewModel<SettingState, SettingSideEffect>(SettingState()) {

    init {
//        fetchDeviceToken()
    }

//    private fun fetchDeviceToken() {
//        viewModelScope.launch {
//            deviceDataStoreDataSource.loadDeviceToken().onSuccess { deviceToken ->
//                setState { settingState ->
//                    settingState.copy(
//                        deviceToken = deviceToken,
//                    )
//                }
//                fetchNotificationStatus()
//            }
//        }
//    }

    internal fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            signOutUseCase().onSuccess {
                postSideEffect(SettingSideEffect.SignOutSuccess)
            }
        }
    }

    internal fun withdraw() {
        viewModelScope.launch(Dispatchers.IO) {
            withdrawUseCase().onSuccess {
                postSideEffect(SettingSideEffect.WithdrawSuccess)
            }
        }
    }

    private fun fetchNotificationStatus() {
        viewModelScope.launch {
            val deviceToken = state.value.deviceToken ?: return@launch
            fetchNotificationTopicStatusUseCase(deviceToken).onSuccess { statuses ->
                val isOnNotification = statuses.topicGroups.any { status ->
                    status.topicSubscriptions.any { subscription ->
                        subscription.isSubscribed
                    }
                }
                setState { state.value.copy(notificationTopicStatus = statuses, isOnNotification = isOnNotification) }
            }.onFailure {
                setState { state.value.copy(isOnNotification = false) }
                postSideEffect(SettingSideEffect.CannotFetchNotificationStatus)
            }
        }
    }

    internal fun updateNotificationStatus(isOnNotification: Boolean) {
        setState {
            state.value.copy(isOnNotification = !isOnNotification)
        }
        if (isOnNotification) /* 구독 취소 */ setNotificationStatus() else return // TODO 구독 업데이트 (false -> true)
    }

    private fun setNotificationStatus() {
    }
}

data class SettingState(
    val deviceToken: String? = null,
    val isOnNotification: Boolean = true,
    val notificationTopicStatus: NotificationTopicStatusModel = NotificationTopicStatusModel(),
)

sealed class SettingSideEffect {
    object CannotFetchNotificationStatus : SettingSideEffect()
    object SignOutSuccess : SettingSideEffect()
    object WithdrawSuccess : SettingSideEffect()
}
