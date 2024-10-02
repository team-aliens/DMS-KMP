package team.aliens.dms.kmp.core.designsystem.foundation

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dmskmp.core.design_system.generated.resources.Res
import dmskmp.core.design_system.generated.resources.wanted_sans_medium
import dmskmp.core.design_system.generated.resources.wanted_sans_semi_bold
import org.jetbrains.compose.resources.Font

@Composable
private fun wantedSansFamily() = FontFamily(
    Font(
        resource = Res.font.wanted_sans_medium,
        weight = FontWeight.Medium,
    ),
    Font(
        resource = Res.font.wanted_sans_semi_bold,
        weight = FontWeight.SemiBold,
    ),
)

object DmsTypography {
    val Title3SemiBold
        @Composable get() = TextStyle(
            fontFamily = wantedSansFamily(),
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 24.sp,
        )

    val Title3Medium
        @Composable get() = TextStyle(
            fontFamily = wantedSansFamily(),
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 24.sp,
        )

    val Title2SemiBold
        @Composable get() = TextStyle(
            fontFamily = wantedSansFamily(),
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 22.sp,
        )

    val Title2Medium
        @Composable get() = TextStyle(
            fontFamily = wantedSansFamily(),
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 22.sp,
        )

    val Title1SemiBold
        @Composable get() = TextStyle(
            fontFamily = wantedSansFamily(),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 20.sp,
        )

    val Title1Medium
        @Composable get() = TextStyle(
            fontFamily = wantedSansFamily(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 20.sp,
        )

    val HeadlineSemiBold
        @Composable get() = TextStyle(
            fontFamily = wantedSansFamily(),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 18.sp,
        )

    val HeadlineMedium
        @Composable get() = TextStyle(
            fontFamily = wantedSansFamily(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 18.sp,
        )

    val SubtitleSemiBold
        @Composable get() = TextStyle(
            fontFamily = wantedSansFamily(),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 24.sp,
        )

    val SubtitleMedium
        @Composable get() = TextStyle(
            fontFamily = wantedSansFamily(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 24.sp,
        )

    val Body1SemiBold
        @Composable get() = TextStyle(
            fontFamily = wantedSansFamily(),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 22.sp,
        )

    val Body1Medium
        @Composable get() = TextStyle(
            fontFamily = wantedSansFamily(),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 22.sp,
        )

    val Body2SemiBold
        @Composable get() = TextStyle(
            fontFamily = wantedSansFamily(),
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 20.sp,
        )

    val Body2Medium
        @Composable get() = TextStyle(
            fontFamily = wantedSansFamily(),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 20.sp,
        )

    val Caption1SemiBold
        @Composable get() = TextStyle(
            fontFamily = wantedSansFamily(),
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 12.sp,
        )

    val Caption1Medium
        @Composable get() = TextStyle(
            fontFamily = wantedSansFamily(),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 12.sp,
        )
}
