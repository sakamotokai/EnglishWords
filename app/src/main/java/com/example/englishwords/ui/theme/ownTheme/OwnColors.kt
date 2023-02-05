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
    tintColor = Color(0xFF9b4ec3),
    backgroundInBackground = Color(0xFFD0D0D0),
    purple = Color(0xFF9b4ec3),
    error = Color(0xFFFF0032),
    red = Color(0xFFFF0032),
    green = Color(0xFF54B435),
    blue = Color(0xFF5BC0F8),
    black = Color(0xFF191E23),
    definitionCard = Color(0xFFFF0032),
    exampleCard = Color(0xFF5BC0F8),
    savedCard = Color(0xFF5BC0F8),
    button = Color(0xFF9b4ec3)
)

val baseDarkPalette = OwnColors(
    primaryBackground = Color(0xFF23282D),
    primaryText = Color(0xFFF2F4F5),
    secondaryBackground = Color(0xFF191E23),
    secondaryText = Color(0xCC7A8A99),
    tintColor = Color(0xFF452256),
    backgroundInBackground = Color(0, 0, 0),
    purple = Color(0xFF452256),
    error = Color(0xFFCD0404),
    red = Color(0xFFCD0404),
    green = Color(0xFF379237),
    blue = Color(0xFF0081C9),
    black = Color(0, 0, 0),
    definitionCard = Color(0xFFCD0404),
    exampleCard = Color(0xFF0081C9),
    savedCard = Color(0xFF0081C9),
    button = Color(0xFF452256)
)

val purpleLightPalette = baseLightPalette.copy(
    tintColor = Color(0xFFAD57D9),
    definitionCard = Color(0xFFC780FA),
    exampleCard = Color(0xFFB980F0),
    button = Color(0xFFB980F0)
)

val purpleDarkPalette = baseDarkPalette.copy(
    tintColor = Color(0xFFD580FF),
    definitionCard = Color(0xFF810CA8),
    exampleCard = Color(0xFF3B185F),
    button = Color(0xFF3B185F)
)

val redLightPalette = baseLightPalette.copy(
    tintColor = Color(0xFFFF0032),
    definitionCard = Color(0xFFFD5D5D),
    exampleCard = Color(0xFFFF4646),
    button = Color(0xFFFF4646)
)

val redDarkPalette = baseDarkPalette.copy(
    tintColor = Color(0xFFCD0404),
    definitionCard = Color(0xFFB25068),
    exampleCard = Color(0xFF950101),
    button = Color(0xFF950101)
)

val blueLightPalette = baseLightPalette.copy(
    tintColor = Color(0xFF5BC0F8),
    definitionCard = Color(0xFF93C6E7),
    exampleCard = Color(0xFF579BB1),
    button = Color(0xFF579BB1)
)

val blueDarkPalette = baseDarkPalette.copy(
    tintColor = Color(0xFF0081C9),
    definitionCard = Color(0xFF00337C),
    exampleCard = Color(0xFF144272),
    button = Color(0xFF144272)
)

val greenLightPalette = baseLightPalette.copy(
    tintColor = Color(0xFF54B435),
    definitionCard = Color(0xFFAACB73),
    exampleCard = Color(0xFF38E54D),
    button = Color(0xFF38E54D)
)

val greenDarkPalette = baseDarkPalette.copy(
    tintColor = Color(0xFF379237),
    definitionCard = Color(0xFF1E5128),
    exampleCard = Color(0xFF519872),
    button = Color(0xFF519872)
)

val darkLightPalette = baseLightPalette.copy(
    tintColor = Color(0xFF191E23),
    definitionCard = Color(0xFF473C33),
    exampleCard = Color(0xFF303841),
    button = Color(0xFF303841)
)

val darkDarkPalette = baseDarkPalette.copy(
    tintColor = Color(0, 0, 0),
    definitionCard = Color(0xFF404258),
    exampleCard = Color(0xFF2D033B),
    button = Color(0xFF2D033B)
)

interface CustomColorInterface {
    var customPalette: OwnColors
}

class CustomColor(settingsSharedPreferences: SettingsSharedPreferences) : CustomColorInterface {
    private val settingsViewModel: SettingsViewModel by inject(SettingsViewModel::class.java)
    override var customPalette: OwnColors = OwnColors(
        primaryText = Color(settingsViewModel.primaryTextDark.value),
        primaryBackground = Color(settingsViewModel.primaryBackgroundDark.value),
        secondaryText = Color(settingsViewModel.secondaryTextDark.value),
        secondaryBackground = Color(settingsViewModel.secondaryBackgroundDark.value),
        tintColor = Color(settingsViewModel.tintColorDark.value),
        backgroundInBackground = Color(settingsViewModel.backgroundInBackgroundDark.value),
        purple = Color(settingsViewModel.purpleDark.value),
        red = Color(settingsViewModel.redDark.value),
        error = Color(settingsViewModel.errorDark.value),
        green = Color(settingsViewModel.greenDark.value),
        blue = Color(settingsViewModel.blueDark.value),
        black = Color(settingsViewModel.blackDark.value),
        definitionCard = Color(settingsViewModel.definitionCardDark.value),
        exampleCard = Color(settingsViewModel.exampleCardDark.value),
        savedCard = Color(settingsViewModel.savedCardDark.value),
        button = Color(settingsViewModel.buttonDark.value)
    )
}

enum class CustomColorName {
    CustomThemePrimaryText, CustomThemePrimaryBackground, CustomThemeSecondaryText, CustomThemeSecondaryBackground,
    CustomThemeTintColor, CustomThemeBackgroundInBackground, CustomThemePurple, CustomThemeRed, CustomThemeError,
    CustomThemeGreen, CustomThemeBlue, CustomThemeBlack, CustomThemeDefinitionCard, CustomThemeExampleCard, CustomThemeSavedCard,
    CustomThemeButton
}

enum class CustomTextSize {
    CustomTextSizeGeneral
}