package team.aliens.dms.kmp.feature.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dmskmp.feature.home.generated.resources.Res
import dmskmp.feature.home.generated.resources.img_food
import org.jetbrains.compose.resources.painterResource
import team.aliens.dms.kmp.core.designsystem.button.ButtonColor
import team.aliens.dms.kmp.core.designsystem.button.ButtonType
import team.aliens.dms.kmp.core.designsystem.button.DmsButton
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText

@Composable
internal fun MealContent(
    modifier: Modifier = Modifier,
    onMealClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = DmsTheme.colors.surfaceTint, shape = RoundedCornerShape(32.dp))
            .padding(24.dp),
    ) {
        DmsText(
            text = "급식",
            style = DmsTypography.BodyB,
            color = DmsTheme.colors.inverseSurface,
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier.size(230.dp),
                painter = painterResource(Res.drawable.img_food),
                contentDescription = null,
            )
            DmsButton(
                text = "오늘의 급식 확인하기",
                buttonType = ButtonType.Contained,
                buttonColor = ButtonColor.Primary,
                onClick = onMealClick,
            )
        }
    }
}
