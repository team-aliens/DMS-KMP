package team.aliens.dms.kmp.core.designsystem.foundation

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dmskmp.core.design_system.generated.resources.Res
import dmskmp.core.design_system.generated.resources.pretendard_bold
import dmskmp.core.design_system.generated.resources.pretendard_medium
import dmskmp.core.design_system.generated.resources.pretendard_regular
import dmskmp.core.design_system.generated.resources.pretendard_semi_bold
import org.jetbrains.compose.resources.Font

@Composable
private fun pretendardFamily() = FontFamily(
    Font(
        resource = Res.font.pretendard_regular,
        weight = FontWeight.Thin,
    ),
    Font(
        resource = Res.font.pretendard_medium,
        weight = FontWeight.Medium,
    ),
    Font(
        resource = Res.font.pretendard_semi_bold,
        weight = FontWeight.SemiBold,
    ),
    Font(
        resource = Res.font.pretendard_bold,
        weight = FontWeight.Bold,
    ),
)

object DmsTypography {
    val Header1
        @Composable get() = TextStyle(
            fontFamily = pretendardFamily(),
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 50.sp,
            color = DmsTheme.colors.onBackground,
        )

    val Header2
        @Composable get() = TextStyle(
            fontFamily = pretendardFamily(),
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 42.sp,
            color = DmsTheme.colors.onBackground,
        )

    val Header3
        @Composable get() = TextStyle(
            fontFamily = pretendardFamily(),
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 34.sp,
            color = DmsTheme.colors.onBackground,
        )

    val Title1
        @Composable get() = TextStyle(
            fontFamily = pretendardFamily(),
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 34.sp,
            color = DmsTheme.colors.onBackground,
        )

    val Title2
        @Composable get() = TextStyle(
            fontFamily = pretendardFamily(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 32.sp,
            color = DmsTheme.colors.onBackground,
        )

    val Title3
        @Composable get() = TextStyle(
            fontFamily = pretendardFamily(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 28.sp,
            color = DmsTheme.colors.onBackground,
        )

    val Body1
        @Composable get() = TextStyle(
            fontFamily = pretendardFamily(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 28.sp,
            color = DmsTheme.colors.onBackground,
        )

    val Body2
        @Composable get() = TextStyle(
            fontFamily = pretendardFamily(),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 26.sp,
            color = DmsTheme.colors.onBackground,
        )

    val Body3
        @Composable get() = TextStyle(
            fontFamily = pretendardFamily(),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 22.sp,
            color = DmsTheme.colors.onBackground,
        )

    val Caption
        @Composable get() = TextStyle(
            fontFamily = pretendardFamily(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Thin,
            lineHeight = 22.sp,
            color = DmsTheme.colors.onBackground,
        )

    val Label
        @Composable get() = TextStyle(
            fontFamily = pretendardFamily(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 22.sp,
            color = DmsTheme.colors.onBackground,
        )

    val Button0
        @Composable get() = TextStyle(
            fontFamily = pretendardFamily(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Thin,
            lineHeight = 28.sp,
            color = DmsTheme.colors.onBackground,
        )

    val Button1
        @Composable get() = TextStyle(
            fontFamily = pretendardFamily(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 16.sp,
            color = DmsTheme.colors.onBackground,
        )

    val Button2
        @Composable get() = TextStyle(
            fontFamily = pretendardFamily(),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 16.sp,
            color = DmsTheme.colors.onBackground,
        )

    val Button3
        @Composable get() = TextStyle(
            fontFamily = pretendardFamily(),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = DmsTheme.colors.onBackground,
        )

    val Button4
        @Composable get() = TextStyle(
            fontFamily = pretendardFamily(),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 16.sp,
            color = DmsTheme.colors.onBackground,
        )
}
