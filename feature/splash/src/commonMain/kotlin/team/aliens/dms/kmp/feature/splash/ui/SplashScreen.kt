package team.aliens.dms.kmp.feature.splash.ui

import KottieAnimation
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dmskmp.feature.splash.generated.resources.Res
import kotlinx.coroutines.delay
import kottieComposition.KottieCompositionSpec
import kottieComposition.animateKottieCompositionAsState
import kottieComposition.rememberKottieComposition
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.koinInject
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.feature.splash.viewmodel.SplashViewModel

@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun Splash(
    navigateToLogin: () -> Unit,
) {
    val viewModel: SplashViewModel = koinInject()
    var animation by remember { mutableStateOf("") }

    val file = if (isSystemInDarkTheme()) {
        "files/splash_dark.json"
    } else {
        "files/splash_light.json"
    }

    LaunchedEffect(Unit) {
        animation = Res.readBytes(file).decodeToString()
    }

    // TODO: 임시 이동 로직
    LaunchedEffect(Unit) {
        delay(1200)
        navigateToLogin()
    }

    SplashScreen(
        animation = animation,
        navigateToLogin = navigateToLogin,
    )
}

@Composable
private fun SplashScreen(
    animation: String,
    navigateToLogin: () -> Unit,
) {
    val composition = rememberKottieComposition(
        spec = KottieCompositionSpec.File(animation),
    )
    val animationState by animateKottieCompositionAsState(
        composition = composition,
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colors.background),
        contentAlignment = Alignment.Center,
    ) {
        KottieAnimation(
            modifier = Modifier
                .padding(horizontal = 120.dp),
            composition = composition,
            progress = { animationState.progress },
        )
    }
}
