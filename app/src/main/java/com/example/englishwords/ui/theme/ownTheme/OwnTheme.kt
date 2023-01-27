package com.example.englishwords.ui.theme.ownTheme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.englishwords.screens.settingsScreen.SettingsViewModel
import com.example.englishwords.ui.theme.ownTheme.OwnTheme.LocalColors
import com.example.englishwords.ui.theme.ownTheme.OwnTheme.LocalDp
import com.example.englishwords.ui.theme.ownTheme.OwnTheme.LocalShape
import com.example.englishwords.ui.theme.ownTheme.OwnTheme.LocalSizesShapes
import com.example.englishwords.ui.theme.ownTheme.OwnTheme.LocalTypography
import org.koin.androidx.compose.get

@Composable
fun OwnTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    style: OwnTheme.OwnStyle = OwnTheme.OwnStyle.Purple,
    textSize: OwnTheme.OwnSize = OwnTheme.OwnSize.Medium,
    paddingSize: OwnTheme.OwnSize = OwnTheme.OwnSize.Medium,
    corners: OwnTheme.OwnCorners = OwnTheme.OwnCorners.Rounded,
    content: @Composable () -> Unit
) {

    val sizesShapes = OwnSizesShapes(
        smallShape = RoundedCornerShape(4),
        mediumShape = RoundedCornerShape(8),
        bigShape = RoundedCornerShape(16),
        largeShape = RoundedCornerShape(32)
    )

    val dp = baseDp

    val settingsViewModel: SettingsViewModel = get()
    val colors =
        if (darkTheme) {
            baseDarkPalette.copy(//TODO zdelat' etu huinu blyat
                tintColor = Color(settingsViewModel.tintColorDark.collectAsState().value.toULong()),
                //primaryBackground = Color(settingsViewModel.primaryBackgroundDark.collectAsState().value),
                black = Color(settingsViewModel.blackDark.collectAsState().value),
                blue = Color(settingsViewModel.blueDark.collectAsState().value),
                //backgroundInBackground = Color(settingsViewModel.backgroundInBackgroundDark.collectAsState().value),
                error = Color(settingsViewModel.errorDark.collectAsState().value),
                green = Color(settingsViewModel.greenDark.collectAsState().value),
                primaryText = Color(settingsViewModel.primaryTextDark.collectAsState().value),
                purple = Color(settingsViewModel.purpleDark.collectAsState().value),
                red = Color(settingsViewModel.redDark.collectAsState().value),
                //secondaryBackground = Color(settingsViewModel.secondaryBackgroundDark.collectAsState().value),
                secondaryText = Color(settingsViewModel.secondaryTextDark.collectAsState().value),
                exampleCard = Color(settingsViewModel.exampleCardDark.collectAsState().value.toULong()),
                definitionCard = Color(settingsViewModel.definitionCardDark.collectAsState().value.toULong()),
                savedCard = Color(settingsViewModel.savedCardDark.collectAsState().value.toULong())
            )
        } else {
            baseLightPalette.copy(
                tintColor = Color(settingsViewModel.tintColor.collectAsState().value.toULong()),
                //primaryBackground = Color(settingsViewModel.primaryBackground.collectAsState().value),
                black = Color(settingsViewModel.black.collectAsState().value),
                blue = Color(settingsViewModel.blue.collectAsState().value),
                //backgroundInBackground = Color(settingsViewModel.backgroundInBackground.collectAsState().value),
                error = Color(settingsViewModel.error.collectAsState().value),
                green = Color(settingsViewModel.green.collectAsState().value),
                primaryText = Color(settingsViewModel.primaryText.collectAsState().value),
                purple = Color(settingsViewModel.purple.collectAsState().value),
                red = Color(settingsViewModel.red.collectAsState().value),
                //secondaryBackground = Color(settingsViewModel.secondaryBackground.collectAsState().value),
                secondaryText = Color(settingsViewModel.secondaryText.collectAsState().value),
                exampleCard = Color(settingsViewModel.exampleCard.collectAsState().value.toULong()),
                definitionCard = Color(settingsViewModel.definitionCard.collectAsState().value.toULong()),
                savedCard = Color(settingsViewModel.savedCard.collectAsState().value.toULong())
            )
        }

    val typography = OwnTypography(
        heading = TextStyle(
            fontSize = when (textSize) {
                OwnTheme.OwnSize.Small -> 24.sp
                OwnTheme.OwnSize.Medium -> 28.sp
                OwnTheme.OwnSize.Big -> 32.sp
            },
            fontWeight = FontWeight.Bold
        ),
        body = TextStyle(
            fontSize = when (textSize) {
                OwnTheme.OwnSize.Small -> 14.sp
                OwnTheme.OwnSize.Medium -> 16.sp
                OwnTheme.OwnSize.Big -> 18.sp
            },
            fontWeight = FontWeight.Normal
        ),
        toolbar = TextStyle(
            fontSize = when (textSize) {
                OwnTheme.OwnSize.Small -> 14.sp
                OwnTheme.OwnSize.Medium -> 16.sp
                OwnTheme.OwnSize.Big -> 18.sp
            },
            fontWeight = FontWeight.Medium
        ),
        general = TextStyle(
            fontSize = settingsViewModel.textSize.collectAsState().value.sp
        )
    )

    val shapes = OwnShape(
        padding = when (paddingSize) {
            OwnTheme.OwnSize.Small -> 12.dp
            OwnTheme.OwnSize.Medium -> 16.dp
            OwnTheme.OwnSize.Big -> 20.dp
        },
        cornersStyle = when (corners) {
            OwnTheme.OwnCorners.Flat -> RoundedCornerShape(0.dp)
            OwnTheme.OwnCorners.Rounded -> RoundedCornerShape(8.dp)
        }
    )

    CompositionLocalProvider(
        LocalTypography provides typography,
        LocalColors provides colors,
        LocalShape provides shapes,
        LocalSizesShapes provides sizesShapes,
        LocalDp provides dp,
        content = content
    )
}