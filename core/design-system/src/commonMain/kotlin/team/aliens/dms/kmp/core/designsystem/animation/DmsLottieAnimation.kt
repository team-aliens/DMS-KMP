package team.aliens.dms.kmp.core.designsystem.animation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun DmsLottieAnimation(
    modifier: Modifier = Modifier,
    animationFileName: String,
)
