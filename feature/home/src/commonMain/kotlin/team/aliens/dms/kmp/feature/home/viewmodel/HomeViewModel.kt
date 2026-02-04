package team.aliens.dms.kmp.feature.home.viewmodel

import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.domain.usecase.notice.GetLatestNoticeUseCase
import team.aliens.dms.kmp.core.domain.usecase.student.GetMyPageUseCase
import team.aliens.dms.kmp.core.model.mypage.MyPageModel
import team.aliens.dms.kmp.core.model.notice.LatestNoticeModel

internal class HomeViewModel(
    private val getMyPageUseCase: GetMyPageUseCase,
    private val getLatestUseCase: GetLatestNoticeUseCase,
) : BaseViewModel<HomeState, HomeSideEffect>(HomeState()) {

    init {
        getMyPage()
        getLatestNotice()
    }

    private fun getMyPage() {
        viewModelScope.launch(Dispatchers.IO) {
            getMyPageUseCase().onSuccess {
                setState { state.value.copy(myPage = it) }
            }.onFailure {
                Logger.a(it) { it.message.toString() }
            }
        }
    }

    internal fun showOutingPassDialog() {
        postSideEffect(HomeSideEffect.ShowOutingPassDialog)
    }

    internal fun getLatestNotice() {
        viewModelScope.launch(Dispatchers.IO) {
            getLatestUseCase().onSuccess {
                setState { state.value.copy(latestNotice = it) }
            }
        }
    }
}

internal data class HomeState(
    val newNoticesExist: Boolean = false,
    val myPage: MyPageModel = MyPageModel(),
    val latestNotice: LatestNoticeModel = LatestNoticeModel()
)

internal sealed interface HomeSideEffect {
    data object ShowOutingPassDialog : HomeSideEffect
    data object NavigateToNotification : HomeSideEffect
}
