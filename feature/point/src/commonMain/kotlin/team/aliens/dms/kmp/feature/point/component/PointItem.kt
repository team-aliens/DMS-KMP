package team.aliens.dms.kmp.feature.point.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import dmskmp.core.design_system.generated.resources.Res
import dmskmp.core.design_system.generated.resources.ic_minus
import dmskmp.core.design_system.generated.resources.ic_plus
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import org.jetbrains.compose.resources.painterResource
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText
import team.aliens.dms.kmp.core.model.type.PointType

@OptIn(FormatStringsInDatetimeFormats::class)
@Composable
internal fun PointItem(
    modifier: Modifier = Modifier,
    name: String,
    point: Int,
    date: LocalDate,
    pointType: PointType,
) {
    val formatter = LocalDate.Format {
        byUnicodePattern("yyyy.MM.dd")
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val (backgroundColor, icon) = if (pointType == PointType.BONUS) {
            DmsTheme.colors.onPrimary to Res.drawable.ic_plus
        } else {
            DmsTheme.colors.onError to Res.drawable.ic_minus
        }
        val (pointText, endText, pointColor) = if (pointType == PointType.BONUS) {
            Triple("상점", "!", DmsTheme.colors.onPrimaryContainer)
        } else {
            Triple("벌점", ".", DmsTheme.colors.onErrorContainer)
        }
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(backgroundColor)
                .padding(6.dp),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(resource = icon),
                contentDescription = null,
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            DmsText(
                text = "[$name]",
                style = DmsTypography.labelB,
                color = DmsTheme.colors.inverseOnSurface,
            )
            Row {
                DmsText(
                    text = pointText,
                    style = DmsTypography.labelM,
                    color = DmsTheme.colors.inverseOnSurface,
                )
                DmsText(
                    text = " ${point}점",
                    style = DmsTypography.labelB,
                    color = pointColor,
                )
                DmsText(
                    text = "을 받았어요${endText}",
                    style = DmsTypography.labelM,
                    color = DmsTheme.colors.inverseOnSurface,
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        DmsText(
            text = formatter.format(date),
            style = DmsTypography.labelM,
            color = DmsTheme.colors.inverseSurface,
        )
    }
}
