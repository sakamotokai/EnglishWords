package com.example.englishwords.ui.theme.ownTheme

import androidx.compose.ui.graphics.Color
import com.example.englishwords.models.preferencesModels.SettingsSharedPreferences
import com.example.englishwords.screens.settingsScreen.SettingsViewModel
import org.koin.java.KoinJavaComponent.inject

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

interface CustomColorInterface{
    var customPalette:OwnColors
}

class CustomColor(settingsSharedPreferences:SettingsSharedPreferences):CustomColorInterface {
    private val settingsViewModel:SettingsViewModel by inject(SettingsViewModel::class.java)
    override var customPalette: OwnColors = OwnColors(
    primaryText = Color(settingsViewModel.primaryTextDark.value),
    primaryBackground = Color(settingsViewModel.primaryBackgroundDark.value),
    secondaryText= Color(settingsViewModel.secondaryTextDark.value),
    secondaryBackground= Color(settingsViewModel.secondaryBackgroundDark.value),
    tintColor= Color(settingsViewModel.tintColorDark.value),
    backgroundInBackground= Color(settingsViewModel.backgroundInBackgroundDark.value),
    purple= Color(settingsViewModel.purpleDark.value),
    red= Color(settingsViewModel.redDark.value),
    error= Color(settingsViewModel.errorDark.value),
    green= Color(settingsViewModel.greenDark.value),
    blue= Color(settingsViewModel.blueDark.value),
    black= Color(settingsViewModel.blackDark.value)
    )
}

enum class CustomColorName{
    CustomThemePrimaryText,CustomThemePrimaryBackground,CustomThemeSecondaryText,CustomThemeSecondaryBackground,
    CustomThemeTintColor,CustomThemeBackgroundInBackground,CustomThemePurple,CustomThemeRed,CustomThemeError,
    CustomThemeGreen,CustomThemeBlue,CustomThemeBlack
}

//val customLightPalette = CustomColor().customPalette

val customDarkPalette = baseDarkPalette