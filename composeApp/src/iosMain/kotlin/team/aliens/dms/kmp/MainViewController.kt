package team.aliens.dms.kmp

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController
import team.aliens.dms.kmp.core.designsystem.animation.LottieControllerProvider

fun mainViewController(
    lottieUIViewController: (String) -> UIViewController
) = ComposeUIViewController(
    configure = { LottieControllerProvider.lottieAnimationController = lottieUIViewController }
) {
    DmsApp()
}
