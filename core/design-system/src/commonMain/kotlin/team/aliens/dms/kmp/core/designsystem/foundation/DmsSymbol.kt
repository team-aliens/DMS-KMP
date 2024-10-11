package team.aliens.dms.kmp.core.designsystem.foundation

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource

@Composable
fun DmsSymbol(modifier: Modifier = Modifier) {
    val symbol = if (isSystemInDarkTheme()) {
        DmsIcon.SymbolDark
    } else {
        DmsIcon.SymbolLight
    }

    Image(
        modifier = modifier,
        painter = painterResource(symbol),
        contentDescription = "symbol",
    )
}
