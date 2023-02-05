package com.example.englishwords.screens.settingsScreen.manageTheme.changeColor

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.englishwords.screens.settingsScreen.SettingsViewModel
import com.example.englishwords.ui.theme.ownTheme.*
import com.example.englishwords.viewModels.GlobalSettingsViewModel
import kotlinx.coroutines.*
import org.koin.androidx.compose.get

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ChangeColorScreen(navController: NavHostController) {
    val globalSettingsViewModel: GlobalSettingsViewModel = get()
    val settingsViewModel: SettingsViewModel = get()
    Column(
        Modifier.verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(OwnTheme.dp.mediumDp))
        ChangeColorChangeColor(settingsViewModel, globalSettingsViewModel)
        Spacer(modifier = Modifier.height(OwnTheme.dp.normalDp))
        ChangeColorTextSizeBox(settingsViewModel)
        Spacer(modifier = Modifier.height(OwnTheme.dp.normalDp))
        ChangeColorColorThemeBox(
            globalSettingsViewModel = globalSettingsViewModel,
            settingsViewModel = settingsViewModel
        )
    }
}

@Composable
fun ChangeColorChangeColor(
    settingsViewModel: SettingsViewModel,
    globalSettingsViewModel: GlobalSettingsViewModel
) {
    var RGBRed by remember { mutableStateOf(0f) }
    var RGBGreen by remember { mutableStateOf(0f) }
    var RGBBlue by remember { mutableStateOf(0f) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                OwnTheme.colors.secondaryBackground,
                shape = OwnTheme.sizesShapes.mediumShape
            )
    ) {
        Column {
            Spacer(modifier = Modifier.height(OwnTheme.dp.mediumDp))
            Text(
                text = "Set custom color",
                color = OwnTheme.colors.primaryText,
                style = TextStyle.Default.copy(fontStyle = OwnTheme.typography.heading.fontStyle),
                modifier = Modifier
                    .padding(start = OwnTheme.dp.mediumDp),
                fontSize = OwnTheme.typography.general.fontSize.value.sp
            )
            Spacer(modifier = Modifier.height(OwnTheme.dp.normalDp))
            var dropMenuExpanded by remember { mutableStateOf(false) }
            var itemName: String = ""
            var itemOfList: List<String> = listOf()
            Slider(
                value = RGBRed,
                onValueChange = { RGBRed = it },
                colors = SliderDefaults.colors(
                    thumbColor = OwnTheme.colors.red,
                    activeTrackColor = OwnTheme.colors.red
                )
            )
            Slider(
                value = RGBGreen,
                onValueChange = { RGBGreen = it },
                colors = SliderDefaults.colors(
                    thumbColor = OwnTheme.colors.green,
                    activeTrackColor = OwnTheme.colors.green
                )
            )
            Slider(
                value = RGBBlue,
                onValueChange = { RGBBlue = it },
                colors = SliderDefaults.colors(
                    thumbColor = OwnTheme.colors.blue,
                    activeTrackColor = OwnTheme.colors.blue
                )
            )
            Row(Modifier.fillMaxWidth()) {
                Row {
                    ChangeColorExpandedCard(
                        dropMenuExpanded = dropMenuExpanded,
                        returnDropMenuExpanded = { dropMenuExpanded = it },
                        { itemName = it },
                        { itemOfList = it }
                    )
                }
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    ChangeColorSaveColorChanged(
                        isDarkMode = globalSettingsViewModel.isDarkMode.collectAsState().value,
                        itemName = itemName,
                        itemOfList = itemOfList,
                        settingsViewModel = settingsViewModel,
                        color = Color(red = RGBRed, green = RGBGreen, blue = RGBBlue)
                    )
                }
            }
            Spacer(modifier = Modifier.height(OwnTheme.dp.mediumDp))
            ChangeColorChangeColorItem(
                itemName = itemName, localName = itemOfList,
                Color(red = RGBRed, blue = RGBBlue, green = RGBGreen)
            )
            Spacer(modifier = Modifier.height(OwnTheme.dp.mediumDp))
        }
    }
}

@Composable
fun ColorChangeWordKeepedCard(
    item: String = "Example",
    color: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(OwnTheme.colors.secondaryBackground, shape = OwnTheme.sizesShapes.bigShape)
            .border(width = 3.dp, color = color, shape = OwnTheme.sizesShapes.bigShape)
            .padding(start = 20.dp, end = 10.dp, top = OwnTheme.dp.normalDp)
    ) {
        Row(Modifier.fillMaxWidth()) {
            androidx.compose.material3.Text(
                text = "delete",
                color = OwnTheme.colors.primaryText,
                fontSize = OwnTheme.typography.general.fontSize.value.sp
            )
            androidx.compose.material3.Text(
                text = item,
                textAlign = TextAlign.Center,
                color = OwnTheme.colors.primaryText,
                modifier = Modifier
                    .fillMaxWidth(),
                fontSize = OwnTheme.typography.general.fontSize.value.sp
            )
        }
        Spacer(modifier = Modifier.height(OwnTheme.dp.normalDp))
    }
    Spacer(modifier = Modifier.height(OwnTheme.dp.normalDp))
}

@Composable
fun ChangeColorDefExamCard(
    color: Color,
    cardType: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(OwnTheme.colors.secondaryBackground, shape = CircleShape)
            .border(width = 3.dp, color = color, shape = CircleShape)
            .shadow(4.dp, CircleShape.copy(CornerSize(90)))
            .padding(start = 20.dp, end = 10.dp)
    ) {
        Column {
            androidx.compose.material3.Text(
                text = cardType,
                color = OwnTheme.colors.primaryText,
                fontSize = OwnTheme.typography.general.fontSize.value.sp
            )
            Spacer(modifier = Modifier.height(OwnTheme.dp.mediumDp))
        }
    }
}

@Composable
fun ChangeColorSaveColorChanged(
    isDarkMode: Boolean,
    itemName: String,
    itemOfList: List<String>,
    settingsViewModel: SettingsViewModel,
    color: Color
) {
    Text(
        text = "Save color",
        fontSize = OwnTheme.typography.general.fontSize,
        color = OwnTheme.colors.primaryText,
        modifier = Modifier
            .clickable {
                if (isDarkMode)
                    when (itemName) {
                        itemOfList[0] -> {
                            settingsViewModel.setTintDark(color.value.toLong())
                        }
                        itemOfList[1] -> {
                            settingsViewModel.setDefinitionCardDark(color.value.toLong())
                        }
                        itemOfList[2] -> {
                            settingsViewModel.setExampleCardDark(color.value.toLong())
                        }
                        itemOfList[3] -> {
                            settingsViewModel.setSavedDark(color.value.toLong())
                        }
                        itemOfList[4]->{
                            settingsViewModel.setButtonDark(color.value.toLong())
                        }
                    }
                else
                    when (itemName) {
                        itemOfList[0] -> {
                            settingsViewModel.setTint(color.value.toLong())
                        }
                        itemOfList[1] -> {
                            settingsViewModel.setDefinitionCard(color.value.toLong())
                        }
                        itemOfList[2] -> {
                            settingsViewModel.setExampleCard(color.value.toLong())
                        }
                        itemOfList[3] -> {
                            settingsViewModel.setSavedCard(color.value.toLong())
                        }
                        itemOfList[4]->{
                            settingsViewModel.setButton(color.value.toLong())
                        }
                    }
            })
}

@Composable
fun ChangeColorExpandedCard(
    dropMenuExpanded: Boolean,
    returnDropMenuExpanded: (Boolean) -> Unit,
    returnItemName: (String) -> Unit,
    returnListName: (List<String>) -> Unit
) {
    //if you changed localNameList you should edit ChangeColorChangeColorItem and ChangeColorSaveColorChanged
    val localNameList: List<String> =
        listOf("Main color", "Definition color", "Example color", "Saved word color","Button")
    var currentName by remember { mutableStateOf(localNameList[0]) }
    returnItemName(currentName)
    returnListName(localNameList)
    Box(
        modifier = Modifier
            .background(OwnTheme.colors.backgroundInBackground, shape = CircleShape)
            .padding(start = OwnTheme.dp.smallDp, end = OwnTheme.dp.smallDp)
    ) {
        Column {
            Row {
                Text(
                    text = currentName,
                    fontSize = OwnTheme.typography.general.fontSize,
                    color = OwnTheme.colors.primaryText,
                    modifier = Modifier
                        .clickable {
                            returnDropMenuExpanded(!dropMenuExpanded)
                        })
                Spacer(modifier = Modifier.width(OwnTheme.dp.smallDp))
                Icon(
                    imageVector = if (dropMenuExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = null,
                    tint = OwnTheme.colors.tintColor,
                    modifier = Modifier
                        .clickable {
                            returnDropMenuExpanded(!dropMenuExpanded)
                        }
                )
            }
            DropdownMenu(
                expanded = dropMenuExpanded,
                onDismissRequest = {
                    returnDropMenuExpanded(false)
                },
                modifier = Modifier
                    .clickable {
                        returnDropMenuExpanded(true)
                    }
                    .background(
                        OwnTheme.colors.backgroundInBackground,
                        shape = OwnTheme.sizesShapes.mediumShape
                    )
            ) {
                for (i in localNameList) {
                    Text(
                        text = i,
                        fontSize = OwnTheme.typography.general.fontSize,
                        color = OwnTheme.colors.primaryText,
                        modifier = Modifier
                            .clickable {
                                returnDropMenuExpanded(false)
                                currentName = i
                            }
                    )
                }
            }
        }
    }
}

@Composable
fun ChangeColorChangeColorItem(itemName: String, localName: List<String>, color: Color) {
    when (itemName) {
        localName[0] -> {
            ChangeColorMainColorBox(color = color)
        }
        localName[1] -> {
            ChangeColorDefExamCard(color = color, cardType = "Definition:")
        }
        localName[2] -> {
            ChangeColorDefExamCard(color = color, cardType = "Example:")
        }
        localName[3] -> {
            ColorChangeWordKeepedCard(color = color)
        }
        localName[4]->{
            ChangeColorButtonBox(color = color)
        }
    }
}

@Composable
fun ChangeColorMainColorBox(color: Color) {
    ThemeExample(tintColor = color)
}

@Composable
fun ChangeColorButtonBox(color: Color){
    ThemeExample(buttonColor = color)//TODO("continue work with colors(i need change color button in color theme "see under"):)")
}

@Composable
fun ChangeColorTextSizeBox(settingsViewModel: SettingsViewModel) {
    Box(
        modifier = Modifier
            .background(
                OwnTheme.colors.secondaryBackground,
                shape = OwnTheme.sizesShapes.mediumShape
            )
            .fillMaxWidth()
    ) {
        val textSizeStart = OwnTheme.typography.general.fontSize.value
        var textSize by remember { mutableStateOf(textSizeStart) }
        Column {
            Spacer(modifier = Modifier.height(OwnTheme.dp.mediumDp))
            Text(
                text = "Set text size",
                color = OwnTheme.colors.primaryText,
                style = TextStyle.Default.copy(fontStyle = OwnTheme.typography.heading.fontStyle),
                modifier = Modifier
                    .padding(start = OwnTheme.dp.mediumDp),
                fontSize = OwnTheme.typography.general.fontSize.value.sp
            )
            Spacer(modifier = Modifier.height(OwnTheme.dp.normalDp))
            Slider(
                value = textSize,
                onValueChange = {
                    textSize = it
                    settingsViewModel.setTextSize(it)
                },
                valueRange = 14f..25f
            )
            Spacer(modifier = Modifier.height(OwnTheme.dp.normalDp))
            Text(
                text = "You can try change to size of this text",
                fontSize = OwnTheme.typography.general.fontSize.value.sp,
                color = OwnTheme.colors.primaryText,
                modifier = Modifier
                    .padding(start = OwnTheme.dp.mediumDp)
            )
            Spacer(modifier = Modifier.height(OwnTheme.dp.mediumDp))
        }
    }
}

@Composable
fun ChangeColorColorThemeBox(
    settingsViewModel: SettingsViewModel,
    globalSettingsViewModel: GlobalSettingsViewModel
) {
    val scope = rememberCoroutineScope()
    val isDarkMode = globalSettingsViewModel.isDarkMode.collectAsState()
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
                    .padding(start = OwnTheme.dp.mediumDp),
                fontSize = OwnTheme.typography.general.fontSize.value.sp
            )
            Spacer(modifier = Modifier.height(OwnTheme.dp.normalDp))
            LazyRow(
                Modifier
                    .fillMaxWidth()
                    .background(OwnTheme.colors.primaryBackground)
                    .padding(
                        top = OwnTheme.dp.smallDp,
                        end = OwnTheme.dp.smallDp,
                        bottom = OwnTheme.dp.smallDp
                    )
            ) {
                scope.launch(Dispatchers.Default) {
                    item {
                        ChangeColorColorThemeLine(
                            isDarkMode = isDarkMode.value,
                            settingsViewModel = settingsViewModel
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(OwnTheme.dp.bigDp))
        }
    }
}

@Composable
fun ChangeColorColorThemeLine(isDarkMode: Boolean, settingsViewModel: SettingsViewModel) {
    if (isDarkMode) {
        for (i in ChangeColorScreenThemeList().darkThemeList) {
            Spacer(modifier = Modifier.width(OwnTheme.dp.smallDp))
            ThemeExampleLessSize(i, settingsViewModel)
        }
    } else {
        for (i in ChangeColorScreenThemeList().lightThemeList) {
            Spacer(modifier = Modifier.width(OwnTheme.dp.smallDp))
            ThemeExampleLessSize(i, settingsViewModel)
        }
    }
    Spacer(modifier = Modifier.height(OwnTheme.dp.normalDp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeExample(
    tintColor: Color = OwnTheme.colors.tintColor,
    buttonColor:Color = OwnTheme.colors.button,
    textSize: OwnTypography = OwnTheme.typography
) {
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
            .border(3.dp, color = tintColor, shape = CircleShape)
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
                    color = OwnTheme.colors.secondaryText,
                    fontSize = textSize.body.fontSize
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
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
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
fun ThemeExampleLessSize(style: OwnColors = OwnTheme.colors, settingsViewModel: SettingsViewModel) {
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
    var clickWasDid by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val animatedColor by animateColorAsState(
        targetValue = if (clickWasDid) OwnTheme.colors.tintColor else OwnTheme.colors.secondaryBackground,
    )
    Box(
        Modifier
            .height(100.dp)
            .width(90.dp)
            .background(
                animatedColor,
                shape = OwnTheme.sizesShapes.mediumShape
            )
            .clickable {
                settingsViewModel.setDefinitionCardDark(style.definitionCard.value.toLong())
                settingsViewModel.setExampleCardDark(style.exampleCard.value.toLong())
                settingsViewModel.setTintDark(style.tintColor.value.toLong())
                clickWasDid = true
                scope.launch {
                    delay(1500)
                    clickWasDid = false
                }
            }
    ) {
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
                        color = style.tintColor,
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
                    colors = ButtonDefaults.buttonColors(containerColor = style.button),
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
}

interface ChangeColorScreenThemeListInterface {
    var lightThemeList: List<OwnColors>
    var darkThemeList: List<OwnColors>
    val saveThemeList: List<OwnColors>
}

class ChangeColorScreenThemeList : ChangeColorScreenThemeListInterface {
    override var lightThemeList = listOf(
        baseLightPalette,
        purpleLightPalette,
        greenLightPalette,
        blueLightPalette,
        darkLightPalette,
        redLightPalette
    )
    override var darkThemeList = listOf(
        baseDarkPalette,
        purpleDarkPalette,
        greenDarkPalette,
        blueDarkPalette,
        darkDarkPalette,
        redDarkPalette
    )
    override var saveThemeList = listOf(
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