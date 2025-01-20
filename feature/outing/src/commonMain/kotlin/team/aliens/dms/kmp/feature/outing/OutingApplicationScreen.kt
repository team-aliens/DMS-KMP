package team.aliens.dms.kmp.feature.outing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import team.aliens.dms.kmp.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme

@Composable
internal fun OutingApplication(
    modifier: Modifier = Modifier
) {

}

@Composable
private fun OutingApplicationScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DmsTheme.colors.background),
        verticalArrangement = Arrangement.Center,
    ) {
        DmsTopAppBar(
            title = "외출 신청",
            onBackPressed = { },
        )
    }
}
