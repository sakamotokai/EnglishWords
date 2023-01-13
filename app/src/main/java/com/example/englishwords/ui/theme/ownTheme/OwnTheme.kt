package com.example.englishwords.ui.theme.ownTheme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.englishwords.ui.theme.ownTheme.OwnTheme.LocalColors
import com.example.englishwords.ui.theme.ownTheme.OwnTheme.LocalShape
import com.example.englishwords.ui.theme.ownTheme.OwnTheme.LocalSizesShapes
import com.example.englishwords.ui.theme.ownTheme.OwnTheme.LocalTypography

@Composable
fun OwnTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    style:OwnTheme.OwnStyle = OwnTheme.OwnStyle.Purple,
    textSize:OwnTheme.OwnSize = OwnTheme.OwnSize.Medium,
    paddingSize:OwnTheme.OwnSize = OwnTheme.OwnSize.Medium,
    corners: OwnTheme.OwnCorners = OwnTheme.OwnCorners.Rounded,
    content:@Composable () -> Unit
){

    val sizesShapes = OwnSizesShapes(
        smallShape = RoundedCornerShape(4),
        mediumShape = RoundedCornerShape(8),
        bigShape = RoundedCornerShape(16),
        largeShape = RoundedCornerShape(32)
    )

    val colors = when(darkTheme){
        true->{
            when(style){
                OwnTheme.OwnStyle.Purple-> purpleDarkPalette
                OwnTheme.OwnStyle.Blue-> blueDarkPalette
                OwnTheme.OwnStyle.Black -> darkDarkPalette
                OwnTheme.OwnStyle.Red -> redDarkPalette
                OwnTheme.OwnStyle.Green -> greenDarkPalette
            }
        }
        false->{
            when(style){
                OwnTheme.OwnStyle.Blue-> blueLightPalette
                OwnTheme.OwnStyle.Purple-> purpleLightPalette
                OwnTheme.OwnStyle.Red-> redLightPalette
                OwnTheme.OwnStyle.Black-> darkLightPalette
                OwnTheme.OwnStyle.Green-> greenLightPalette
            }
        }
    }

    val typography = OwnTypography(
        heading = TextStyle(
            fontSize = when(textSize){
                OwnTheme.OwnSize.Small -> 24.sp
                OwnTheme.OwnSize.Medium -> 28.sp
                OwnTheme.OwnSize.Big -> 32.sp
            },
            fontWeight = FontWeight.Bold
        ),
        body = TextStyle(
            fontSize = when(textSize){
                OwnTheme.OwnSize.Small -> 14.sp
                OwnTheme.OwnSize.Medium -> 16.sp
                OwnTheme.OwnSize.Big -> 18.sp
            },
            fontWeight = FontWeight.Normal
        ),
        toolbar = TextStyle(
            fontSize = when(textSize){
                OwnTheme.OwnSize.Small -> 14.sp
                OwnTheme.OwnSize.Medium -> 16.sp
                OwnTheme.OwnSize.Big -> 18.sp
            },
            fontWeight = FontWeight.Medium
        )
    )

    val shapes = OwnShape(
        padding = when(paddingSize){
            OwnTheme.OwnSize.Small -> 12.dp
            OwnTheme.OwnSize.Medium -> 16.dp
            OwnTheme.OwnSize.Big -> 20.dp
        },
        cornersStyle = when(corners){
            OwnTheme.OwnCorners.Flat -> RoundedCornerShape(0.dp)
            OwnTheme.OwnCorners.Rounded -> RoundedCornerShape(8.dp)
        }
    )

    CompositionLocalProvider(
        LocalTypography provides typography,
        LocalColors provides colors,
        LocalShape provides shapes,
        LocalSizesShapes provides sizesShapes,
        content = content
    )
}