package team.aliens.dms.kmp.feature.splash.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import org.koin.compose.koinInject
import team.aliens.dms.kmp.core.designsystem.animation.An
import team.aliens.dms.kmp.core.designsystem.animation.DmsAnimation
import team.aliens.dms.kmp.feature.splash.viewmodel.SplashViewModel

@Composable
internal fun Splash(
    navigateToLogin: () -> Unit,
) {
    val viewModel: SplashViewModel = koinInject()

    val fileName = if (isSystemInDarkTheme()) {
        DmsAnimation.SplashDark
    } else {
        DmsAnimation.SplashLight
    }

    // TODO: 임시 이동 로직
    LaunchedEffect(Unit) {
        delay(1200)
        //navigateToLogin()
    }

    SplashScreen(
        animationName = fileName,
        navigateToLogin = navigateToLogin,
    )
}

@Composable
private fun SplashScreen(
    animationName: String,
    navigateToLogin: () -> Unit,
) {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(DmsTheme.colors.background),
//        contentAlignment = Alignment.Center,
//    ) {
//        DmsLottieAnimation(
//            modifier = Modifier
//                .fillMaxSize()
//                .horizontalPadding(100.dp)
//                .background(DmsTheme.colors.background),
//            animationFileName = animationName
//        )
//    }
    An()
}
