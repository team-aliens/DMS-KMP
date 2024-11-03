package team.aliens.dms.kmp.core.designsystem.animation

import platform.UIKit.UIViewController

object LottieControllerProvider {
    lateinit var lottieAnimationController: (String) -> UIViewController
}
