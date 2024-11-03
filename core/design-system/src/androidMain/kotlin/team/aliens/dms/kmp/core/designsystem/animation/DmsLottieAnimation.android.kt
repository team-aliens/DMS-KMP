package team.aliens.dms.kmp.core.designsystem.animation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import dmskmp.core.design_system.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
actual fun DmsLottieAnimation(
    modifier: Modifier,
    animationFileName: String,
) {
    var animation by remember { mutableStateOf("") }
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.JsonString(animation))

    LaunchedEffect(Unit) {
        animation = Res.readBytes("files/$animationFileName.json").decodeToString()
    }

    LottieAnimation(
        composition = composition,
        modifier = modifier,
    )
}
