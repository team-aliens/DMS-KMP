package team.aliens.dms.kmp.core.common.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object PaddingDefaults {
    val Medium = 12.dp
    val Large = 24.dp
}

fun Modifier.horizontalPadding(
    value: Dp = PaddingDefaults.Large,
): Modifier = padding(horizontal = value)

fun Modifier.topPadding(
    value: Dp = PaddingDefaults.Medium,
): Modifier = padding(top = value)

fun Modifier.bottomPadding(
    value: Dp = PaddingDefaults.Medium,
): Modifier = padding(bottom = value)
