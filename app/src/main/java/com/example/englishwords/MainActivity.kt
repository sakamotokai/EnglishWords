package com.example.englishwords

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.example.englishwords.navigation.Navigation
import com.example.englishwords.notifications.SendNotificationReceiverImpl
import com.example.englishwords.notifications.rememberWordNotification.RememberWordNotificationReceiver
import com.example.englishwords.notifications.reminderNotification.ReminderNotificationReceiver
import com.example.englishwords.screens.ourUiElements.MainDrawerMenu
import com.example.englishwords.ui.theme.ownTheme.OwnTheme
import com.example.englishwords.viewModels.GlobalSettingsViewModel
import com.example.englishwords.viewModels.MessageViewModel
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.java.KoinJavaComponent
import java.time.LocalTime

class MainActivity : ComponentActivity() {
/*    private val globalSettingsViewModel by KoinJavaComponent.inject<GlobalSettingsViewModel>(
        GlobalSettingsViewModel::class.java
    )*/
    private val messageViewModel by KoinJavaComponent.inject<MessageViewModel>(
        MessageViewModel::class.java
    )

    @SuppressLint(
        "CommitPrefEdits", "UnusedMaterialScaffoldPaddingParameter",
        "UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition",
        "UnrememberedMutableState", "RestrictedApi", "MissingPermission"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val globalSettingsViewModel:GlobalSettingsViewModel = get()
            val isDarkmode = globalSettingsViewModel.isDarkMode.collectAsState()
            val mainDrawerGesturesEnabled =
                globalSettingsViewModel.mainDrawerGesturesEnabled.collectAsState()
            val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
            val scope = rememberCoroutineScope()
            OwnTheme(
                darkTheme = isDarkmode.value,
                style = OwnTheme.OwnStyle.Custom
            ) {
                val navController = rememberNavController()
                Scaffold(
                    drawerShape = OwnTheme.sizesShapes.mediumShape,
                    drawerContent = {
                        MainDrawerMenu(
                            navController = navController,
                            scaffoldState,
                            scope
                        )
                    },
                    backgroundColor = OwnTheme.colors.primaryBackground,
                    scaffoldState = scaffoldState,
                    drawerGesturesEnabled = mainDrawerGesturesEnabled.value
                ) {
                    Navigation(navController = navController)
                }
            }
        }
    }

    override fun onResume() {
        messageViewModel.setAppState(true)
        super.onResume()
    }

    override fun onStop() {
        messageViewModel.setAppState(false)
        messageViewModel.setLastHour(LocalTime.now().hour)
        super.onStop()
    }
}
