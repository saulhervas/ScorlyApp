package com.saulhervas.scorlyapp.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = VoltageGreen,
    onPrimary = Color(0xFF000000),
    primaryContainer = VoltageGreen,
    onPrimaryContainer = Color(0xFF000000),
    secondary = ElectricBlue,
    onSecondary = Color(0xFF00363F),
    background = DarkBackground,
    surface = DarkSurface,
    surfaceContainer = DarkSurfaceContainer,
    surfaceContainerHigh = DarkSurfaceContainerHigh,
    onSurface = DarkOnSurface,
    onSurfaceVariant = DarkOnSurfaceVariant,
    outline = DarkOutline,
    outlineVariant = Color(0x33FFFFFF)
)

private val LightColorScheme = lightColorScheme(
    primary = VoltageGreenDark,
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = VoltageGreen,
    onPrimaryContainer = Color(0xFF161E00),
    secondary = ElectricBlueDark,
    onSecondary = Color(0xFFFFFFFF),
    background = LightBackground,
    surface = LightSurface,
    surfaceContainer = LightSurfaceContainer,
    surfaceContainerHigh = LightSurfaceContainerHigh,
    onSurface = LightOnSurface,
    onSurfaceVariant = LightOnSurfaceVariant,
    outline = LightOutline,
    outlineVariant = Color(0x1F000000)
)

@Composable
fun ScorlyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
