package team.aliens.dms.kmp.feature.splash.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.domain.usecase.auth.GetTokenUseCase
import team.aliens.dms.kmp.core.domain.usecase.auth.ReissueTokenUseCase
import team.aliens.dms.kmp.core.domain.usecase.onboarding.GetOnboardingCompletedUseCase
import team.aliens.dms.kmp.core.domain.usecase.onboarding.SetOnboardingCompletedUseCase
import team.aliens.dms.kmp.core.model.auth.TokenModel
import team.aliens.dms.kmp.core.util.now
import kotlin.time.Duration.Companion.seconds

internal class SplashViewModel(
    private val getTokenUseCase: GetTokenUseCase,
    private val reissueTokenUseCase: ReissueTokenUseCase,
    private val getOnboardingCompletedUseCase: GetOnboardingCompletedUseCase,
    private val setOnboardingCompletedUseCase: SetOnboardingCompletedUseCase,
) : BaseViewModel<SplashState, SplashSideEffect>(SplashState()) {

    init {
        // TODO: 비동기 처리 필요
        viewModelScope.launch {
            delay(1.2.seconds)
            checkOnboardingCompleted()
        }
    }

    private fun checkOnboardingCompleted() {
        viewModelScope.launch {
            getOnboardingCompletedUseCase()
                .onSuccess { isCompleted ->
                    if (isCompleted) {
                        getToken()
                    } else {
                        setOnboardingCompletedUseCase(isCompleted = true)
                        postSideEffect(SplashSideEffect.NavigateToOnBoarding)
                    }
                }.onFailure {
                    postSideEffect(SplashSideEffect.NavigateToLogin)
                }
        }
    }

    private fun getToken() {
        viewModelScope.launch {
            getTokenUseCase()
                .onSuccess { token ->
                    if (token != null) {
                        setState { state.value.copy(token = token) }
                        checkRefreshTokenExpired()
                    } else {
                        postSideEffect(SplashSideEffect.NavigateToLogin)
                    }
                }.onFailure {
                    postSideEffect(SplashSideEffect.NavigateToLogin)
                }
        }
    }

    private fun checkRefreshTokenExpired() {
        if (now > state.value.token.refreshToken.expiration) {
            postSideEffect(SplashSideEffect.NavigateToLogin)
        } else {
            reissueToken()
        }
    }

    private fun reissueToken() {
        viewModelScope.launch {
            reissueTokenUseCase(
                refreshToken = state.value.token.refreshToken.value,
            ).onSuccess {
                postSideEffect(SplashSideEffect.NavigateToMain)
            }.onFailure {
                postSideEffect(SplashSideEffect.NavigateToLogin)
            }
        }
    }
}

internal data class SplashState(
    val token: TokenModel = TokenModel(),
)

internal sealed interface SplashSideEffect {
    data object NavigateToLogin : SplashSideEffect
    data object NavigateToMain : SplashSideEffect
    data object NavigateToOnBoarding : SplashSideEffect
}
