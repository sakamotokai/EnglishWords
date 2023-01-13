package com.example.englishwords

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import com.example.englishwords.screens.MainScreen
import com.example.englishwords.screens.SearchBar
import com.example.englishwords.ui.theme.EnglishWordsTheme
import com.example.englishwords.ui.theme.ownTheme.OwnTheme
import com.example.englishwords.ui.theme.secondTheme.SecondTheme
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.get
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {
    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val sharedPreferences =
                get<SharedPreferences>(parameters = { parametersOf(SharedPreferencesEnum.settings.route) })
            var isDarkmode =
                remember { mutableStateOf(sharedPreferences.getBoolean("isDarkmode", false)) }
            var ownStyle = remember {
                mutableStateOf(
                    when (sharedPreferences.getString("ownStyle", OwnTheme.OwnStyle.Purple.name)) {
                        "Purple" -> OwnTheme.OwnStyle.Purple
                        "Blue" -> OwnTheme.OwnStyle.Blue
                        "Red" -> OwnTheme.OwnStyle.Red
                        "Green" -> OwnTheme.OwnStyle.Green
                        "Black" -> OwnTheme.OwnStyle.Black
                        else -> OwnTheme.OwnStyle.Purple
                    }
                )
            }
            OwnTheme(
                darkTheme = isDarkmode.value,
                style = ownStyle.value
            ) {
                Surface(modifier = Modifier.fillMaxSize().background(OwnTheme.colors.primaryBackground)){
                    sharedPreferences.edit().putBoolean("isDarkmode", isDarkmode.value).apply()
                    sharedPreferences.edit().putString("ownStyle",ownStyle.value.name).apply()
                    MainScreen(isDarkmode,ownStyle)
                }
            }
        }
    }
}