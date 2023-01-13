package com.example.englishwords.ui.theme.ownTheme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color

val baseLightPalette = OwnColors(
    primaryText = Color(0xFF3D454C),
    primaryBackground = Color(0xFFFFFFFF),
    secondaryBackground = Color(0xFFE1E1E1),
    secondaryText = Color(0xCC7A8A99),
    tintColor = Color.Magenta,
    backgroundInBackground = Color(0xFFD0D0D0),
    purple = Color(0xFF9b4ec3),
    error = Color(0xFFFF0032),
    red = Color(0xFFFF0032),
    green = Color(0xFF54B435),
    blue = Color(0xFF5BC0F8),
    black = Color(0xFF191E23)
)

val baseDarkPalette = OwnColors(
    primaryBackground = Color(0xFF23282D),
    primaryText = Color(0xFFF2F4F5),
    secondaryBackground = Color(0xFF191E23),
    secondaryText = Color(0xCC7A8A99),
    tintColor = Color.Magenta,
    backgroundInBackground = Color(0,0,0),
    purple = Color(0xFF452256),
    error = Color(0xFFCD0404),
    red = Color(0xFFCD0404),
    green = Color(0xFF379237),
    blue = Color(0xFF0081C9),
    black = Color(0,0,0)
)

val purpleLightPalette = baseLightPalette.copy(
    tintColor = Color(0xFFAD57D9)
)

val purpleDarkPalette = baseDarkPalette.copy(
    tintColor = Color(0xFFD580FF)
)

val redLightPalette = baseLightPalette.copy(
    tintColor = Color(0xFFFF0032)
)

val redDarkPalette = baseDarkPalette.copy(
    tintColor = Color(0xFFCD0404)
)

val blueLightPalette = baseLightPalette.copy(
    tintColor = Color(0xFF5BC0F8)
)

val blueDarkPalette = baseDarkPalette.copy(
    tintColor = Color(0xFF0081C9)
)

val greenLightPalette = baseLightPalette.copy(
    tintColor = Color(0xFF54B435)
)

val greenDarkPalette = baseDarkPalette.copy(
    tintColor = Color(0xFF379237)
)

val darkLightPalette = baseLightPalette.copy(
    tintColor = Color(0xFF191E23)
)

val darkDarkPalette = baseDarkPalette.copy(
    tintColor = Color(0,0,0)
)