package com.example.englishwords.navigation

import android.annotation.SuppressLint
import android.media.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.englishwords.screens.MainScreen
import com.example.englishwords.screens.settingsScreen.SettingsScreen
import com.example.englishwords.screens.wordKeepedScreen.WordKeepedScreen
import com.example.englishwords.ui.theme.ownTheme.OwnTheme
import com.example.englishwords.viewModels.GlobalSettingsViewModel
import org.koin.androidx.compose.getViewModel
import androidx.compose.material3.Scaffold as Scaffold

sealed class Screen(val route: String, val icon: ImageVector) {
    object MainScreen : Screen("Main", Icons.Filled.Home)
    object WordKeeped : Screen("Saved Word", Icons.Filled.Star)
    object SettingsScreen:Screen("Settings",Icons.Filled.Settings)
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
            SettingsScreen()
        }
    }
}