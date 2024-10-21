package team.aliens.dms.kmp.core.designsystem.status

import KottieAnimation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dmskmp.core.design_system.generated.resources.Res
import kottieComposition.KottieCompositionSpec
import kottieComposition.animateKottieCompositionAsState
import kottieComposition.rememberKottieComposition
import org.jetbrains.compose.resources.ExperimentalResourceApi
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ErrorStatus(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
) {
    var animation by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        animation = Res.readBytes("files/error_status.json").decodeToString()
    }

    val composition = rememberKottieComposition(
        spec = KottieCompositionSpec.File(animation),
    )
    val animationState by animateKottieCompositionAsState(
        composition = composition,
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        KottieAnimation(
            modifier = Modifier.size(80.dp),
            composition = composition,
            progress = { animationState.progress },
        )
        Spacer(modifier = Modifier.height(12.dp))
        DmsText(
            text = title,
            style = DmsTypography.HeadlineSemiBold,
        )
        Spacer(modifier = Modifier.height(2.dp))
        description?.let {
            DmsText(
                text = it,
                style = DmsTypography.Body2Medium,
                color = DmsTheme.colors.onSurface,
            )
        }
    }
}
