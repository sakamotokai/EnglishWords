package com.example.englishwords.screens.ourUiElements

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.englishwords.navigation.Screen
import com.example.englishwords.ui.theme.ownTheme.OwnTheme

//@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainDrawerMenu(navController: NavHostController) {
    Column(
        Modifier
            .fillMaxSize()
            .background(color = OwnTheme.colors.primaryBackground)
            .padding(start = 10.dp, top = 48.dp, end = 10.dp),
    ) {
        DrawerItems().screensItem.forEach {
            DrawerCard(item = it,navController)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun DrawerCard(item: Screen,navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = OwnTheme.colors.secondaryBackground,
                shape = OwnTheme.sizesShapes.largeShape
            )
            .border(
                1.dp,
                color = OwnTheme.colors.tintColor,
                shape = OwnTheme.sizesShapes.largeShape
            ).clickable {
                navController.navigate(item.route)/*{
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }*/
            }
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.route
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = item.route,
                color = OwnTheme.colors.primaryText,
                style = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
            )
        }
    }
}

class DrawerItems : DrawerItemsInterface {
    override val screensItem: List<Screen> = listOf(
            Screen.MainScreen,
            Screen.WordKeeped
        )
}

interface DrawerItemsInterface {
    val screensItem: List<Screen>
}