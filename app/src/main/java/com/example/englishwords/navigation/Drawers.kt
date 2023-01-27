package com.example.englishwords.screens.ourUiElements

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.englishwords.navigation.Screen
import com.example.englishwords.ui.theme.ownTheme.OwnTheme
import com.example.englishwords.viewModels.DrawerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

@Composable
fun MainDrawerMenu(navController: NavHostController,scaffoldState: ScaffoldState,scope: CoroutineScope) {
    Column(
        Modifier
            .fillMaxSize()
            .background(color = OwnTheme.colors.primaryBackground)
    ) {
        Box(
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
                .background(
                    OwnTheme.colors.secondaryBackground,
                    shape = OwnTheme.sizesShapes.bigShape
                )
        )
        Spacer(modifier = Modifier.height(OwnTheme.dp.mediumDp))
        DrawerItems().screensItem.forEach {
            DrawerCard(item = it, navController,scaffoldState,scope)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun DrawerCard(item: Screen, navController: NavHostController,scaffoldState: ScaffoldState,scope: CoroutineScope) {
    Spacer(modifier = Modifier.width(OwnTheme.dp.normalDp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = OwnTheme.colors.primaryBackground,
                shape = OwnTheme.sizesShapes.largeShape
            )
            .clickable {
                navController.navigate(item.route) {
                    popUpTo(Screen.MainScreen.route) {
                        //inclusive = true
                    }
                    launchSingleTop = true
                }
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            }
    ) {
        Row(
            Modifier.fillMaxWidth()
                .background(OwnTheme.colors.primaryBackground,shape = OwnTheme.sizesShapes.mediumShape),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.route
            )
            Spacer(modifier = Modifier.width(OwnTheme.dp.normalDp))
            Text(
                text = item.route,
                color = OwnTheme.colors.primaryText,
                style = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                fontSize = OwnTheme.typography.general.fontSize.value.sp
            )
        }
    }
}

class DrawerItems : DrawerItemsInterface {
    override val screensItem: List<Screen> = listOf(
        Screen.MainScreen,
        Screen.WordKeeped,
        Screen.SettingsScreen
    )
}

interface DrawerItemsInterface {
    val screensItem: List<Screen>
}