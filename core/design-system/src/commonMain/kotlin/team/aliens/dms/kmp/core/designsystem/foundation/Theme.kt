package team.aliens.dms.kmp.core.designsystem.foundation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

private val lightColorScheme = lightColorScheme(
    primary = DmsColor.Light.primary50,
    onPrimary = DmsColor.Light.primary100,
    primaryContainer = DmsColor.Light.primary200,
    onPrimaryContainer = DmsColor.Light.primary300,
    inversePrimary = DmsColor.Light.primary400,
    secondary = DmsColor.Light.primary500,
    onSecondary = DmsColor.Light.primary600,
    secondaryContainer = DmsColor.Light.primary700,
    onSecondaryContainer = DmsColor.Light.primary800,
    tertiary = DmsColor.Light.primary900,
    onTertiary = DmsColor.Light.gray50,
    tertiaryContainer = DmsColor.Light.gray100,
    onTertiaryContainer = DmsColor.Light.gray200,
    surface = DmsColor.Light.gray300,
    onSurface = DmsColor.Light.gray400,
    surfaceVariant = DmsColor.Light.gray500,
    onSurfaceVariant = DmsColor.Light.gray600,
    surfaceTint = DmsColor.Light.gray700,
    inverseSurface = DmsColor.Light.gray800,
    inverseOnSurface = DmsColor.Light.gray900,
    error = DmsColor.Light.error50,
    onError = DmsColor.Light.error100,
    errorContainer = DmsColor.Light.error200,
    onErrorContainer = DmsColor.Light.error300,
    outline = DmsColor.Light.error400,
    outlineVariant = DmsColor.Light.error500,
    scrim = DmsColor.Light.error600,
    surfaceBright = DmsColor.Light.error700,
    surfaceContainer = DmsColor.Light.error800,
    surfaceContainerHigh = DmsColor.Light.error900,
    surfaceContainerHighest = DmsColor.Light.label,
    surfaceContainerLow = DmsColor.Light.color,
    surfaceContainerLowest = DmsColor.Light.symbol,
    surfaceDim = DmsColor.Light.sad,
    background = DmsColor.Light.backgroundPrimary,
    onBackground = DmsColor.Light.backgroundSecondary,
)

private val darkColorScheme = darkColorScheme(
    primary = DmsColor.Dark.primary50,
    onPrimary = DmsColor.Dark.primary100,
    primaryContainer = DmsColor.Dark.primary200,
    onPrimaryContainer = DmsColor.Dark.primary300,
    inversePrimary = DmsColor.Dark.primary400,
    secondary = DmsColor.Dark.primary500,
    onSecondary = DmsColor.Dark.primary600,
    secondaryContainer = DmsColor.Dark.primary700,
    onSecondaryContainer = DmsColor.Dark.primary800,
    tertiary = DmsColor.Dark.primary900,
    onTertiary = DmsColor.Dark.gray50,
    tertiaryContainer = DmsColor.Dark.gray100,
    onTertiaryContainer = DmsColor.Dark.gray200,
    surface = DmsColor.Dark.gray300,
    onSurface = DmsColor.Dark.gray400,
    surfaceVariant = DmsColor.Dark.gray500,
    onSurfaceVariant = DmsColor.Dark.gray600,
    surfaceTint = DmsColor.Dark.gray700,
    inverseSurface = DmsColor.Dark.gray800,
    inverseOnSurface = DmsColor.Dark.gray900,
    error = DmsColor.Dark.error50,
    onError = DmsColor.Dark.error100,
    errorContainer = DmsColor.Dark.error200,
    onErrorContainer = DmsColor.Dark.error300,
    outline = DmsColor.Dark.error400,
    outlineVariant = DmsColor.Dark.error500,
    scrim = DmsColor.Dark.error600,
    surfaceBright = DmsColor.Dark.error700,
    surfaceContainer = DmsColor.Dark.error800,
    surfaceContainerHigh = DmsColor.Dark.error900,
    surfaceContainerHighest = DmsColor.Dark.label,
    surfaceContainerLow = DmsColor.Dark.color,
    surfaceContainerLowest = DmsColor.Dark.symbol,
    surfaceDim = DmsColor.Dark.sad,
    background = DmsColor.Dark.backgroundPrimary,
    onBackground = DmsColor.Dark.backgroundSecondary,
)

val LocalColors = staticCompositionLocalOf { lightColorScheme }

@Composable
fun DmsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) {
        darkColorScheme
    } else {
        lightColorScheme
    }

    CompositionLocalProvider(LocalColors provides colors) {
        content()
    }
}

object DmsTheme {
    val colors
        @Composable get() = LocalColors.current
}
