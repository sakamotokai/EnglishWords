package com.example.englishwords.screens.settingsScreen.manageTheme.changeColor

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.englishwords.db.room.Modeldb
import com.example.englishwords.screens.MainCard
import com.example.englishwords.screens.settingsScreen.SettingsViewModel
import com.example.englishwords.ui.theme.ownTheme.*
import com.example.englishwords.viewModels.GlobalSettingsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ChangeColorScreen(navController: NavHostController) {
    Log.e("Log","Change Color Screen Start")
    val globalSettingsViewModel: GlobalSettingsViewModel = get()
    val settingsViewModel: SettingsViewModel = get()
    //globalSettingsViewModel.setMainGesturesEnabled(false)
    /*BackHandler(true) {
        globalSettingsViewModel.setMainGesturesEnabled(true)
        navController.popBackStack()
    }*/
    Column {
        val isDarkMode = globalSettingsViewModel.isDarkMode.collectAsState()
        var RGBRed by remember { mutableStateOf(0f) }
        var RGBGreen by remember { mutableStateOf(0f) }
        var RGBBlue by remember { mutableStateOf(0f) }
        Slider(
            value = RGBRed,
            onValueChange = { RGBRed = it }
        )
        Slider(
            value = RGBGreen,
            onValueChange = { RGBGreen = it }
        )
        Slider(
            value = RGBBlue,
            onValueChange = { RGBBlue = it }
        )
        Box(
            modifier = Modifier.background(
                color = Color(
                    red = RGBRed,
                    green = RGBGreen,
                    blue = RGBBlue
                )
            )
        ) {
            Text(text = "Text")
        }
        Box(
            modifier = Modifier
                .background(
                    OwnTheme.colors.secondaryBackground,
                    shape = OwnTheme.sizesShapes.mediumShape
                )
                .fillMaxWidth()
        ) {
            Column {
                Spacer(modifier = Modifier.height(OwnTheme.dp.mediumDp))
                Text(
                    text = "Definition card text size",
                    color = OwnTheme.colors.primaryText,
                    style = TextStyle.Default.copy(fontStyle = OwnTheme.typography.heading.fontStyle),
                    modifier = Modifier
                        .padding(start = OwnTheme.dp.mediumDp)
                )
                Spacer(modifier = Modifier.height(OwnTheme.dp.normalDp))
                Slider(
                    value = 0f,
                    onValueChange = {}
                )
                Spacer(modifier = Modifier.height(OwnTheme.dp.normalDp))
                ThemeExample(
                    OwnTheme.colors.copy(
                        tintColor = Color(
                            red = RGBRed,
                            green = RGBGreen,
                            blue = RGBBlue
                        )
                    )
                )
                Spacer(modifier = Modifier.height(OwnTheme.dp.mediumDp))
            }
        }
        Spacer(modifier = Modifier.height(OwnTheme.dp.normalDp))
        Box(
            modifier = Modifier
                .background(
                    OwnTheme.colors.secondaryBackground,
                    shape = OwnTheme.sizesShapes.mediumShape
                )
                .fillMaxWidth()
        ) {
            Column {
                Spacer(modifier = Modifier.height(OwnTheme.dp.mediumDp))
                Text(
                    text = "Color theme",
                    color = OwnTheme.colors.primaryText,
                    style = TextStyle.Default.copy(fontStyle = OwnTheme.typography.heading.fontStyle),
                    modifier = Modifier
                        .padding(start = OwnTheme.dp.mediumDp)
                )
                Spacer(modifier = Modifier.height(OwnTheme.dp.normalDp))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .horizontalScroll(ScrollState(0))
                        .background(OwnTheme.colors.primaryBackground)
                        .padding(
                            top = OwnTheme.dp.smallDp,
                            end = OwnTheme.dp.smallDp,
                            bottom = OwnTheme.dp.smallDp
                        )
                ) {
                    if (isDarkMode.value) {
                        for (i in ChangeColorScreenThemeList().darkThemeList) {
                            Spacer(modifier = Modifier.width(OwnTheme.dp.smallDp))
                            Box(
                                Modifier
                                    .height(100.dp)
                                    .width(90.dp)
                                    .background(
                                        OwnTheme.colors.secondaryBackground,
                                        shape = OwnTheme.sizesShapes.mediumShape
                                    )
                                    .clickable {
                                        settingsViewModel.setDefinitionCardDark(i.definitionCard.value.toLong())
                                        settingsViewModel.setExampleCardDark(i.exampleCard.value.toLong())
                                        settingsViewModel.setTintDark(i.tintColor.value.toLong())
                                    }
                            ) {
                                ThemeExampleLessSize(i)
                            }
                        }
                    } else {
                        for (i in ChangeColorScreenThemeList().lightThemeList) {
                            Spacer(modifier = Modifier.width(OwnTheme.dp.smallDp))
                            Box(
                                Modifier
                                    .height(100.dp)
                                    .width(90.dp)
                                    .background(
                                        OwnTheme.colors.secondaryBackground,
                                        shape = OwnTheme.sizesShapes.mediumShape
                                    ).clickable {
                                        settingsViewModel.setDefinitionCard(i.definitionCard.value.toLong())
                                        settingsViewModel.setExampleCard(i.exampleCard.value.toLong())
                                        settingsViewModel.setTint(i.tintColor.value.toLong())
                                    }

                            ) {
                                ThemeExampleLessSize(i)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(OwnTheme.dp.normalDp))
                }
                Spacer(modifier = Modifier.height(OwnTheme.dp.bigDp))
            }
        }
    }
}

class ChangeColorScreenThemeList() {
    var lightThemeList = listOf<OwnColors>(
        baseLightPalette,
        purpleLightPalette,
        greenLightPalette,
        blueLightPalette,
        darkLightPalette,
        redLightPalette
    )
    var darkThemeList = listOf<OwnColors>(
        baseDarkPalette, purpleDarkPalette, greenDarkPalette, blueDarkPalette, darkDarkPalette,
        redDarkPalette
    )
    var saveThemeList = listOf<OwnColors>(
        baseDarkPalette,
        purpleDarkPalette,
        greenDarkPalette,
        blueDarkPalette,
        darkDarkPalette,
        redDarkPalette,
        baseLightPalette,
        purpleLightPalette,
        greenLightPalette,
        blueLightPalette,
        darkLightPalette,
        redLightPalette
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeExample(style: OwnColors = OwnTheme.colors) {
    var animatedErrorButton: Boolean by remember { mutableStateOf(false) }
    val errorColor by animateColorAsState(
        targetValue = if (animatedErrorButton) OwnTheme.colors.error else Color(
            0,
            0,
            0,
            0
        ),
    )
    val scale by animateFloatAsState(
        targetValue = if (animatedErrorButton) 1.1f else 1f,
        animationSpec = repeatable(
            iterations = 5,
            animation = tween(durationMillis = 50, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        finishedListener = {
            animatedErrorButton = false
        }
    )
    Spacer(modifier = Modifier.height(OwnTheme.dp.bigDp))
    OutlinedTextField(
        readOnly = true,
        value = "",
        onValueChange = {
        },
        modifier = Modifier
            .border(3.dp, color = style.tintColor/*OwnTheme.colors.tintColor*/, shape = CircleShape)
            .height(50.dp)
            .fillMaxSize()
            .shadow(5.dp, shape = CircleShape)
            .background(color = OwnTheme.colors.secondaryBackground),
        shape = CircleShape,
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Center,
            color = OwnTheme.colors.primaryText
        ),
        placeholder = {
            androidx.compose.material3.Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Enter your word",
                style = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center,
                    color = OwnTheme.colors.secondaryText
                )
            )
        }
    )
    Spacer(Modifier.height(OwnTheme.dp.normalDp))
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(
            onClick = {
                animatedErrorButton = true
            },
            modifier = Modifier
                .graphicsLayer {
                    scaleX = if (animatedErrorButton) scale else 1f
                    scaleY = if (animatedErrorButton) scale else 1f
                }
                .border(
                    width = 4.dp,
                    color = errorColor,
                    shape = RoundedCornerShape(100)
                )
                .shadow(5.dp, RoundedCornerShape(40)),
            colors = ButtonDefaults.buttonColors(containerColor = OwnTheme.colors.purple)
        ) {
            androidx.compose.material3.Text(text = "Search", color = OwnTheme.colors.primaryText)
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(OwnTheme.colors.secondaryBackground, shape = CircleShape)
            .border(width = 3.dp, color = OwnTheme.colors.purple, shape = CircleShape)
            .shadow(4.dp, CircleShape.copy(CornerSize(90)))
            .padding(start = 20.dp, end = 10.dp)
    )
    Spacer(modifier = Modifier.height(8.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeExampleLessSize(style: OwnColors = OwnTheme.colors) {
    var animatedErrorButton: Boolean by remember { mutableStateOf(false) }
    val errorColor by animateColorAsState(
        targetValue = if (animatedErrorButton) OwnTheme.colors.error else Color(
            0,
            0,
            0,
            0
        ),
    )
    val scale by animateFloatAsState(
        targetValue = if (animatedErrorButton) 1.1f else 1f,
        animationSpec = repeatable(
            iterations = 5,
            animation = tween(durationMillis = 50, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        finishedListener = {
            animatedErrorButton = false
        }
    )
    Column {
        Spacer(modifier = Modifier.height(OwnTheme.dp.smallDp))
        OutlinedTextField(
            readOnly = true,
            value = "",
            onValueChange = {
            },
            modifier = Modifier
                .border(
                    3.dp,
                    color = style.tintColor/*OwnTheme.colors.tintColor*/,
                    shape = CircleShape
                )
                .height(22.dp)
                .fillMaxSize()
                .shadow(2.dp, shape = CircleShape)
                .background(color = OwnTheme.colors.secondaryBackground),
            shape = CircleShape,
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Center,
                color = OwnTheme.colors.primaryText
            ),
            placeholder = {
                androidx.compose.material3.Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Enter your word",
                    style = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Center,
                        color = OwnTheme.colors.secondaryText
                    )
                )
            }
        )
        Spacer(Modifier.height(OwnTheme.dp.smallDp))
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Button(
                onClick = {
                    animatedErrorButton = true
                },
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = if (animatedErrorButton) scale else 1f
                        scaleY = if (animatedErrorButton) scale else 1f
                    }
                    .border(
                        width = 2.dp,
                        color = errorColor,
                        shape = RoundedCornerShape(100)
                    )
                    .shadow(2.dp, RoundedCornerShape(40))
                    .height(20.dp)
                    .width(32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = OwnTheme.colors.purple),
            ) {}
        }
        Spacer(modifier = Modifier.height(2.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(OwnTheme.colors.secondaryBackground, shape = CircleShape)
                .border(width = 2.dp, color = style.definitionCard, shape = CircleShape)
                .shadow(2.dp, CircleShape.copy(CornerSize(90)))
                .height(20.dp)
                .padding(start = 5.dp, end = 2.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(OwnTheme.colors.secondaryBackground, shape = CircleShape)
                .border(width = 2.dp, color = style.exampleCard, shape = CircleShape)
                .shadow(2.dp, CircleShape.copy(CornerSize(90)))
                .height(20.dp)
                .padding(start = 5.dp, end = 2.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
    }
}