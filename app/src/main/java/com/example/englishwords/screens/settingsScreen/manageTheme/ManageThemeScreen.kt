package com.example.englishwords.screens.settingsScreen.manageTheme

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.englishwords.models.preferencesModels.SettingsSharedPreferences
import com.example.englishwords.navigation.SettingsScreen
import com.example.englishwords.screens.ourUiElements.customClickable
import com.example.englishwords.screens.settingsScreen.SettingsArrowForward
import com.example.englishwords.screens.settingsScreen.SettingsViewModel
import com.example.englishwords.ui.theme.ownTheme.OwnColors
import com.example.englishwords.ui.theme.ownTheme.OwnTheme
import com.example.englishwords.viewModels.GlobalSettingsViewModel
import org.koin.androidx.compose.get
import org.koin.java.KoinJavaComponent.inject

@Composable
fun ManageTheme(navController: NavHostController) {
    val settingsSharedPreferences = get<SettingsSharedPreferences>()
    val globalSettingsViewModel: GlobalSettingsViewModel = get()
    Column(Modifier.fillMaxSize()) {
        val isDarkMode = globalSettingsViewModel.isDarkMode.collectAsState()
        /*Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    OwnTheme.colors.secondaryBackground,
                    shape = OwnTheme.sizesShapes.mediumShape
                ),
            horizontalArrangement = Arrangement.End
        ) {
            ManageThemeTopBar()
        }*/

        Spacer(modifier = Modifier.height(OwnTheme.dp.largeDp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = OwnTheme.colors.secondaryBackground,
                    shape = OwnTheme.sizesShapes.mediumShape
                )
        ) {
            Column {
                Spacer(modifier = Modifier.height(OwnTheme.dp.normalDp))
                ManageScreenDoElementCard(
                    icon = if (isDarkMode.value) Icons.Filled.Email else Icons.Filled.Search,
                    text = "Enable dark mode",
                    endElement = {
                        OwnToggleButton(
                            work = { globalSettingsViewModel.changeDarkMode() },
                            darkModeState = isDarkMode.value
                        )
                    }
                )
                Spacer(modifier = Modifier.height(OwnTheme.dp.normalDp))
                ManageScreenTransferElementCard(
                    settingsElement = SettingsScreen.ChangeColor,
                    navController = navController,
                    endElement = { SettingsArrowForward() }
                )
                Spacer(modifier = Modifier.height(OwnTheme.dp.normalDp))
            }
        }
    }
}

@Composable
fun OwnToggleButton(work: () -> Unit, darkModeState: Boolean) {
    Switch(
        checked = darkModeState,
        onCheckedChange = {
            work()
        })
}

@Composable
fun ManageScreenDoElementCard(endElement: @Composable () -> Unit, icon: ImageVector, text: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(OwnTheme.dp.bigDp)
            .padding(start = OwnTheme.dp.normalDp)
    ) {
        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(OwnTheme.dp.normalDp))
            Text(
                text = text,
                color = OwnTheme.colors.primaryText
            )
        }
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            endElement()
        }
    }
}

@Composable
fun ManageScreenTransferElementCard(
    settingsElement: SettingsScreen,
    endElement: @Composable (() -> Unit),
    navController: NavHostController
) {
    Row(
        Modifier
            .fillMaxWidth()
            .customClickable {
                navController.navigate(settingsElement.route)
                Log.e("Log", "Click CLick")
            }
            .height(OwnTheme.dp.bigDp)
    ) {
        Spacer(modifier = Modifier.width(OwnTheme.dp.normalDp))
        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = settingsElement.icon,
                contentDescription = settingsElement.route
            )
            Spacer(modifier = Modifier.width(OwnTheme.dp.normalDp))
            Text(
                text = settingsElement.route,
                color = OwnTheme.colors.primaryText
            )
        }
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            endElement()
        }
    }
}

@Composable
fun ManageThemeTopBar() {
    val colorList = ColorListImpl().colorList
    val settingsViewModel: SettingsViewModel by inject(SettingsViewModel::class.java)
    val globalSettingsViewModel: GlobalSettingsViewModel = get()
    var isDarkmode = globalSettingsViewModel.isDarkMode.collectAsState().value
    colorList.forEach { style ->
        FloatingActionButton(
            onClick = {
                if (isDarkmode)
                    settingsViewModel.setTintDark(
                        when (style.name) {
                            "Purple" -> 0xFF9b4ec3
                            "Blue" -> 0xFF5BC0F8
                            "Red" -> 0xFFFF0032
                            "Green" -> 0xFF54B435
                            "Black" -> 0xFF191E23
                            else -> 0xFF9b4ec3
                        }
                    )
                else settingsViewModel.setTint(
                    when (style.name) {
                        "Purple" -> 0xFF9b4ec3
                        "Blue" -> 0xFF5BC0F8
                        "Red" -> 0xFFFF0032
                        "Green" -> 0xFF54B435
                        "Black" -> 0xFF191E23
                        else -> 0xFF9b4ec3
                    }
                )
            },
            modifier = Modifier
                .border(1.dp, color = OwnTheme.colors.primaryText, shape = CircleShape)
                .size(40.dp),
            containerColor = when (style.name) {
                "Purple" -> OwnTheme.colors.purple
                "Blue" -> OwnTheme.colors.blue
                "Red" -> OwnTheme.colors.red
                "Green" -> OwnTheme.colors.green
                "Black" -> OwnTheme.colors.black
                else -> OwnTheme.colors.purple
            },
            shape = CircleShape,
        ) {}
        Spacer(modifier = Modifier.width(4.dp))
    }
    Log.e("Log", "TopBar")
    IconButton(
        onClick = {
            globalSettingsViewModel.changeDarkMode()
            isDarkmode = !isDarkmode
        },
        Modifier.height(40.dp)
    ) {
        androidx.compose.material3.Icon(
            if (isDarkmode) Icons.Filled.Build else Icons.Filled.Call,
            null,
            tint = OwnTheme.colors.primaryText
        )
    }
}

interface ColorListInterface {
    var colorList: List<OwnTheme.OwnStyle>
}

class ColorListImpl : ColorListInterface {
    override var colorList: List<OwnTheme.OwnStyle> =
        listOf<OwnTheme.OwnStyle>(
            OwnTheme.OwnStyle.Black,
            OwnTheme.OwnStyle.Green,
            OwnTheme.OwnStyle.Blue,
            OwnTheme.OwnStyle.Red,
            OwnTheme.OwnStyle.Purple
        )
}

interface ManageThemeColorsListInterface {
    var colorsList: List<OwnColors>
}

class ManageThemeColorsList : ManageThemeColorsListInterface {
    override var colorsList: List<OwnColors> = listOf(

    )
}