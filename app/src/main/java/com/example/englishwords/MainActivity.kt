package com.example.englishwords

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.DrawerValue
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.lifecycle.Lifecycle
import androidx.navigation.compose.rememberNavController
import com.example.englishwords.models.preferencesModels.SettingsSharedPreferences
import com.example.englishwords.navigation.Navigation
import com.example.englishwords.screens.ourUiElements.MainDrawerMenu
import com.example.englishwords.ui.theme.ownTheme.OwnTheme
import com.example.englishwords.viewModels.DrawerViewModel
import com.example.englishwords.viewModels.GlobalSettingsViewModel
import com.example.englishwords.viewModels.MainViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint(
        "CommitPrefEdits", "UnusedMaterialScaffoldPaddingParameter",
        "UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition",
        "UnrememberedMutableState"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val drawerViewModel: DrawerViewModel = get()
            val globalSettingsViewModel: GlobalSettingsViewModel = get()//Here
            val isDarkmode = globalSettingsViewModel.isDarkMode.collectAsState()
            val style = globalSettingsViewModel.ownStyle.collectAsState()
            val drawerState = drawerViewModel.drawerState.collectAsState()
            val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
            val scope = rememberCoroutineScope()
            OwnTheme(
                darkTheme = isDarkmode.value,
                style = style.value
            ) {
                val navController = rememberNavController()
                androidx.compose.material.Scaffold(
                    drawerShape = OwnTheme.sizesShapes.mediumShape,
                    drawerContent = { MainDrawerMenu(navController = navController,scaffoldState,scope) },
                    backgroundColor = OwnTheme.colors.primaryBackground,
                    scaffoldState = scaffoldState/*if (drawerState.value) rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
                    else rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))*/
                ) {
                    Navigation(navController = navController)
                }
            }
        }
    }
}