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
    surface = DmsColor.Light.gray50,
    onSurface = DmsColor.Light.gray100,
    surfaceVariant = DmsColor.Light.gray200,
    onSurfaceVariant = DmsColor.Light.gray300,
    inverseSurface = DmsColor.Light.gray400,
    inverseOnSurface = DmsColor.Light.gray500,
    tertiaryContainer = DmsColor.Light.gray600,
    onTertiaryContainer = DmsColor.Light.gray700,
    surfaceBright = DmsColor.Light.gray800,
    error = DmsColor.Light.error50,
    onError = DmsColor.Light.error100,
    errorContainer = DmsColor.Light.error200,
    onErrorContainer = DmsColor.Light.error300,
    outline = DmsColor.Light.error400,
    background = DmsColor.Light.background,
    onBackground = DmsColor.Light.black,
    surfaceTint = DmsColor.Light.white,
    scrim = DmsColor.Light.title,
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
    surface = DmsColor.Dark.gray50,
    onSurface = DmsColor.Dark.gray100,
    surfaceVariant = DmsColor.Dark.gray200,
    onSurfaceVariant = DmsColor.Dark.gray300,
    inverseSurface = DmsColor.Dark.gray400,
    inverseOnSurface = DmsColor.Dark.gray500,
    tertiaryContainer = DmsColor.Dark.gray600,
    onTertiaryContainer = DmsColor.Dark.gray700,
    surfaceBright = DmsColor.Dark.gray800,
    error = DmsColor.Dark.error50,
    onError = DmsColor.Dark.error100,
    errorContainer = DmsColor.Dark.error200,
    onErrorContainer = DmsColor.Dark.error300,
    outline = DmsColor.Dark.error400,
    background = DmsColor.Dark.background,
    onBackground = DmsColor.Dark.black,
    surfaceTint = DmsColor.Dark.white,
    scrim = DmsColor.Dark.title,
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
