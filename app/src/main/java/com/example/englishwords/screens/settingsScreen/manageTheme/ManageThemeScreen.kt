package com.example.englishwords.screens.settingsScreen.manageTheme

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.englishwords.navigation.SettingsScreen
import com.example.englishwords.screens.ourUiElements.customClickable
import com.example.englishwords.screens.settingsScreen.SettingsArrowForward
import com.example.englishwords.ui.theme.ownTheme.OwnTheme
import com.example.englishwords.viewModels.GlobalSettingsViewModel
import org.koin.androidx.compose.get

@Composable
fun ManageTheme(navController: NavHostController) {
    val globalSettingsViewModel: GlobalSettingsViewModel = get()
    Column(Modifier.fillMaxSize()) {
        val isDarkMode = globalSettingsViewModel.isDarkMode.collectAsState()
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
                color = OwnTheme.colors.primaryText,
                fontSize = OwnTheme.typography.general.fontSize.value.sp
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
                color = OwnTheme.colors.primaryText,
                fontSize = OwnTheme.typography.general.fontSize.value.sp
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

interface ColorListInterface {
    var colorList: List<OwnTheme.OwnStyle>
}

class ColorListImpl : ColorListInterface {
    override var colorList: List<OwnTheme.OwnStyle> =
        listOf(
            OwnTheme.OwnStyle.Black,
            OwnTheme.OwnStyle.Green,
            OwnTheme.OwnStyle.Blue,
            OwnTheme.OwnStyle.Red,
            OwnTheme.OwnStyle.Purple
        )
}