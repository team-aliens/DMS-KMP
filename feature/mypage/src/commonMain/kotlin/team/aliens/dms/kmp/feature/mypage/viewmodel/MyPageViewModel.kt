package team.aliens.dms.kmp.feature.mypage.viewmodel

import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.domain.usecase.student.GetMyPageUseCase
import team.aliens.dms.kmp.core.model.mypage.MyPageModel

internal class MyPageViewModel(
    private val getMyPageUseCase: GetMyPageUseCase,
) : BaseViewModel<MyPageState, MyPageSideEffect>(MyPageState()) {

    init {
        getMyPage()
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
}

internal data class MyPageState(
    val myPage: MyPageModel = MyPageModel(),
)

sealed interface MyPageSideEffect
