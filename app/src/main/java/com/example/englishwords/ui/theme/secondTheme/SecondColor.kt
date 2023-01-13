package com.example.englishwords.ui.theme.secondTheme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

class SecondColor (
    primary:Color,
    secondary: Color,
    textPrimary:Color,
    error:Color,
    primaryBackground:Color,
    isLight:Boolean
){

    var primary by mutableStateOf(primary)
    private set

    var secondary by mutableStateOf(secondary)
    private set

    var textPrimary by mutableStateOf(textPrimary)
    private set

    var error by mutableStateOf(error)
    private set

    var primaryBackground by mutableStateOf(primaryBackground)
    private set

    var isLight by mutableStateOf(isLight)
    internal set

    fun copy(
        primary:Color = this.primary,
        secondary:Color = this.secondary,
        textPrimary:Color = this.textPrimary,
        error:Color = this.error,
        primaryBackground: Color = this.primaryBackground,
        isLight:Boolean = this.isLight
    ):SecondColor = SecondColor(
        primary,
        secondary,
        textPrimary,
        error,
        primaryBackground,
        isLight

    )

    fun updateColorsFrom(other:SecondColor){
        primary = other.primary
        textPrimary = other.textPrimary
        secondary = other.secondary
        primaryBackground = other.primaryBackground
        error = other.error
    }

}

private val colorLightPrimary = Color(0xFFFFB400)
private val colorLightTextPrimary = Color(0xFF000000)
private val colorLightTextSecondary = Color(0xFF6C727A)
private val colorLightBackground = Color(0xFFFFFFFF)
private val colorLightError = Color(0xFFD62222)

fun lightColors(
    primary: Color = colorLightPrimary,
    textPrimary: Color = colorLightTextPrimary,
    textSecondary: Color = colorLightTextSecondary,
    background: Color = colorLightBackground,
    error: Color = colorLightError
): SecondColor = SecondColor(
    primary = primary,
    textPrimary = textPrimary,
    secondary = textSecondary,
    primaryBackground = background,
    error = error,
    isLight = true
)

fun darkColors(
    primary: Color = colorLightPrimary,
    textPrimary: Color = colorLightTextPrimary,
    textSecondary: Color = colorLightTextSecondary,
    background: Color = colorLightBackground,
    error: Color = colorLightError
): SecondColor = SecondColor(
    primary = primary,
    textPrimary = textPrimary,
    secondary = textSecondary,
    primaryBackground = background,
    error = error,
    isLight = false
)

internal val SecondLocalColors = staticCompositionLocalOf { lightColors() }