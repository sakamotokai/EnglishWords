package com.example.englishwords.ui.theme.secondTheme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


data class SecondTypography(
    val h1: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    val subtitle: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    val body: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    val button: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    val caption: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
)

internal val SecondLocalTypography = staticCompositionLocalOf { SecondTypography() }

data class SecondDimensions(
    val paddingSmall: Dp = 4.dp,
    val paddingMedium: Dp = 8.dp,
    val paddingLarge: Dp = 24.dp
)

internal val SecondLocalDimensions = staticCompositionLocalOf { SecondDimensions() }