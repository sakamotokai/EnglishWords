package com.example.englishwords.ui.theme.secondTheme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember

@Composable
fun SecondTheme(
    colors: SecondColor = SecondTheme.colors,
    typography: SecondTypography = SecondTheme.secondTypography,
    dimensions: SecondDimensions = SecondTheme.secondDimensions,
    content: @Composable () -> Unit
) {
    val rememberedColors = remember { colors.copy() }.apply { updateColorsFrom(colors) }
    CompositionLocalProvider(
        SecondLocalColors provides rememberedColors,
        SecondLocalDimensions provides dimensions,
        SecondLocalTypography provides typography
    ) {
        content()
    }
}

object SecondTheme {
    val colors: SecondColor
        @Composable
        @ReadOnlyComposable
        get() = SecondLocalColors.current

    val secondTypography: SecondTypography
        @Composable
        @ReadOnlyComposable
        get() = SecondLocalTypography.current

    val secondDimensions: SecondDimensions
        @Composable
        @ReadOnlyComposable
        get() = SecondLocalDimensions.current
}