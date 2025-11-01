package team.aliens.dms.kmp

import androidx.compose.ui.window.ComposeUIViewController
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.ui.DmsApp

fun mainViewController() = ComposeUIViewController {
    DmsTheme {
        DmsApp()
    }
}
