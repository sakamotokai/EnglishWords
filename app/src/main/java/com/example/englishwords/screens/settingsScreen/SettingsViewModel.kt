package com.example.englishwords.screens.settingsScreen

import androidx.lifecycle.ViewModel
import com.example.englishwords.models.preferencesModels.SettingsSharedPreferences
import com.example.englishwords.ui.theme.ownTheme.CustomColorName
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsViewModel(settingsSharedPreferences: SettingsSharedPreferences) : ViewModel() {
//TODO("Do optimisation for this class and set up custom theme system")
    val settingsSharedPreference = settingsSharedPreferences.getPreferences()

    private var _primaryTextDark: MutableStateFlow<Long> = MutableStateFlow(
        settingsSharedPreference.getLong(
            CustomColorName.CustomThemePrimaryText.name,
            0xFFF2F4F5
        )
    )
    var primaryTextDark: StateFlow<Long> = _primaryTextDark

    private var _primaryBackgroundDark: MutableStateFlow<Long> = MutableStateFlow(
        settingsSharedPreference.getLong(
            CustomColorName.CustomThemePrimaryBackground.name,
            0xFF23282D
        )
    )
    var primaryBackgroundDark: StateFlow<Long> = _primaryBackgroundDark

    private var _secondaryTextDark: MutableStateFlow<Long> = MutableStateFlow(
        settingsSharedPreference.getLong(
            CustomColorName.CustomThemeSecondaryText.name,
            0xCC7A8A99
        )
    )
    var secondaryTextDark: StateFlow<Long> = _secondaryTextDark

    private var _secondaryBackgroundDark: MutableStateFlow<Long> = MutableStateFlow(
        settingsSharedPreference.getLong(
            CustomColorName.CustomThemeSecondaryBackground.name,
            0xFF191E23
        )
    )
    var secondaryBackgroundDark: StateFlow<Long> = _secondaryBackgroundDark

    private var _tintColorDark: MutableStateFlow<Long> = MutableStateFlow(
        settingsSharedPreference.getLong(
            CustomColorName.CustomThemeTintColor.name,
            0xFF5BC0F8
        )
    )
    var tintColorDark: StateFlow<Long> = _tintColorDark

    private var _backgroundInBackgroundDark: MutableStateFlow<Long> = MutableStateFlow(
        settingsSharedPreference.getLong(
            CustomColorName.CustomThemeBackgroundInBackground.name,
            0xFF000000
        )
    )
    var backgroundInBackgroundDark: StateFlow<Long> = _backgroundInBackgroundDark

    private var _purpleDark: MutableStateFlow<Long> = MutableStateFlow(
        settingsSharedPreference.getLong(
            CustomColorName.CustomThemePurple.name,
            0xFF452256
        )
    )
    var purpleDark: StateFlow<Long> = _purpleDark

    private var _redDark: MutableStateFlow<Long> = MutableStateFlow(
        settingsSharedPreference.getLong(
            CustomColorName.CustomThemeRed.name,
            0xFFCD0404
        )
    )
    var redDark: StateFlow<Long> = _redDark

    private var _errorDark: MutableStateFlow<Long> = MutableStateFlow(
        settingsSharedPreference.getLong(
            CustomColorName.CustomThemeError.name,
            0xFFCD0404
        )
    )
    var errorDark: StateFlow<Long> = _errorDark

    private var _greenDark: MutableStateFlow<Long> = MutableStateFlow(
        settingsSharedPreference.getLong(
            CustomColorName.CustomThemeGreen.name,
            0xFF379237
        )
    )
    var greenDark: StateFlow<Long> = _greenDark

    private var _blueDark: MutableStateFlow<Long> = MutableStateFlow(
        settingsSharedPreference.getLong(
            CustomColorName.CustomThemeBlue.name,
            0xFF0081C9
        )
    )
    var blueDark: StateFlow<Long> = _blueDark

    private var _blackDark: MutableStateFlow<Long> = MutableStateFlow(
        settingsSharedPreference.getLong(
            CustomColorName.CustomThemeBlack.name,
            0xFF000000
        )
    )
    var blackDark: StateFlow<Long> = _blackDark

    private var _definitionCardDark: MutableStateFlow<Long> = MutableStateFlow(
        settingsSharedPreference.getLong(
            CustomColorName.CustomThemeDefinitionCard.name,
            0xFFCD0404
        )
    )
    var definitionCardDark: StateFlow<Long> = _definitionCardDark

    private var _exampleCardDark: MutableStateFlow<Long> = MutableStateFlow(
        settingsSharedPreference.getLong(
            CustomColorName.CustomThemeExampleCard.name,
            0xFF0081C9
        )
    )
    var exampleCardDark: StateFlow<Long> = _exampleCardDark

    fun setTintDark(color: Long) {
        _tintColorDark.value = color
        settingsSharedPreference.edit().putLong(CustomColorName.CustomThemeTintColor.name, color)
            .apply()
    }
    fun setBlueDark(color: Long) {
        _blueDark.value = color
        settingsSharedPreference.edit().putLong(CustomColorName.CustomThemeBlue.name, color)
            .apply()
    }
    fun setRedDark(color: Long) {
        _redDark.value = color
        settingsSharedPreference.edit().putLong(CustomColorName.CustomThemeRed.name, color)
            .apply()
    }
    fun setGreenDark(color: Long) {
        _greenDark.value = color
        settingsSharedPreference.edit().putLong(CustomColorName.CustomThemeGreen.name, color)
            .apply()
    }
    fun setBlackDark(color: Long) {
        _blackDark.value = color
        settingsSharedPreference.edit().putLong(CustomColorName.CustomThemeBlack.name, color)
            .apply()
    }
    fun setErrorDark(color: Long) {
        _errorDark.value = color
        settingsSharedPreference.edit().putLong(CustomColorName.CustomThemeError.name, color)
            .apply()
    }
    fun setPurpleDark(color: Long) {
        _purpleDark.value = color
        settingsSharedPreference.edit().putLong(CustomColorName.CustomThemePurple.name, color)
            .apply()
    }
    fun setBackgroundInBackgroundDark(color: Long) {
        _backgroundInBackgroundDark.value = color
        settingsSharedPreference.edit().putLong(CustomColorName.CustomThemeBackgroundInBackground.name, color)
            .apply()
    }
    fun setSecondaryBackgroundDark(color: Long) {
        _secondaryBackgroundDark.value = color
        settingsSharedPreference.edit().putLong(CustomColorName.CustomThemeSecondaryBackground.name, color)
            .apply()
    }
    fun setSecondaryTextDark(color: Long) {
        _secondaryTextDark.value = color
        settingsSharedPreference.edit().putLong(CustomColorName.CustomThemeSecondaryText.name, color)
            .apply()
    }
    fun setPrimaryBackgroundDark(color: Long) {
        _primaryBackgroundDark.value = color
        settingsSharedPreference.edit().putLong(CustomColorName.CustomThemePrimaryBackground.name, color)
            .apply()
    }
    fun setPrimaryTextDark(color: Long) {
        _primaryTextDark.value = color
        settingsSharedPreference.edit().putLong(CustomColorName.CustomThemePrimaryText.name, color)
            .apply()
    }

    fun setDefinitionCardDark(color: Long) {
        _definitionCardDark.value = color
        settingsSharedPreference.edit().putLong(CustomColorName.CustomThemeDefinitionCard.name, color)
            .apply()
    }

    fun setExampleCardDark(color: Long) {
        _exampleCardDark.value = color
        settingsSharedPreference.edit().putLong(CustomColorName.CustomThemeExampleCard.name, color)
            .apply()
    }

    private var _primaryText: MutableStateFlow<Long> = MutableStateFlow(
        settingsSharedPreference.getLong(
            CustomColorName.CustomThemePrimaryText.name,
            0xFF3D454C
        )
    )
    var primaryText: StateFlow<Long> = _primaryText

    private var _primaryBackground: MutableStateFlow<Long> = MutableStateFlow(
        settingsSharedPreference.getLong(
            CustomColorName.CustomThemePrimaryBackground.name,
            0xFFFFFFFF
        )
    )
    var primaryBackground: StateFlow<Long> = _primaryBackground

    private var _secondaryText: MutableStateFlow<Long> = MutableStateFlow(
        settingsSharedPreference.getLong(
            CustomColorName.CustomThemeSecondaryText.name,
            0xCC7A8A99
        )
    )
    var secondaryText: StateFlow<Long> = _secondaryText

    private var _secondaryBackground: MutableStateFlow<Long> = MutableStateFlow(
        settingsSharedPreference.getLong(
            CustomColorName.CustomThemeSecondaryBackground.name,
            0xFFE1E1E1
        )
    )
    var secondaryBackground: StateFlow<Long> = _secondaryBackground

    private var _tintColor: MutableStateFlow<Long> = MutableStateFlow(
        settingsSharedPreference.getLong(
            CustomColorName.CustomThemeTintColor.name,
            0xFF5BC0F8
        )
    )
    var tintColor: StateFlow<Long> = _tintColor

    private var _backgroundInBackground: MutableStateFlow<Long> = MutableStateFlow(
        settingsSharedPreference.getLong(
            CustomColorName.CustomThemeBackgroundInBackground.name,
            0xFFD0D0D0
        )
    )
    var backgroundInBackground: StateFlow<Long> = _backgroundInBackground

    private var _purple: MutableStateFlow<Long> = MutableStateFlow(
        settingsSharedPreference.getLong(
            CustomColorName.CustomThemePurple.name,
            0xFF9b4ec3
        )
    )
    var purple: StateFlow<Long> = _purple

    private var _red: MutableStateFlow<Long> = MutableStateFlow(
        settingsSharedPreference.getLong(
            CustomColorName.CustomThemeRed.name,
            0xFFFF0032
        )
    )
    var red: StateFlow<Long> = _red

    private var _error: MutableStateFlow<Long> = MutableStateFlow(
        settingsSharedPreference.getLong(
            CustomColorName.CustomThemeError.name,
            0xFFFF0032
        )
    )
    var error: StateFlow<Long> = _error

    private var _green: MutableStateFlow<Long> = MutableStateFlow(
        settingsSharedPreference.getLong(
            CustomColorName.CustomThemeGreen.name,
            0xFF54B435
        )
    )
    var green: StateFlow<Long> = _green

    private var _blue: MutableStateFlow<Long> = MutableStateFlow(
        settingsSharedPreference.getLong(
            CustomColorName.CustomThemeBlue.name,
            0xFF5BC0F8
        )
    )
    var blue: StateFlow<Long> = _blue

    private var _black: MutableStateFlow<Long> = MutableStateFlow(
        settingsSharedPreference.getLong(
            CustomColorName.CustomThemeBlack.name,
            0xFF191E23
        )
    )
    var black: StateFlow<Long> = _black

    private var _definitionCard: MutableStateFlow<Long> = MutableStateFlow(
        settingsSharedPreference.getLong(
            CustomColorName.CustomThemeDefinitionCard.name,
            0xFFFF0032
        )
    )
    var definitionCard: StateFlow<Long> = _definitionCard

    private var _exampleCard: MutableStateFlow<Long> = MutableStateFlow(
        settingsSharedPreference.getLong(
            CustomColorName.CustomThemeExampleCard.name,
            0xFF5BC0F8
        )
    )
    var exampleCard: StateFlow<Long> = _exampleCard

    fun setTint(color: Long) {
        _tintColor.value = color
        settingsSharedPreference.edit().putLong(CustomColorName.CustomThemeTintColor.name, color)
            .apply()
    }
    fun setBlue(color: Long) {
        _blue.value = color
        settingsSharedPreference.edit().putLong(CustomColorName.CustomThemeBlue.name, color)
            .apply()
    }
    fun setRed(color: Long) {
        _red.value = color
        settingsSharedPreference.edit().putLong(CustomColorName.CustomThemeRed.name, color)
            .apply()
    }
    fun setBlack(color: Long) {
        _black.value = color
        settingsSharedPreference.edit().putLong(CustomColorName.CustomThemeBlack.name, color)
            .apply()
    }
    fun setGreen(color: Long) {
        _green.value = color
        settingsSharedPreference.edit().putLong(CustomColorName.CustomThemeGreen.name, color)
            .apply()
    }
    fun setError(color: Long) {
        _error.value = color
        settingsSharedPreference.edit().putLong(CustomColorName.CustomThemeError.name, color)
            .apply()
    }
    fun setPurple(color: Long) {
        _purple.value = color
        settingsSharedPreference.edit().putLong(CustomColorName.CustomThemePurple.name, color)
            .apply()
    }
    fun setBackgroundInBackground(color: Long) {
        _backgroundInBackground.value = color
        settingsSharedPreference.edit().putLong(CustomColorName.CustomThemeBackgroundInBackground.name, color)
            .apply()
    }
    fun setSecondaryBackground(color: Long) {
        _secondaryBackground.value = color
        settingsSharedPreference.edit().putLong(CustomColorName.CustomThemeSecondaryBackground.name, color)
            .apply()
    }
    fun setSecondaryText(color: Long) {
        _secondaryText.value = color
        settingsSharedPreference.edit().putLong(CustomColorName.CustomThemeSecondaryText.name, color)
            .apply()
    }
    fun setPrimaryBackground(color: Long) {
        _primaryBackground.value = color
        settingsSharedPreference.edit().putLong(CustomColorName.CustomThemePrimaryBackground.name, color)
            .apply()
    }
    fun setPrimaryText(color: Long) {
        _primaryText.value = color
        settingsSharedPreference.edit().putLong(CustomColorName.CustomThemePrimaryText.name, color)
            .apply()
    }

    fun setDefinitionCard(color: Long) {
        _definitionCard.value = color
        settingsSharedPreference.edit().putLong(CustomColorName.CustomThemeDefinitionCard.name, color)
            .apply()
    }

    fun setExampleCard(color: Long) {
        _exampleCard.value = color
        settingsSharedPreference.edit().putLong(CustomColorName.CustomThemeExampleCard.name, color)
            .apply()
    }

}