package com.example.englishwords.navigation

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.englishwords.R
import com.example.englishwords.screens.mainScreen.MainScreen
import com.example.englishwords.screens.settingsScreen.SettingsScreen
import com.example.englishwords.screens.settingsScreen.manageTheme.ManageTheme
import com.example.englishwords.screens.settingsScreen.manageTheme.changeColor.ChangeColorScreen
import com.example.englishwords.screens.settingsScreen.notifications.NotificationsScreen
import com.example.englishwords.screens.wordKeepedScreen.WordKeepedScreen

sealed class Screen(val route: String, val icon: ImageVector) {
    object MainScreen : Screen("Main", Icons.Filled.Home)
    object WordKeeped : Screen("Saved Word", Icons.Filled.Star)
    object SettingsScreen:Screen("Settings",Icons.Filled.Settings)
}

sealed class SettingsScreen(val route: String,val icon:Int){
    object CustomTheme :SettingsScreen(route = "Manage theme",icon = R.drawable.brush_1 )
    object ChangeColor :SettingsScreen(route = "Change Color", icon = R.drawable.edit_1)
    object NotificationScreen:SettingsScreen(route = "Notification",icon = R.drawable.notification_on_1)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ComposableDestinationInComposeScope")
@Composable
fun Navigation(
    navController: NavHostController = rememberNavController()
    ) {
    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route
    ) {
        composable(Screen.MainScreen.route) {
            MainScreen()
        }
        composable(Screen.WordKeeped.route) {
            WordKeepedScreen()
        }
        composable(Screen.SettingsScreen.route){
            SettingsScreen(navController)
        }
        composable(SettingsScreen.CustomTheme.route){
            ManageTheme(navController)
        }
        composable(SettingsScreen.ChangeColor.route){
            ChangeColorScreen(navController)
        }
        composable(SettingsScreen.NotificationScreen.route){
            NotificationsScreen()
        }
    }
}