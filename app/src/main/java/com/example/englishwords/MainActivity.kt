package com.example.englishwords

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.DrawerValue
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.example.englishwords.navigation.Navigation
import com.example.englishwords.screens.ourUiElements.MainDrawerMenu
import com.example.englishwords.ui.theme.ownTheme.OwnTheme
import com.example.englishwords.viewModels.GlobalSettingsViewModel
import org.koin.androidx.compose.get

class MainActivity : ComponentActivity() {
    @SuppressLint(
        "CommitPrefEdits", "UnusedMaterialScaffoldPaddingParameter",
        "UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition",
        "UnrememberedMutableState"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val globalSettingsViewModel: GlobalSettingsViewModel = get()
            val isDarkmode = globalSettingsViewModel.isDarkMode.collectAsState()
            val style = globalSettingsViewModel.ownStyle.collectAsState()
            val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
            val scope = rememberCoroutineScope()
            OwnTheme(
                darkTheme = isDarkmode.value,
                style = OwnTheme.OwnStyle.Custom/*style.value*/
            ) {
                val navController = rememberNavController()
                androidx.compose.material.Scaffold(
                    drawerShape = OwnTheme.sizesShapes.mediumShape,
                    drawerContent = { MainDrawerMenu(navController = navController,scaffoldState,scope) },
                    backgroundColor = OwnTheme.colors.primaryBackground,
                    scaffoldState = scaffoldState
                ) {
                    Navigation(navController = navController)
                }
            }
        }
    }
}