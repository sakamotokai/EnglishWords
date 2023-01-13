package com.example.englishwords.ui.theme.ownTheme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

data class OwnColors(
    val primaryText: Color,
    val primaryBackground: Color,
    val secondaryText: Color,
    val secondaryBackground: Color,
    val tintColor: Color,
    val backgroundInBackground:Color,
    val purple:Color,
    val red:Color,
    val error:Color,
    val green:Color,
    val blue:Color,
    val black:Color
)

data class OwnTypography(
    val heading: TextStyle,
    val body: TextStyle,
    val toolbar: TextStyle
)

data class OwnShape(
    val padding: Dp,
    val cornersStyle: Shape
)

data class OwnSizesShapes(
    val smallShape: Shape,
    val mediumShape:Shape,
    val bigShape:Shape,
    val largeShape:Shape,
)

object OwnTheme{

    val colors:OwnColors
    @Composable
    get() = LocalColors.current

    val sizesShapes:OwnSizesShapes
    @Composable
    get() = LocalSizesShapes.current

    val shapes:OwnShape
    @Composable
    get() = LocalShape.current

    val typography:OwnTypography
    @Composable
    get() = LocalTypography.current

    enum class OwnStyle{
        Purple,Blue,Red,Green,Black
    }

    enum class OwnSize{
        Small,Medium,Big
    }

    enum class OwnCorners{
        Flat,Rounded
    }

    val LocalColors = staticCompositionLocalOf<OwnColors> {
        error("No colors provided")
    }

    val LocalTypography = staticCompositionLocalOf<OwnTypography> {
        error("No font provided")
    }

    val LocalSizesShapes = staticCompositionLocalOf<OwnSizesShapes> {
        error("No sizesShapes provided")
    }

    val LocalShape = staticCompositionLocalOf<OwnShape> {
        error("No shapes provided")
    }
}