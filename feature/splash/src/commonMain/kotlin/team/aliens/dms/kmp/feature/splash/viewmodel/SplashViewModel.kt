package team.aliens.dms.kmp.feature.splash.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.common.utils.now
import team.aliens.dms.kmp.core.domain.usecase.auth.GetTokenUseCase
import team.aliens.dms.kmp.core.domain.usecase.auth.ReissueTokenUseCase
import team.aliens.dms.kmp.core.model.auth.TokenModel

internal class SplashViewModel(
    private val getTokenUseCase: GetTokenUseCase,
    private val reissueTokenUseCase: ReissueTokenUseCase,
) : BaseViewModel<SplashState, SplashSideEffect>(SplashState()) {

    init {
        viewModelScope.launch {
            delay(1200)
            getToken()
        }
    }

    private fun getToken() {
        viewModelScope.launch(Dispatchers.IO) {
            getTokenUseCase()
                .onSuccess { token ->
                    if (token != null) {
                        setState { state.value.copy(token = token) }
                        checkRefreshTokenExpired()
                    } else {
                        postSideEffect(SplashSideEffect.MoveToLogin)
                    }
                }.onFailure {
                    postSideEffect(SplashSideEffect.MoveToLogin)
                }
        }
    }

    private fun checkRefreshTokenExpired() {
        if (now > state.value.token.refreshToken.expiration) {
            postSideEffect(SplashSideEffect.MoveToLogin)
        } else {
            reissueToken()
        }
    }

    private fun reissueToken() {
        viewModelScope.launch(Dispatchers.IO) {
            reissueTokenUseCase(
                refreshToken = state.value.token.refreshToken.value,
            ).onSuccess {
                postSideEffect(SplashSideEffect.MoveToMain)
            }.onFailure {
                postSideEffect(SplashSideEffect.MoveToLogin)
            }
        }
    }
}

internal data class SplashState(
    val token: TokenModel = TokenModel(),
)

internal sealed interface SplashSideEffect {
    data object MoveToLogin : SplashSideEffect
    data object MoveToMain : SplashSideEffect
}
