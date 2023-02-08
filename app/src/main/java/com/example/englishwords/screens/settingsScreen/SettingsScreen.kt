package com.example.englishwords.screens.settingsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.englishwords.R
import com.example.englishwords.navigation.SettingsScreen
import com.example.englishwords.screens.ourUiElements.CustomOutlinedTextField
import com.example.englishwords.screens.ourUiElements.customClickable
import com.example.englishwords.screens.settingsScreen.manageTheme.OwnToggleButton
import com.example.englishwords.screens.settingsScreen.notifications.NotificationViewModel
import com.example.englishwords.ui.theme.ownTheme.OwnTheme
import org.koin.androidx.compose.get

@Composable
fun SettingsScreen(navController: NavHostController) {
    Column(Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Settings",
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(start = OwnTheme.dp.mediumDp),
            style = OwnTheme.typography.heading.copy(
                color = OwnTheme.colors.primaryText,
                fontSize = (OwnTheme.typography.heading.fontSize.value +
                        OwnTheme.typography.general.fontSize.value - 14f).sp
            )
        )
        //Spacer(modifier = Modifier.height(OwnTheme.dp.normalDp))
        Spacer(modifier = Modifier.height(OwnTheme.dp.bigDp))
        Box(
            modifier = Modifier
                .background(
                    color = OwnTheme.colors.secondaryBackground,
                    shape = OwnTheme.sizesShapes.smallShape
                )
                .fillMaxWidth()
                .padding(start = OwnTheme.dp.smallDp)
        ) {
            Column {
                SettingsThemeBox(navController)
                SettingsNotificationBox(navController)
                SettingsDeleteWordBox(get())
            }
        }
    }
}

@Composable
fun SettingsThemeBox(navController: NavHostController) {
    Spacer(modifier = Modifier.height(OwnTheme.dp.smallDp))
    SettingsElementCard(
        settingName = SettingsScreen.CustomTheme.route,
        settingRoute = SettingsScreen.CustomTheme.route,
        settingIcon = ImageVector.vectorResource(SettingsScreen.CustomTheme.icon),
        endElement = {
            SettingsArrowForward()
        },
        navController = navController
    )
    Spacer(modifier = Modifier.height(OwnTheme.dp.smallDp))
}

@Composable
fun SettingsNotificationBox(navController: NavHostController) {
    val notificationViewModel: NotificationViewModel = get()
    val getRemindedNotifications =
        notificationViewModel.isRemindedNotifications.collectAsState()
    val getWordNotifications = notificationViewModel.isWordNotifications.collectAsState()
    Spacer(modifier = Modifier.height(OwnTheme.dp.smallDp))
    SettingsElementCard(
        settingName = SettingsScreen.NotificationScreen.route,
        endElement = {
            SettingsArrowForward()
        },
        navController = navController,
        settingRoute = SettingsScreen.NotificationScreen.route,
        settingIcon = if (!getRemindedNotifications.value && !getWordNotifications.value) ImageVector.vectorResource(
            id = R.drawable.notification_off_1
        )
        else ImageVector.vectorResource(id = R.drawable.notification_on_1)
    )
    Spacer(modifier = Modifier.height(OwnTheme.dp.smallDp))
}

@Composable
fun SettingsDeleteWordBox(settingsScreenViewModel: SettingsViewModel) {
    Spacer(modifier = Modifier.height(OwnTheme.dp.smallDp))
    SettingsElementCard(
        settingName = "Delete word after 7 days",
        endElement = {
            OwnToggleButton(
                work = {
                    settingsScreenViewModel.changeDeleteWordAfterState()
                },
                darkModeState = settingsScreenViewModel.deleteWordAfter.collectAsState().value
            )
        },
        navController = null,
        settingRoute = null,
        settingIcon = Icons.Filled.Delete
    )
    Spacer(modifier = Modifier.height(OwnTheme.dp.smallDp))
}

@Composable
fun SettingsArrowForward() {
    Icon(
        imageVector = Icons.Filled.ArrowForward,
        contentDescription = null,
        tint = OwnTheme.colors.primaryBackground
    )
}

@Composable
fun SettingsElementCard(
    settingName: String,
    settingIcon: ImageVector,
    settingRoute: String?,
    endElement: @Composable (() -> Unit),
    navController: NavHostController? = null
) {
    Row(
        Modifier
            .fillMaxWidth()
            .customClickable {
                navController?.navigate(settingRoute ?: "")
            }
            .height(OwnTheme.dp.bigDp)
    ) {
        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = settingIcon,
                contentDescription = settingRoute,
                tint = OwnTheme.colors.tintColor
            )
            Spacer(modifier = Modifier.width(OwnTheme.dp.normalDp))
            Text(
                text = settingName,
                color = OwnTheme.colors.primaryText,
                fontSize = OwnTheme.typography.general.fontSize.value.sp
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxSize()
        ) {
            endElement()
        }
    }
}

@Composable
fun SettingsElementCard2(
    settingsElement: SettingsScreen,
    endElement: @Composable (() -> Unit),
    navController: NavHostController
) {
    Row(
        Modifier
            .fillMaxWidth()
            .customClickable {
                navController.navigate(settingsElement.route)
            }
            .height(OwnTheme.dp.bigDp)
    ) {
        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(settingsElement.icon),
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
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxSize()
        ) {
            endElement()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarSettings() {
    var localSearchText by remember { mutableStateOf("") }
    CustomOutlinedTextField(
        value = localSearchText,
        onValueChange = { localSearchText = it },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search",
                tint = OwnTheme.colors.secondaryText
            )
        },
        placeholder = {
            Text(text = "Search", color = OwnTheme.colors.secondaryText)
        },
        shape = CircleShape,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = OwnTheme.colors.secondaryBackground,
            focusedIndicatorColor = OwnTheme.colors.secondaryBackground,
            unfocusedIndicatorColor = OwnTheme.colors.secondaryBackground,
            textColor = OwnTheme.colors.primaryText,
        ),
        modifier = Modifier
            .padding(start = OwnTheme.dp.mediumDp, end = OwnTheme.dp.mediumDp)
            .height(OwnTheme.dp.bigDp)
            .fillMaxSize()
            .background(
                color = OwnTheme.colors.secondaryBackground,
                shape = CircleShape
            ),
        contentPaddingValues = PaddingValues(vertical = 5.dp)
    )
}

