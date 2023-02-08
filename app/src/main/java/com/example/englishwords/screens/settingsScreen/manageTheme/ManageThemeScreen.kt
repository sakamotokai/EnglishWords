package com.example.englishwords.screens.settingsScreen.manageTheme

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.englishwords.R
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
                ManageScreenElementCard(
                    icon = if (isDarkMode.value) ImageVector.vectorResource(R.drawable.darktheme_1) else ImageVector.vectorResource(R.drawable.lighttheme_1),
                    text = "Enable dark mode",
                    endElement = {
                        OwnToggleButton(
                            work = { globalSettingsViewModel.changeDarkMode() },
                            darkModeState = isDarkMode.value
                        )
                    })
                Spacer(modifier = Modifier.height(OwnTheme.dp.normalDp))
                ManageScreenElementCard(
                    text = SettingsScreen.ChangeColor.route,
                    onClick = { navController.navigate(SettingsScreen.ChangeColor.route)},
                    endElement = { SettingsArrowForward() },
                icon = ImageVector.vectorResource(SettingsScreen.ChangeColor.icon))
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
fun ManageScreenElementCard(endElement: @Composable () -> Unit, icon: ImageVector, text: String,onClick:()->Unit = {}) {
    Row(
        Modifier
            .customClickable {
                onClick()
            }
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
                contentDescription = null,
                tint = OwnTheme.colors.tintColor
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
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            endElement()
        }
    }
}