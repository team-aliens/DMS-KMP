package team.aliens.dms.kmp.feature.onboarding.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dmskmp.core.design_system.generated.resources.Res
import dmskmp.core.design_system.generated.resources.img_3d_logo
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import team.aliens.dms.kmp.core.designsystem.button.ButtonColor
import team.aliens.dms.kmp.core.designsystem.button.ButtonType
import team.aliens.dms.kmp.core.designsystem.button.DmsButton
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText

@Composable
internal fun CompleteContent(
    modifier: Modifier = Modifier,
    onCompleteClick: () -> Unit,
) {
    var step by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        step = 1
        delay(500)
        step = 2
        delay(500)
        step = 3
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AnimatedVisibility(
            modifier = Modifier.fillMaxHeight(0.6f),
            visible = step >= 1,
            enter = fadeIn(animationSpec = tween(durationMillis = 500)) + scaleIn(
                animationSpec = tween(
                    durationMillis = 600,
                ),
            ),
        ) {
            Image(
                painter = painterResource(Res.drawable.img_3d_logo),
                contentDescription = null,
            )
        }
        AnimatedVisibility(
            visible = step >= 2,
        ) {
            val startColor by rememberInfiniteTransition().animateColor(
                initialValue = Color(
                    0xFFC176D0,
                ),
                targetValue = Color(0xFF62A4FF),
                animationSpec = infiniteRepeatable(animation = tween(durationMillis = 600)),
            )
            val endColor by rememberInfiniteTransition().animateColor(
                initialValue = Color(0xFF62A4FF),
                targetValue = Color(0xFFC176D0),
                animationSpec = infiniteRepeatable(animation = tween(durationMillis = 600)),
            )
            val brush = Brush.linearGradient(
                colors = listOf(startColor, endColor),
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                DmsText(
                    text = "최고의",
                    style = DmsTypography.HeadlineB,
                    color = DmsTheme.colors.onTertiaryContainer,
                )
                DmsText(
                    text = "기숙사 관리 시스템",
                    style = DmsTypography.HeadlineB.copy(brush = brush),
                    color = DmsTheme.colors.onTertiaryContainer,
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        AnimatedVisibility(
            visible = step >= 3,
        ) {
            DmsButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                text = "시작하기",
                buttonType = ButtonType.Contained,
                buttonColor = ButtonColor.Primary,
                onClick = onCompleteClick,
            )
        }
    }
}
