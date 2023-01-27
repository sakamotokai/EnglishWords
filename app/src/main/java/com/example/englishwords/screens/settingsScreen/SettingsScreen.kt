package com.example.englishwords.screens.settingsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.example.englishwords.navigation.SettingsScreen
import com.example.englishwords.screens.ourUiElements.CustomOutlinedTextField
import com.example.englishwords.screens.ourUiElements.customClickable
import com.example.englishwords.ui.theme.ownTheme.OwnTheme

@Composable
fun SettingsScreen(navController: NavHostController) {
    Column(Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Settings",
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(start = OwnTheme.dp.mediumDp),
            style = OwnTheme.typography.heading.copy(color = OwnTheme.colors.primaryText, fontSize = (OwnTheme.typography.heading.fontSize.value +
                    OwnTheme.typography.general.fontSize.value - 14f).sp)
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
            }
        }
    }
}

@Composable
fun SettingsThemeBox(navController: NavHostController){
    Spacer(modifier = Modifier.height(OwnTheme.dp.smallDp))
    SettingsElementCard(SettingsScreen.CustomTheme, endElement = {
        SettingsArrowForward()
    }, navController)
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

