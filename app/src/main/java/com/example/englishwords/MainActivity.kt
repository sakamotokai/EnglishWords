package com.example.englishwords

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.example.englishwords.navigation.Navigation
import com.example.englishwords.screens.ourUiElements.MainDrawerMenu
import com.example.englishwords.ui.theme.ownTheme.OwnTheme
import com.example.englishwords.viewModels.GlobalSettingsViewModel
import org.koin.androidx.compose.getViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("CommitPrefEdits", "UnusedMaterialScaffoldPaddingParameter",
        "UnusedMaterial3ScaffoldPaddingParameter"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val globalSettingsViewModel:GlobalSettingsViewModel = getViewModel()
            val isDarkMode = globalSettingsViewModel.isDarkMode.collectAsState()
            val ownStyle = globalSettingsViewModel.ownStyle.collectAsState()
            val navController = rememberNavController()
            OwnTheme(
                darkTheme = isDarkMode.value,
                style = ownStyle.value
            ) {
                androidx.compose.material.Scaffold(
                    drawerShape = OwnTheme.sizesShapes.mediumShape,
                    drawerContent = { MainDrawerMenu(navController = navController) },
                    backgroundColor = OwnTheme.colors.primaryBackground

                ) {
                    Navigation(navController = navController)
                }
            }
        }
    }
}