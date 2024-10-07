package team.aliens.dms.kmp.feature.splash.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dmskmp.feature.splash.generated.resources.Res
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import team.aliens.dms.kmp.core.designsystem.foundation.DmsIcon
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme

@Composable
internal fun Splash(
    navigateToLogin: () -> Unit,
) {
    SplashScreen()
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colors.background),
        contentAlignment = Alignment.Center,
    ) {
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

        Image(
            painter = rememberLottiePainter(
                composition = composition,
            ),
            contentDescription = "splash",
        )
    }
}
