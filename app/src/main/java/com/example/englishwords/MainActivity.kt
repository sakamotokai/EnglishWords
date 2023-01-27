package com.example.englishwords

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.englishwords.navigation.Navigation
import com.example.englishwords.screens.MainScreen
import com.example.englishwords.screens.ourUiElements.MainDrawerMenu
import com.example.englishwords.screens.settingsScreen.SettingsScreen
import com.example.englishwords.ui.theme.ownTheme.OwnTheme
import com.example.englishwords.viewModels.GlobalSettingsViewModel
import org.koin.androidx.compose.get
import java.lang.reflect.Modifier

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
            val mainDrawerGesturesEnabled = globalSettingsViewModel.mainDrawerGesturesEnabled.collectAsState()
            //Delete above line in the future
            Log.e("Log",mainDrawerGesturesEnabled.value.toString())
            val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
            val scope = rememberCoroutineScope()
            OwnTheme(
                darkTheme = isDarkmode.value,
                style = OwnTheme.OwnStyle.Custom/*style.value*/
            ) {//TODO("Turn of drawerContent when i wish to select color")
                val navController = rememberNavController()
                /*ModalDrawer(
                    drawerShape = OwnTheme.sizesShapes.mediumShape,
                    drawerContent = { MainDrawerMenu(navController = navController,scaffoldState,scope) },
                    drawerBackgroundColor = OwnTheme.colors.primaryBackground,
                    drawerContentColor = OwnTheme.colors.primaryBackground,
                    scrimColor = OwnTheme.colors.primaryBackground,
                    drawerState = scaffoldState.drawerState,
                    gesturesEnabled = false
                ) {
                    Navigation(navController)
                }*/
                androidx.compose.material.Scaffold(
                    drawerShape = OwnTheme.sizesShapes.mediumShape,
                    drawerContent = { MainDrawerMenu(navController = navController,scaffoldState,scope) },
                    backgroundColor = OwnTheme.colors.primaryBackground,
                    scaffoldState = scaffoldState,
                    drawerGesturesEnabled = mainDrawerGesturesEnabled.value
                ) {
                    Navigation(navController = navController)
                }
            }
        }
    }
}
