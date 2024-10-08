package team.aliens.dms.kmp.feature.splash.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.domain.usecase.auth.GetTokenUseCase

internal class SplashViewModel(
    private val getTokenUseCase: GetTokenUseCase,
) : BaseViewModel<SplashState, SplashSideEffect>(SplashState.getInitialState()) {

    internal fun getToken() {
        viewModelScope.launch(Dispatchers.IO) {
            getTokenUseCase().onSuccess { token ->
                setState {
                    state.value.copy(
                        accessToken = token.accessToken.value,
                        refreshToken = token.refreshToken.value,
                        refreshTokenExpired = token.refreshToken.expiration,
                    )
                }
                checkRefreshTokenExpired()
            }.onFailure {
                when (it) {
                    is NullPointerException -> {
                        postSideEffect(SplashSideEffect.MoveToLogin)
                    }
                }
            }
        }
    }

    private fun checkRefreshTokenExpired() {
        if (Clock.System.now() > state.value.refreshTokenExpired) {
            postSideEffect(SplashSideEffect.MoveToLogin)
        } else {
            reissueToken()
        }
    }

    private fun reissueToken() {
        // TODO: reissue 구현 (성공 시 main으로 이동)
    }
}

internal data class SplashState(
    val accessToken: String,
    val refreshToken: String,
    val refreshTokenExpired: Instant,
) {
    companion object {
        fun getInitialState() = SplashState(
            accessToken = "",
            refreshToken = "",
            refreshTokenExpired = Clock.System.now(),
        )
    }
}

internal sealed interface SplashSideEffect {
    data object MoveToLogin : SplashSideEffect
    data object MoveToMain : SplashSideEffect
}
