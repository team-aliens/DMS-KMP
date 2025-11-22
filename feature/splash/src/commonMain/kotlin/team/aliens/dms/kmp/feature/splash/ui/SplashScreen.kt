package team.aliens.dms.kmp.feature.splash.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dmskmp.feature.splash.generated.resources.Res
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import org.koin.compose.viewmodel.koinViewModel
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.feature.splash.viewmodel.SplashSideEffect
import team.aliens.dms.kmp.feature.splash.viewmodel.SplashViewModel

@Composable
internal fun Splash(
    navigateToOnboarding: () -> Unit,
    navigateToLogin: () -> Unit,
    navigateToMain: () -> Unit,
) {
    val viewModel: SplashViewModel = koinViewModel()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect {
            when (it) {
                SplashSideEffect.NavigateToOnBoarding -> navigateToOnboarding()
                SplashSideEffect.NavigateToMain -> navigateToMain()
                SplashSideEffect.NavigateToLogin -> navigateToLogin()
            }
        }
    }

    SplashScreen()
}

@Composable
private fun SplashScreen() {
    val file = if (isSystemInDarkTheme()) {
        "files/splash_dark.json"
    } else {
        "files/splash_light.json"
    }
    val composition by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes(file).decodeToString(),
        )
    }
    val progress by animateLottieCompositionAsState(composition)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colors.background)
            .statusBarsPadding()
            .navigationBarsPadding(),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            modifier = Modifier.padding(horizontal = 120.dp),
            painter = rememberLottiePainter(
                composition = composition,
                progress = { progress },
            ),
            contentDescription = null,
        )
    }
}
